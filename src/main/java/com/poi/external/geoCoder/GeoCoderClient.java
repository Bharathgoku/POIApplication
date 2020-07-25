package com.poi.external.geoCoder;


import com.poi.pojo.GeoCoderPojo;
import com.poi.pojo.GeoCoderPojo.Geometry;
import com.poi.util.HttpRequestUtil;
import java.net.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

  @Value("${geo-coding.api.key}")
  private static String API_KEY;

  @Value("${geo-coding.api.base.url}")
  private String geoCodingBaseUrl;

  @Cacheable("geoCoding")
  public Geometry getGeometry(String cityName){

    HttpHeaders headers = getRequestHeaders();

    GeoCoderPojo geoCoderPojo = null;
    URI uri =
        UriComponentsBuilder.fromUriString(geoCodingBaseUrl)
            .queryParam("key", API_KEY)
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
