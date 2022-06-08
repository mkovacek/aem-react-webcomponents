package com.ibmix.adobe.aem.core.components.helloworld;

import java.util.List;

import com.ibmix.adobe.aem.core.integrations.faker.dto.User;

public interface HelloWorldComponent {

   String getTitle();

   String getText();

   String getMessage();

   List<User> getUsers();

   String getUsersJson();

   String getUpdateApi();

}