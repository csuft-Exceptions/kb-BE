package com.kb.kbcommon.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response{

    String code = "200";
    String msg = "success";
    Object data;

    public static Response success(){
        return new Response();
    }
    public static Response success(Object data){
        return new Response(data);
    }
    public Response(Object data){
        this.data = data;
    }
}
