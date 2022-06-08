package com.ibmix.adobe.aem.core.integrations.faker.dto;

import java.util.List;

import lombok.Data;

@Data
public class Response<T> {

   private String status;
   private int code;
   private long total;
   private List<T> data;

}
