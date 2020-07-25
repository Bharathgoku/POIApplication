package com.poi.pojo;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess.Item;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HereMapsPojo {


  private Results results;

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Results{
    private List<Item> items;
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Item{

    private List<Double> position;
    private Integer distance;
    private String title;
    private String href;

  }





}
