package me.seungwoo;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Leo.
 * User: sonseungwoo
 * Date: 2019-04-05
 * Time: 23:24
 */
public interface UserRepository extends JpaRepository<User, Long> {

}
