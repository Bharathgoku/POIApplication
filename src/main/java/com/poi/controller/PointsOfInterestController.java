package com.poi.controller;

import com.poi.service.PointsOfInterestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/poi")
public class PointsOfInterestController {

  @Autowired
  PointsOfInterestService pointsOfInterestService;

  @RequestMapping(value = "", method = RequestMethod.GET)
  public void getPoiByCityName(@RequestParam(value = "city_name") String cityName) {
    pointsOfInterestService.getPoiByCityName(cityName);
  }

}
