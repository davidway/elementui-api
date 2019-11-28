package com.cxytiandi.elementui.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Habbit {
    int id;
    String imageUrl;
    int userId;
    String title;
    long likeNum;
    long followNum;
    String content;
}
