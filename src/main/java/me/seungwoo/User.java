package me.seungwoo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * Created by Leo.
 * User: sonseungwoo
 * Date: 2019-04-05
 * Time: 23:18
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column
    private String userName;

    @Column
    private String password;

    @Column
    private String email;

    @Column
    private LocalDate insertDate;

    public User(String userName, String password, String email, LocalDate insertDate){
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.insertDate = insertDate;
    }
}
