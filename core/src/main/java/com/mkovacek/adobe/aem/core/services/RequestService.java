package com.mkovacek.adobe.aem.core.services;

import java.util.Map;

import org.apache.sling.api.SlingHttpServletRequest;

public interface RequestService {

   String getUpdateApi(SlingHttpServletRequest request);

}