package me.seungwoo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

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
        double distanceMile =
                distance(37.504198, 127.047967, 37.501025, 127.037701, "");

        // 미터(Meter) 단위
        double distanceMeter =
                distance(37.504198, 127.047967, 37.501025, 127.037701, "meter");

        // 킬로미터(Kilo Meter) 단위
        double distanceKiloMeter =
                distance(37.504198, 127.047967, 37.501025, 127.037701, "kilometer");

        logger.info("distanceMile : {}", distanceMile);
        logger.info("distanceMeter : {}", distanceMeter);
        logger.info("distanceKiloMeter : {}", distanceKiloMeter);
    }

    private static double distance(double lat1, double lon1, double lat2, double lon2, String unit) {

        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));

        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;

        if (unit == "kilometer") {
            dist = dist * 1.609344;
        } else if (unit == "meter") {
            dist = dist * 1609.344;
        }

        return (dist);
    }


    // This function converts decimal degrees to radians
    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    // This function converts radians to decimal degrees
    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }

}
