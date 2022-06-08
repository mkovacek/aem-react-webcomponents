package com.ibmix.adobe.aem.core.components.containerinherit.impl;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import org.apache.sling.models.annotations.injectorspecific.Self;

import com.day.cq.wcm.api.Page;
import com.ibmix.adobe.aem.core.components.containerinherit.ContainerInheritComponent;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Model(
      adaptables = SlingHttpServletRequest.class,
      adapters = ContainerInheritComponent.class,
      defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL,
      resourceType = {ContainerInheritComponentImpl.HEADER_RESOURCE_TYPE, ContainerInheritComponentImpl.FOOTER_RESOURCE_TYPE}
)
public class ContainerInheritComponentImpl implements ContainerInheritComponent {

   protected static final String HEADER_RESOURCE_TYPE = "mkovacek/components/structure/headerInherit";
   protected static final String FOOTER_RESOURCE_TYPE = "mkovacek/components/structure/footerInherit";

   @Self
   private SlingHttpServletRequest request;

   @ScriptVariable
   private Page currentPage;

   @Getter
   private boolean empty;

   @Getter
   private String resourcePath;

   @PostConstruct
   private void init() {
      try {
         Page landingPage = this.currentPage.getAbsoluteParent(3);
         if (StringUtils.equals(landingPage.getTemplate().getPath(),
               "/conf/mkovacek/settings/wcm/templates/page-language")) {
            String resourceName = this.getResourceName();
            this.resourcePath =
                  StringUtils.join(landingPage.getPath(), "/jcr:content/root/", resourceName);
            this.empty = StringUtils.isEmpty(this.resourcePath);
         }
      } catch (RuntimeException e) {
         log.error("Exception in post construct", e);
      }
   }

   private String getResourceName() {
      String resourceType = this.request.getResource().getResourceType();
      if (StringUtils.equals(resourceType, HEADER_RESOURCE_TYPE)) {
         return "header";
      } else if (StringUtils.equals(resourceType, FOOTER_RESOURCE_TYPE)) {
         return "footer";
      } else {
         log.error("Not supported resource type");
         throw new RuntimeException();
      }
   }

}