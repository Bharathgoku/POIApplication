package com.poi.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseResponse<T> {

  private Integer code;
  private String message;
  private T data;

  public BaseResponse() {
    this.code = 200;
    this.message = "OK";
  }

  public BaseResponse(T data) {
    this.code = 200;
    this.message = "OK";
    this.data = data;
  }

  public BaseResponse(Integer code, String message, T data) {
    this.code = code;
    this.message = message;
    this.data = data;
  }

  public Integer getCode() {
    return this.code;
  }

  public BaseResponse setCode(Integer code) {
    this.code = code;
    return this;
  }

  public String getMessage() {
    return this.message;
  }

  public BaseResponse setMessage(String message) {
    this.message = message;
    return this;
  }

}
