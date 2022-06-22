package com.mkovacek.adobe.aem.core.components.helloworld.impl;

import com.mkovacek.adobe.aem.core.components.helloworld.HelloWorldComponent;
import com.mkovacek.adobe.aem.core.integrations.ApiClientImpl;
import com.mkovacek.adobe.aem.core.integrations.faker.dto.User;
import com.mkovacek.adobe.aem.core.services.impl.RequestServiceImpl;
import com.mkovacek.adobe.aem.core.testcontext.AppAemContextBuilder;
import com.mkovacek.adobe.aem.core.testcontext.OsgiConfigs;
import com.mkovacek.adobe.aem.core.testcontext.WireMockUtil;
import com.day.cq.wcm.api.Page;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.apache.sling.api.resource.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static junitx.framework.ComparableAssert.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@WireMockTest(httpPort = 18081)
@ExtendWith(AemContextExtension.class)
class HelloWorldComponentImplTest {


   private final AemContext context = new AppAemContextBuilder().build();

   private Page page;
   private Resource resource;

   @BeforeEach
   public void setup() {
      context.registerInjectActivateService(new ApiClientImpl(), OsgiConfigs.fakerApiConfig);
      context.registerInjectActivateService(new RequestServiceImpl());
      context.registerInjectActivateService(new HelloWorldService());


      // prepare a page with a helloworld component
      this.page = this.context.create().page("/content/aem-integrations/ch/de/mypage");
      this.resource = this.context.create().resource(this.page, "hello",
            "sling:resourceType", "aem-integrations/components/helloworld");
      this.context.currentResource(this.resource);
   }

   @Test
   @DisplayName("GIVEN content page with helloworld component WHEN page is requested and request adapted to the model THEN model it should contain expected message")
   void testGetMessage() {
      HelloWorldComponent helloWorldComponent = this.context.request().adaptTo(HelloWorldComponent.class);
      String actualMessage = helloWorldComponent.getMessage();

      assertAll(
            () -> assertNotNull(actualMessage),
            () -> assertTrue(StringUtils.contains(actualMessage, this.resource.getResourceType())),
            () -> assertTrue(StringUtils.contains(actualMessage, this.page.getPath())),
            () -> assertTrue(StringUtils.contains(actualMessage, "default value"))
      );
   }

   @Test
   @DisplayName("GIVEN mocked API response and content page with helloworld component "
                  + "WHEN page is requested and request adapted to the model "
                  + "THEN model it should contain expected sorted users")
   void testGetUsers() {
      //GIVEN
      WireMockUtil.mockExpectedGetResponse("/v1/users", HttpStatus.SC_OK,
            "/wiremock/payload/fakerapi/get-users-response.json", Collections.emptyMap());

      //WHEN
      HelloWorldComponent helloWorldComponent = this.context.request().adaptTo(HelloWorldComponent.class);
      List<User> actualUsers = helloWorldComponent.getUsers();

      //THEN
      assertAll(
            () -> assertNotNull(actualUsers),
            () -> assertEquals(10, actualUsers.size()),
            () -> assertThat(actualUsers).isSortedAccordingTo(Comparator.comparing(User::getLastname))
      );
   }

}
