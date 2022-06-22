package com.mkovacek.adobe.aem.core.integrations;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.metatype.annotations.Designate;

import feign.AsyncFeign;
import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;

@Component(service = ApiClient.class, immediate = true)
@Designate(ocd = ApiClientConfig.class, factory = true)
public class ApiClientImpl implements ApiClient {

   private ApiClientConfig config;
   private Feign.Builder feignBuilder;
   private AsyncFeign.AsyncBuilder<Object> asyncFeignBuilder;

   @Activate
   @Modified
   public void init(ApiClientConfig config) {
      this.config = config;
      this.initFeignBuilders();
   }

   @Override public ApiClientConfig getConfig() {
      return this.config;
   }

   @Override
   public <T> T getApiClient(Class<T> clientClass) {
      return this.feignBuilder.target(clientClass, this.config.basePath());
   }

   @Override
   public <T> T getAsyncApiClient(Class<T> clientClass) {
      return this.asyncFeignBuilder.target(clientClass, this.config.basePath());
   }

   private void initFeignBuilders() {
      this.feignBuilder = Feign.builder().encoder(new JacksonEncoder()).decoder(new JacksonDecoder());
      this.asyncFeignBuilder = AsyncFeign.asyncBuilder().encoder(new JacksonEncoder()).decoder(new JacksonDecoder());
   }

}