package com.hfut.bs.gateway.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * Created by chenxiong on 18/12/3.
 */
@Data
public class RegisterDTO {

    @NotNull
    private String mobile;

    @NotNull
    @Length(min = 8)
    private String password;

    private String username;

    public RegisterDTO() {
    }

}
