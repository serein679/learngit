package com.shxy.models.paramsBean.fiber;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @description id
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class addFiberParam {
    private String fiberType;
    private String characteristic;
    private List<String> fileParam;
}
