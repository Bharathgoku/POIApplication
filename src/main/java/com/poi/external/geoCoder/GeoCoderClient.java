package com.poi.external.geoCoder;


import com.poi.pojo.GeoCoderPojo;
import com.poi.pojo.GeoCoderPojo.Geometry;
import com.poi.util.HttpRequestUtil;
import java.net.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class GeoCoderClient {

  @Autowired
  HttpRequestUtil httpRequestUtil;

  @Cacheable("geoCoding")
  public Geometry getGeometry(String cityName){

    String url = "https://api.opencagedata.com/geocode/v1/json";
    HttpHeaders headers = getRequestHeaders();

    GeoCoderPojo geoCoderPojo = null;
    URI uri =
        UriComponentsBuilder.fromUriString(url)
            .queryParam("key", "4a1399d28687498f98945dd5d72bdc84")
            .queryParam("q", cityName)
            .build()
            .toUri();

    geoCoderPojo = httpRequestUtil.getApiResponse(uri, headers, GeoCoderPojo.class);

    return (geoCoderPojo != null && !CollectionUtils.isEmpty(geoCoderPojo.getResultList())) ? geoCoderPojo.getResultList().get(0).getGeometry() : null;


  }

  private HttpHeaders getRequestHeaders() {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.setCacheControl("no-cache");
    return headers;
  }

}
