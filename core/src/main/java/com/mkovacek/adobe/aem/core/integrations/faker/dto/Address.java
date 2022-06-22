package com.mkovacek.adobe.aem.core.integrations.faker.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Address {

   private long id;
   private String street;
   private String streetName;
   private String buildingNumber;
   private String city;
   private String zipcode;
   private String country;

   @JsonProperty("county_code")
   private String countyCode;
   private Double latitude;
   private Double longitude;

}
