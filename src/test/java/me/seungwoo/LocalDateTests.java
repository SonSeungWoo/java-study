package me.seungwoo;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import me.seungwoo.user.User;
import me.seungwoo.user.UserDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

/**
 * Created by Leo.
 * User: sonseungwoo
 * Date: 2019-04-05
 * Time: 23:26
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class LocalDateTests {

    ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    @Test
    public void dateTest(){
        User user = new User("seungwoo", "seung0000",
                "seungwoo@test.co.kr", LocalDate.now());
        UserDto userDto = objectMapper.convertValue(user, UserDto.class);
        System.out.println(userDto);
    }
}
