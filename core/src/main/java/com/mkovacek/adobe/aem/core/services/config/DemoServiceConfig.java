package com.mkovacek.adobe.aem.core.services.config;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

// TODO: Please specify name and description!
@ObjectClassDefinition(name = "", description = "")
public @interface DemoServiceConfig {

   @AttributeDefinition(
         name = "A parameter name",
         description = "A parameter description"
   )
   String myParameter() default "default value";

   @AttributeDefinition(
         name = "Scheduler cron expression",
         description = "Example for every 30 seconds: */30 * * * * ?"
   )
   String scheduleExpression() default "*/30 * * * * ?";

}