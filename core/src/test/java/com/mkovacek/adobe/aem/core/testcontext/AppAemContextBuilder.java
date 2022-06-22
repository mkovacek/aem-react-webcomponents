package com.mkovacek.adobe.aem.core.testcontext;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextBuilder;
import io.wcm.testing.mock.aem.junit5.AemContextCallback;
import java.util.Collections;
import java.util.Map;
import org.apache.sling.testing.mock.sling.ResourceResolverType;

import static com.adobe.cq.wcm.core.components.testing.mock.ContextPlugins.CORE_COMPONENTS;
import static org.apache.sling.testing.mock.caconfig.ContextPlugins.CACONFIG;

/**
 * Sets up {@link AemContext} for unit tests in this application.
 */
public class AppAemContextBuilder {

   private final AemContext aemContext;

   public AppAemContextBuilder() {
      this.aemContext = this.newAemContextBuilder(ResourceResolverType.RESOURCERESOLVER_MOCK).build();
   }

   public AppAemContextBuilder(ResourceResolverType resourceResolverType) {
      this.aemContext = this.newAemContextBuilder(resourceResolverType).build();
   }

   public AemContext build() {
      return this.aemContext;
   }

   public AemContextBuilder newAemContextBuilder(ResourceResolverType resourceResolverType) {
      return new AemContextBuilder(resourceResolverType)
            .plugin(CACONFIG)
            .plugin(CORE_COMPONENTS)
            .afterSetUp(SETUP_CALLBACK);
   }

   /**
    * Custom set up rules required in all unit tests.
    */
   private final AemContextCallback SETUP_CALLBACK = context -> {
      // custom project initialization code for every unit test
   };

   public AppAemContextBuilder loadResource(String classPathResource, String destinationPath) {
      this.aemContext.load().json(classPathResource, destinationPath);
      return this;
   }

   public <T> AppAemContextBuilder registerService(Class<T> serviceClass, T service) {
      this.aemContext.registerService(serviceClass, service, Collections.emptyMap());
      return this;
   }

   public <T> AppAemContextBuilder registerService(Class<T> serviceClass, T service, Map<String, Object> config) {
      this.aemContext.registerService(serviceClass, service, config);
      return this;
   }

   public <T> AppAemContextBuilder registerInjectActivateService(T service) {
      this.aemContext.registerInjectActivateService(service, Collections.emptyMap());
      return this;
   }

   public <T> AppAemContextBuilder registerInjectActivateService(T service, Map<String, Object> config) {
      this.aemContext.registerInjectActivateService(service, config);
      return this;
   }

}
