package com.poi.service;

import com.poi.enums.Category;
import com.poi.external.geoCoder.GeoCoderClient;
import com.poi.external.hereMaps.HereMapsClient;
import com.poi.pojo.GeoCoderPojo.Geometry;
import com.poi.pojo.HereMapsPojo;
import java.util.concurrent.CompletableFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IPointsOfInterestService implements PointsOfInterestService{

  @Autowired
  private GeoCoderClient geoCoderClient;

  @Autowired
  private HereMapsClient hereMapsClient;

  @Override
  public void getPoiByCityName(String cityName) throws Exception {

    Geometry geometry = geoCoderClient.getGeometry(cityName);

    if(geometry == null){
      throw new Exception();
    }

    // call for restaurants
    CompletableFuture<HereMapsPojo> restaurants = hereMapsClient.getPoiByCategory(geometry, Category.RESTAURANTS);

    // get the top 3

    // call for charging stations
    CompletableFuture<HereMapsPojo> charginStations = hereMapsClient.getPoiByCategory(geometry, Category.CHARGING_STATIONS);

    // get top 3

    // call for parking spots
    CompletableFuture<HereMapsPojo> parkingSpots = hereMapsClient.getPoiByCategory(geometry, Category.PARKING_SPOTS);

    // get top 3
  }
}
