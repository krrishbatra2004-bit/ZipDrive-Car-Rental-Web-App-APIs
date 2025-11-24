package com.Zipdrive.API.Response;
import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
public class ResultResponse <T>{
    private T result;
    private MetadataResponse metadata;
    public ResultResponse() {}
    public ResultResponse(T result, MetadataResponse metadata) {
        this.result = result;
        this.metadata = metadata;
    }
}
