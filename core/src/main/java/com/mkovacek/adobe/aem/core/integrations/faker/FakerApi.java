package com.mkovacek.adobe.aem.core.integrations.faker;

import com.mkovacek.adobe.aem.core.integrations.faker.dto.Address;
import com.mkovacek.adobe.aem.core.integrations.faker.dto.Response;
import com.mkovacek.adobe.aem.core.integrations.faker.dto.User;

import feign.Param;
import feign.RequestLine;

public interface FakerApi {

   @RequestLine("GET /v1/users")
   Response<User> getUsers();

   @RequestLine("GET /v1/addresses?_quantity={quantity}")
   Response<Address> getAddresses(@Param("quantity") long quantity);

}
