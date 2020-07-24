package com.poi.dto;

import ch.qos.logback.core.joran.spi.NoAutoStart;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.poi.enums.Category;
import com.poi.pojo.GeoCoderPojo.Geometry;
import com.poi.service.PointsOfInterestService;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PointsOfInterestResponseDto {

  @JsonProperty("city_name")
  private String cityName;

  private Geometry geometry;

  @JsonProperty("points_of_interests")
  private Map<Category, POI> poiMap;

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  @Builder
  public static class POI{

    private Integer distance;

    private String name;

  }

  public static PointsOfInterestResponseDto createResponse(String cityName, Geometry geometry){
    PointsOfInterestResponseDto pointsOfInterestResponseDto = PointsOfInterestResponseDto.builder()
                                                                    .cityName(cityName)
                                                                    .geometry(geometry)
                                                                    .build();

    return pointsOfInterestResponseDto;

  }

}
