package me.seungwoo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.StopWatch;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by Leo.
 * User: ssw
 * Date: 2019-04-08
 * Time: 17:57
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class LocationDistanceTests {

    private static final Logger logger = LoggerFactory.getLogger(LocationDistanceTests.class);

    @Test
    public void distanceTest() {
        // 마일(Mile) 단위
        //LATITUDE:위도, LONGITUDE:경도
        double distanceKiloMeter =
                calDistance(37.504198, 127.047967, 37.501025, 127.037701);
        logger.info("distanceKiloMeter : {}", distanceKiloMeter);

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Locale[] cosas = Locale.getAvailableLocales();
        for (int i = 0; i < cosas.length; i++) {
            String[] lang = String.valueOf(cosas[i]).split("_");
            if(lang.length > 1){
                if(lang[1].equals("KR")){
                    logger.info("cosas : {}",cosas[i]);
                    logger.info("lang {} :,  CON {}",lang[0], lang[1]);
                }
            }
        }
        stopWatch.stop();
        logger.info("start : {}", stopWatch.getTotalTimeSeconds());

        List<Locale> cosasList = Arrays.asList(Locale.getAvailableLocales()).stream().filter(lang -> lang.getCountry().equals("US"))
                .collect(Collectors.toList());
        Locale language = Locale.getDefault();
        if(!cosasList.isEmpty()){
            language = Optional.ofNullable(cosasList.get(0)).orElse(Locale.getDefault());
        }
        logger.info("Language : {}",language.getLanguage());
        logger.info("Country : {}",language.getCountry());

    }

    // This function converts decimal degrees to radians
    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    // This function converts radians to decimal degrees
    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }


    private static double calDistance(double lat1, double lon1, double lat2, double lon2){
        double theta, dist;
        theta = lon1 - lon2;
        dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);

        dist = dist * 60 * 1.1515;
        dist = dist * 1.609344;     // 단위 mile 에서 km 변환
        //dist = dist * 1000.0;     // 단위  km 에서 m 로 변환.

        return dist;
    }

}
