package com.poi.external.hereMaps;

import com.poi.enums.Category;
import com.poi.pojo.GeoCoderPojo;
import com.poi.pojo.GeoCoderPojo.Geometry;
import com.poi.pojo.HereMapsPojo;
import com.poi.util.HttpRequestUtil;
import java.net.URI;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class HereMapsClient {

  @Autowired
  HttpRequestUtil httpRequestUtil;

  @Cacheable("pointsOfInterest")
  public HereMapsPojo getPoiByCategory(Geometry geometry, Category cat) {

    String url = "https://places.demo.api.here.com/places/v1/discover/explore";
    HttpHeaders headers = getRequestHeaders();

    HereMapsPojo hereMapsPojo = null;
    URI uri =
        UriComponentsBuilder.fromUriString(url)
            .queryParam("at", geometry.toString())
            .queryParam("cat", cat.getCatCode())
            .queryParam("app_id", "DemoAppId01082013GAL")
            .queryParam("app_code", "AJKnXv84fjrb0KIHawS0Tg")
            .build()
            .toUri();

    hereMapsPojo = httpRequestUtil.getApiResponse(uri, headers, HereMapsPojo.class);

    return hereMapsPojo;
  }

  private HttpHeaders getRequestHeaders() {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.setCacheControl("no-cache");
    return headers;
  }

}
