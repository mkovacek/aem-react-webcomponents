package com.mkovacek.adobe.aem.core.components.helloworld.impl;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.Designate;

import com.day.cq.wcm.api.Page;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.mkovacek.adobe.aem.core.components.helloworld.config.HelloWorldServiceConfig;
import com.mkovacek.adobe.aem.core.integrations.ApiClient;
import com.mkovacek.adobe.aem.core.integrations.faker.FakerApi;
import com.mkovacek.adobe.aem.core.integrations.faker.dto.Response;
import com.mkovacek.adobe.aem.core.integrations.faker.dto.User;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component(service = HelloWorldService.class, immediate = true)
@Designate(ocd = HelloWorldServiceConfig.class)
public class HelloWorldService {

   @Reference(target = "(name=fakerApi)")
   private ApiClient apiClient;

   private HelloWorldServiceConfig config;

   private final Cache<String, List<User>> cache = Caffeine.newBuilder()
                                                           .maximumSize(1000)
                                                           .expireAfterWrite(1, TimeUnit.MINUTES)
                                                           .recordStats()
                                                           .build();

   @Activate
   @Modified
   private void activate(HelloWorldServiceConfig config) {
      this.config = config;
   }

   public String getMessage(SlingHttpServletRequest request, Page page) {
      Resource resource = request.getResource();
      return "Hello World!\n"
            + "Resource type is: " + resource.getResourceType() + "\n"
            + "Current page is:  " + page.getPath() + "\n"
            + "Config param is:  " + this.config.myParameter() + "\n"
            + "Current time:  " + LocalDateTime.now() + "\n";
   }

   public List<User> getUsers() {
      return this.cache.get("users", k -> this.fetchUsers());
   }

   private List<User> fetchUsers() {
      try {
         FakerApi fakerApi = this.apiClient.getApiClient(FakerApi.class);
         return Optional.ofNullable(fakerApi.getUsers())
                        .map(Response::getData)
                        .orElse(Collections.emptyList())
                        .stream()
                        .sorted(Comparator.comparing(User::getLastname))
                        .collect(Collectors.toList());
      } catch (FeignException e) {
         log.error("Exception from FakerApi {}", e.contentUTF8());
      } catch (RuntimeException e) {
         log.error("Exception during calling FakerApi {}", e);
      }

      return Collections.emptyList();
   }

   public String getUsersJson(List<User> users) {
      try {
         ObjectMapper objectMapper = new ObjectMapper();
         // potential issues with substitute rules in Dispatcher
         // if so enable pretty print
         return objectMapper.writeValueAsString(users);
      } catch (JsonProcessingException e) {
         log.error("Error during exporting json: {}", e);
      }
      return "{}";
   }

}