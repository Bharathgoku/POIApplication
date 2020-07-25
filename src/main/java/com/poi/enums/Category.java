package com.poi.enums;

public enum Category {
  RESTAURANTS("restaurant"),
  CHARGING_STATIONS("ev-charging-station"),
  PARKING_FACILITY("parking-facility");

  private String catCode;

  Category(String catCode){
    this.catCode = catCode;
  }

  public String getCatCode(){
    return this.catCode;
  }


}
