package com.poi.enums;

public enum Category {
  RESTAURANTS("restaurants"),
  CHARGING_STATIONS("charging_stations"),
  PARKING_SPOTS("parking_spots");

  private String catCode;

  Category(String catCode){
    this.catCode = catCode;
  }

  public String getCatCode(){
    return this.catCode;
  }


}
