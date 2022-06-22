package com.mkovacek.adobe.aem.core.testcontext;

import java.util.Map;

import com.github.tomakehurst.wiremock.matching.StringValuePattern;

import lombok.experimental.UtilityClass;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.givenThat;
import static com.github.tomakehurst.wiremock.client.WireMock.post;

@UtilityClass
public class WireMockUtil {

    public static void mockExpectedGetResponse(String api, int status, String jsonResponseResourcePath, Map<String, StringValuePattern> queryParams) {
        givenThat(
          get(api)
            .withQueryParams(queryParams)
            .willReturn(
              aResponse()
              .withStatus(status)
              .withBody(TestUtil.getJson(WireMockUtil.class, jsonResponseResourcePath))
            ));
    }

    public static void mockExpectedPostResponse(String api, int status, String jsonResponseResourcePath) {
        givenThat(
          post(api)
            .willReturn(
              aResponse()
                .withStatus(status)
                .withBody(TestUtil.getJson(WireMockUtil.class, jsonResponseResourcePath))
            ));
    }

}
