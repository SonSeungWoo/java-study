package me.seungwoo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Leo.
 * User: sonseungwoo
 * Date: 2019-03-24
 * Time: 14:29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person {

    private String firstName;

    private String lastName;

    private String email;

}
