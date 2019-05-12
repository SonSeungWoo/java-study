package me.seungwoo.user;

import lombok.Data;

import java.time.LocalDate;

/**
 * Created by Leo.
 * User: sonseungwoo
 * Date: 2019-04-05
 * Time: 23:37
 */
@Data
public class UserDto {
    private long id;

    private String userName;

    private String password;

    private String email;

    private LocalDate insertDate;

}
