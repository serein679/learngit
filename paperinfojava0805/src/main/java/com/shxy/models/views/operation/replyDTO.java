package com.shxy.models.views.operation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;


/**
 * @description
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class replyDTO extends replyItem implements Serializable {
    private List<replyDTO> children;
}
