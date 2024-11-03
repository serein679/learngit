package com.shxy.models.views;

import com.shxy.models.dbBean.historicalEvents;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class historyItem extends historicalEvents implements Serializable {
    private String creator;
    private String pictureLocation;
}
