package com.ibmix.adobe.aem.core.testcontext;

import java.util.HashMap;
import java.util.Map;

public class OsgiConfigs {

   public static Map<String, Object> fakerApiConfig = new HashMap<String, Object>() {{
      put("name", "fakerApi");
      put("basePath", "http://localhost:18081");
      put("apiKey", "abcd12345");
   }};

}
