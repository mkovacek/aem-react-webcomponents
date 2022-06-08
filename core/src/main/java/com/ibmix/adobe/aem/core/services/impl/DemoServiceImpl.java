package com.ibmix.adobe.aem.core.services.impl;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.Designate;

import com.day.cq.commons.Externalizer;
import com.ibmix.adobe.aem.core.services.DemoService;
import com.ibmix.adobe.aem.core.services.config.DemoServiceConfig;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component(service = DemoService.class, immediate = true)
@Designate(ocd = DemoServiceConfig.class)
public class DemoServiceImpl implements DemoService {

   @Reference
   private Externalizer externalizer;

   private DemoServiceConfig config;

   @Activate
   private void activate(DemoServiceConfig config) {
      this.config = config;
      log.info("activate");
   }

   @Modified
   private void modify(DemoServiceConfig config) {
      this.config = config;
      log.info("modify");
   }

   @Deactivate
   private void deactivate() {
      log.info("deactivate");
   }

}