package com.Zipdrive.API.Controller;
import com.Zipdrive.API.Response.ResultResponse;
import com.Zipdrive.API.Service.GovtIdService;
import com.Zipdrive.API.dto.UploadGovernmentIdRequest;
import com.Zipdrive.API.enums.IdType;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
@Data
@Getter
@Setter
@RestController
@RequestMapping("/api/govt-id")
@RequiredArgsConstructor
public class GovtIdController {
    private final GovtIdService govtIdService;
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResultResponse<String> uploadGovtId(
            @RequestParam("userId") Long userId,
            @RequestParam("idType") IdType idType,
            @RequestParam("idNumber") String idNumber,
            @RequestParam("file") MultipartFile file) {
        UploadGovernmentIdRequest request = new UploadGovernmentIdRequest();
        request.setIdType(idType);
        request.setIdNumber(idNumber);
        return govtIdService.uploadGovernmentId(userId,request,file);
    }
}

