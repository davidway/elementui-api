package com.cxytiandi.elementui.vo;

import lombok.Data;
import lombok.ToString;

@Data
@ToString

public class HabbitVo {
    int id;
    String imgUrl;
    int userId;
    String title;
    long likeNum;
    long followNum;
    String content;

}
