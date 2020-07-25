package com.poi.service;

import com.poi.dto.PointsOfInterestResponseDto;
import com.poi.exceptions.InternalServerException;
import com.poi.exceptions.InvalidCityException;
import org.springframework.web.client.HttpServerErrorException.InternalServerError;

public interface PointsOfInterestService {


  PointsOfInterestResponseDto getPoiByCityName(String cityName) throws InternalServerException, InvalidCityException, RuntimeException;

}
