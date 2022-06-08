package com.ibmix.adobe.aem.core.integrations;

public interface ApiClient {

   ApiClientConfig getConfig();

   <T> T getApiClient(final Class<T> clientClass);

   <T> T getAsyncApiClient(final Class<T> clientClass);

}
