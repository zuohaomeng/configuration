package com.meng.configuration.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author HAO.ZUO
 * @date 2020/3/27--23:31
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserAddVO implements Serializable {
    private static final long serialVersionUID = -2205207062592895865L;

    private Integer id;

    private String username;

    private String name;

    private String password;

    private String passwordAgain;
}
