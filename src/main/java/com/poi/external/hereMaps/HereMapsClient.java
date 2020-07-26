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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class HereMapsClient {

  private static final String APP_ID = "DemoAppId01082013GAL";

  private static final String APP_CODE = "AJKnXv84fjrb0KIHawS0Tg";

  private String hereMapsBaseUrl = "https://places.demo.api.here.com/places/v1/discover/explore";

  @Autowired
  HttpRequestUtil httpRequestUtil;

  @Cacheable("pointsOfInterest")
  public HereMapsPojo getPoiByCategory(Geometry geometry, Category cat) {

    HttpHeaders headers = getRequestHeaders();

    HereMapsPojo hereMapsPojo = null;
    URI uri =
        UriComponentsBuilder.fromUriString(hereMapsBaseUrl)
            .queryParam("at", geometry.toString())
            .queryParam("cat", cat.getCatCode())
            .queryParam("app_id", APP_ID)
            .queryParam("app_code", APP_CODE)
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
