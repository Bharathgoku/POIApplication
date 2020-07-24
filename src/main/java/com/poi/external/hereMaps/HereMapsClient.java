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

  @Async
  @Cacheable("pointsOfInterest")
  public CompletableFuture<HereMapsPojo> getPoiByCategory(Geometry geometry, Category cat) {
    String url = "https://places.ls.hereapi.com/places/v1/discover/explore";
    HttpHeaders headers = getRequestHeaders();

    HereMapsPojo hereMapsPojo = null;
    URI uri =
        UriComponentsBuilder.fromUriString(url)
            .queryParam("at", geometry.toString())
            .queryParam("cat", cat.getCatCode())
            .queryParam("api_key", "H6XyiCT0w1t9GgTjqhRXxDMrVj9h78ya3NuxlwM7XUs")
            .build()
            .toUri();

    hereMapsPojo = httpRequestUtil.getApiResponse(uri, headers, HereMapsPojo.class);

    return CompletableFuture.completedFuture(hereMapsPojo);
  }

  private HttpHeaders getRequestHeaders() {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.setCacheControl("no-cache");
    return headers;
  }

}
