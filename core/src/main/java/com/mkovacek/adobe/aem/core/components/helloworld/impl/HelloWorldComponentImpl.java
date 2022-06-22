package com.mkovacek.adobe.aem.core.components.helloworld.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;

import com.adobe.cq.export.json.ExporterConstants;
import com.day.cq.wcm.api.Page;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mkovacek.adobe.aem.core.components.helloworld.HelloWorldComponent;
import com.mkovacek.adobe.aem.core.integrations.faker.dto.User;
import com.mkovacek.adobe.aem.core.services.RequestService;

import lombok.Getter;
import lombok.experimental.Delegate;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Model(
      adaptables = SlingHttpServletRequest.class,
      adapters = HelloWorldComponent.class,
      defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL,
      resourceType = {HelloWorldComponentImpl.RESOURCE_TYPE, HelloWorldComponentImpl.RESOURCE_TYPE_REACT}
)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)
public class HelloWorldComponentImpl implements HelloWorldComponent {

   static final String RESOURCE_TYPE = "aem-integrations/components/helloworld";
   static final String RESOURCE_TYPE_REACT = "aem-integrations/components/react/helloworld";

   @ScriptVariable
   private Page currentPage;

   @SlingObject
   private SlingHttpServletRequest request;

   @SlingObject
   private ResourceResolver resourceResolver;

   @OSGiService
   private HelloWorldService helloWorldService;

   @OSGiService
   private RequestService requestService;

   @Self
   @Delegate
   private HelloWorldModel model;

   @Getter
   private String message;

   @Getter
   private List<User> users = new ArrayList<>();

   @Getter
   @JsonIgnore
   private String updateApi;

   @PostConstruct
   protected void init() {
      try {
         this.message = this.helloWorldService.getMessage(this.request, this.currentPage);
         this.users = this.helloWorldService.getUsers();
         this.updateApi = this.requestService.getUpdateApi(this.request);
      } catch (RuntimeException e) {
         log.error("Exception in post construct", e);
      }
   }

   @Override
   @JsonIgnore
   public String getUsersJson() {
      return this.helloWorldService.getUsersJson(this.users);
   }

}