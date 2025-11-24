package com.Zipdrive.API.dto;
import com.Zipdrive.API.enums.IdType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UploadGovernmentIdRequest {
    private IdType idType;
    private String idNumber;
}

