package com.poi.controller;

import com.poi.dto.BaseResponse;
import com.poi.dto.PointsOfInterestResponseDto;
import com.poi.dto.ServiceResponse;
import com.poi.exceptions.InvalidCityException;
import com.poi.service.PointsOfInterestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException.InternalServerError;

@Slf4j
@RestController
@RequestMapping(value = "/poi")
public class PointsOfInterestController {

  @Autowired
  PointsOfInterestService pointsOfInterestService;

  @GetMapping(value = "/get_top")
  public ServiceResponse<BaseResponse<PointsOfInterestResponseDto>> getPoiByCityName(@RequestParam(value = "city_name") String cityName) throws RuntimeException{
    try{
      return new ServiceResponse<>(new BaseResponse<>(pointsOfInterestService.getPoiByCityName(cityName)));
    }catch(InvalidCityException e){
      return new ServiceResponse<>(new BaseResponse<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null), HttpStatus.BAD_REQUEST);
    }catch(InternalServerError e){
      return new ServiceResponse<>(new BaseResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
    }

  }

}
