package com.ibmix.adobe.aem.core.testcontext;

import com.adobe.granite.rest.Constants;
import java.io.InputStream;
import java.io.InputStreamReader;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import wiremock.com.fasterxml.jackson.databind.JsonNode;
import wiremock.com.fasterxml.jackson.databind.ObjectMapper;

@UtilityClass
public final class TestUtil {

   private static final ObjectMapper objectMapper = new ObjectMapper();

   @SneakyThrows
   public static String getJson(Class<?> clazz, String resourcePath) {
      final InputStream inputStream = clazz.getResourceAsStream(resourcePath);
      final JsonNode jsonNode =
            objectMapper.readValue(new InputStreamReader(inputStream, Constants.DEFAULT_CHARSET), JsonNode.class);
      inputStream.close();
      return objectMapper.writeValueAsString(jsonNode);
   }

}