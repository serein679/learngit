package com.shxy.models.paramsBean.component;

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
public class addComponentParam {
    private String componentName;
    private Integer componentType;
    private String description;
    private List<String> fileParam;
}
