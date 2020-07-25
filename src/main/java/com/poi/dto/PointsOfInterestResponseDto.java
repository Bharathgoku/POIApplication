package com.poi.dto;

import ch.qos.logback.core.joran.spi.NoAutoStart;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.poi.enums.Category;
import com.poi.pojo.GeoCoderPojo.Geometry;
import com.poi.pojo.HereMapsPojo.Item;
import com.poi.service.PointsOfInterestService;
import java.util.List;
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
  private Map<Category, List<POI>> poiMap;

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  @Builder
  public static class POI{

    private Geometry geometry;

    private Integer distance;

    private String name;

    private String href;

    public static POI getObject(Item item){
      return POI.builder()
              .distance(item.getDistance())
              .name(item.getTitle())
              .href(item.getHref())
              .geometry(Geometry.getObject(item.getPosition()))
              .build();
    }

  }

  public static PointsOfInterestResponseDto createResponse(String cityName, Geometry geometry, Map<Category, List<POI>> poiMap){
    PointsOfInterestResponseDto pointsOfInterestResponseDto = PointsOfInterestResponseDto.builder()
                                                                    .cityName(cityName)
                                                                    .geometry(geometry)
                                                                    .poiMap(poiMap)
                                                                    .build();

    return pointsOfInterestResponseDto;

  }

}
