package com.poi.service;

import com.poi.dto.PointsOfInterestResponseDto;
import com.poi.dto.PointsOfInterestResponseDto.POI;
import com.poi.enums.Category;
import com.poi.exceptions.InternalServerException;
import com.poi.exceptions.InvalidCityException;
import com.poi.external.geoCoder.GeoCoderClient;
import com.poi.external.hereMaps.HereMapsClient;
import com.poi.pojo.GeoCoderPojo.Geometry;
import com.poi.pojo.HereMapsPojo;
import com.poi.pojo.HereMapsPojo.Item;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestClientResponseException;

@Slf4j
@Service
public class IPointsOfInterestService implements PointsOfInterestService{

  @Autowired
  private GeoCoderClient geoCoderClient;

  @Autowired
  private HereMapsClient hereMapsClient;

  @Override
  public PointsOfInterestResponseDto getPoiByCityName(String cityName) throws RuntimeException {

    PointsOfInterestResponseDto pointsOfInterestResponseDto = null;

    Geometry geometry = geoCoderClient.getGeometry(cityName);

    if(geometry == null){
      log.error("Invalid city name - " + cityName);
      throw new InvalidCityException("Invalid city , city Name - " + cityName);
    }

    try {
      CompletableFuture<List<POI>> topRestaurants = getTopK(geometry,  3, Category.RESTAURANTS);
      CompletableFuture<List<POI>> topChargingStations = getTopK(geometry, 3, Category.CHARGING_STATIONS);
      CompletableFuture<List<POI>> topParkingSpots = getTopK(geometry, 3, Category.PARKING_FACILITY);

      Map<Category, List<POI>> poiMap = new HashMap<Category, List<POI>>();

      poiMap.put(Category.RESTAURANTS, topRestaurants.get());
      poiMap.put(Category.CHARGING_STATIONS, topChargingStations.get());
      poiMap.put(Category.PARKING_FACILITY, topParkingSpots.get());

      pointsOfInterestResponseDto = PointsOfInterestResponseDto.createResponse(cityName, geometry, poiMap);

    }catch (Exception e) {
      log.error("Error occured in POI Service, cityName - " + cityName + ", error message - " + e.getMessage());
      throw new InternalServerException(e.getMessage());
    }

    return pointsOfInterestResponseDto;
  }

  @Async
  public CompletableFuture<List<POI>> getTopK(Geometry geometry, Integer K, Category category){
    HereMapsPojo hereMapsPojo = null;
    try{
       hereMapsPojo = hereMapsClient.getPoiByCategory(geometry, category);
    }catch(RestClientResponseException e){
      log.error("RestClient Exception, error message - " + e.getMessage());
      throw new InternalServerException(e.getMessage());
    }
    List<POI> poiList = new ArrayList<>();

    if(hereMapsPojo != null && hereMapsPojo.getResults() != null && !CollectionUtils.isEmpty(hereMapsPojo.getResults().getItems())){

      List<Item> items = hereMapsPojo.getResults().getItems();
      // here maps results are ranked based on category distance, so no need to loop over all the poi.
      poiList = items.stream().limit(K).map(POI::getObject).sorted(
          Comparator.comparingInt(POI::getDistance)).collect(Collectors.toList());
    }else{
      log.info("Empty Here maps client object");
    }
    return CompletableFuture.completedFuture(poiList);

  }
}
