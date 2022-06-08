package com.ibmix.adobe.aem.core.components.helloworld.config;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(name = "Hello World Config", description = "Hello World Config")
public @interface HelloWorldServiceConfig {

   @AttributeDefinition(
         name = "A parameter name",
         description = "A parameter description"
   )
   String myParameter() default "default value";

}