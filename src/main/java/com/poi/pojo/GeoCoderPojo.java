package com.poi.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class GeoCoderPojo {


  @JsonProperty("results")
  private List<Result> resultList;

  @Data

  @NoArgsConstructor
  @AllArgsConstructor
  public static class Result{

    private Geometry geometry;

  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Geometry{
    private Double lat;
    private Double lng;

    @Override
    public String toString(){
      return new String(lat.toString() + "," + lng.toString());
    }
  }

}
