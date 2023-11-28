package com.beat.back.pojo;

import lombok.Data;

@Data
public class ResponseData<T> {
    int code;
    T body;
    String message;

    public  ResponseData (int code,T body,String message){
         this.code = code;
         this.message = message;
         this.body = body;
    }
    public static ResponseData  successResponse(String message,Object bodyData){
        ResponseData responseData = new ResponseData(200,bodyData,message);
        return  responseData;
    }
    public static ResponseData failedResponse(String failedMessage,Object failedBody){

        ResponseData responseData = new ResponseData(500,failedBody,failedMessage);
        return  responseData;
    }

    public  static  ResponseData repeatDataResponse(String repeatMessage){
        ResponseData responseData = new ResponseData(300,null,repeatMessage);
        return  responseData;
    }

}
