package com.shxy.models.paramsBean.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

/**
 * @description id
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class changeSon {
    private Integer id;
    private ArrayList<Integer> list;
}
