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
import java.time.LocalTime;
import java.util.Currency;
import java.util.Locale;
import java.util.Optional;

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
    public void dateTest() {
        User user = new User("seungwoo", "seung0000",
                "seungwoo@test.co.kr", LocalDate.now());
        UserDto userDto = objectMapper.convertValue(user, UserDto.class);
        System.out.println(userDto);
    }

    @Test
    public void test() {
        /*System.out.println(Locale.KOREA);
        Locale locale = new Locale("ko", "KR");
        Currency currency = Currency.getInstance(locale);
        System.out.println(currency.getCurrencyCode());
        System.out.println(currency.getNumericCode());*/
        String locale = "ko_KR";
        String[] array = locale.split("_");
        System.out.println(array.length);
        String language = array.length > 0 ? array[0] : "ko";
        String country = array.length > 1 ? array[1] : "KR";
        Currency currency = Currency.getInstance(new Locale(language, country));
        System.out.println(currency);
    }

    @Test
    public void 시간비교() {
        String costTime = null;
        String pickStr = "03:00";
        String timeStr = "03:01";
        int costMin = Integer.parseInt(Optional.ofNullable(costTime).orElse("0"));
        int pickTime = getTime(pickStr);
        int compareTime = getTime(timeStr);

        System.out.println(pickTime - costMin <= compareTime && compareTime < pickTime);

    }

    private int getTime(String strTime) {
        String[] array = strTime.split(":");
        int hour = Integer.parseInt(array.length > 0 ? array[0] : "0");
        int minute = Integer.parseInt(array.length > 1 ? array[1] : "0");
        LocalTime targetTime = LocalTime.of(hour, minute);
        int min = (targetTime.getHour() * 60) + targetTime.getMinute();
        return min;
    }

    @Test
    public void 테스트() {
        StringBuilder sb1 = new StringBuilder("ICNSEASEABNA");
        StringBuilder sb2 = new StringBuilder("ICNSEASEABNA");


        System.out.println(sb1.toString().equals(sb2.toString()));

    }
}
