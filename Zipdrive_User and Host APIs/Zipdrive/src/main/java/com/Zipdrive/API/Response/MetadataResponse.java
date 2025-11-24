package com.Zipdrive.API.Response;
import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
public class MetadataResponse {
    private String code;
    private String message;
    private int noOfRecords;
    public MetadataResponse() {
    }
    public MetadataResponse(String code, String message, int noOfRecords) {
        this.code = code;
        this.message = message;
        this.noOfRecords = noOfRecords;
    }
    public static MetadataResponse success() {
        return new MetadataResponse("200", "Success", 1);
    }
    public static MetadataResponse badRequest() {
        return new MetadataResponse("400", "Bad Request", 0);
    }
    public static MetadataResponse internalServerError() {
        return new MetadataResponse("500", "Internal Server Error", 0);
    }
}
