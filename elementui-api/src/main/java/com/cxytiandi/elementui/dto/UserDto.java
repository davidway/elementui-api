package com.cxytiandi.elementui.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("userDto")
public class UserDto {
    private int id;
    private String name;
    private String address;
    private String date;
    private String password;
    private String username;

}
