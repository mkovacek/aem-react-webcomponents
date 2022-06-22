package com.mkovacek.adobe.aem.core.services.impl;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.osgi.service.component.annotations.Component;

import com.mkovacek.adobe.aem.core.services.RequestService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component(service = RequestService.class, immediate = true)
public class RequestServiceImpl implements RequestService {

   @Override
   public String getUpdateApi(SlingHttpServletRequest request) {
      String resourcePath = request.getResource().getPath();
      String suffix = request.getRequestPathInfo().getSuffix();
      if (StringUtils.isBlank(suffix)) {
         return StringUtils.join(resourcePath, ".model.json");
      } else {
         String suffixWithoutExtensions = StringUtils.remove(suffix, ".html");
         suffixWithoutExtensions = StringUtils.remove(suffixWithoutExtensions, ".json");
         return StringUtils.join(resourcePath, ".model.json", suffixWithoutExtensions, ".json");
      }
   }

}