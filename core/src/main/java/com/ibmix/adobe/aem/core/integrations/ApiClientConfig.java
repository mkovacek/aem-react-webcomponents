package com.ibmix.adobe.aem.core.integrations;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(name = "ApiClientConfig", description = "Configurations related to Api Clients")
public @interface ApiClientConfig {

   @AttributeDefinition(
         name = "API Name",
         description = "Name is used as unique identity to get correct osgi factory instance"
   )
   String name() default "";

   @AttributeDefinition(name = "API Base Path")
   String basePath() default "http://localhost:8080/api/v1";

   @AttributeDefinition(name = "API Key")
   String apiKey() default "";

}
