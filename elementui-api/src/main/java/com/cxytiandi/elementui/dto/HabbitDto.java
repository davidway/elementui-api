package com.cxytiandi.elementui.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HabbitDto {
        int id;
        String content;
        String title;
        String imgUrl;
        BigDecimal money;
        int userId;

}
