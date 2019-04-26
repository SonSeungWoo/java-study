package me.seungwoo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Leo.
 * User: sonseungwoo
 * Date: 2019-04-01
 * Time: 21:59
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class studyChapter02Tests {

    @Test
    public void studyChapter02_Test01() {
        StudyChapter02 studyChapter02 = new StudyChapter02();
        System.out.println(studyChapter02.menuName());
    }

    @Test
    public void studyChapter02_Test02() {
        List<String> title = Arrays.asList("son", "seung", "woo");
        Stream<String> s = title.stream();
        s.forEach(System.out::println); //title 각 단어 출력
        s.forEach(System.out::println); //스트림이 이미 소비되었거나 닫힘 에러 발생
    }

    @Test
    public void studyChapter02_Test03() {
        List<StudyChapter02.Dish> menu = StudyChapter02.menu();
        //컬렉션 for-each 외부반복
        List<String> names1 = new ArrayList<>();
        for (StudyChapter02.Dish d : menu) {
            names1.add(d.getName());
        }

        //컬렉션 내부적으로 숨겨졌던 반복자를 사용한 외부반복
        List<String> names2 = new ArrayList<>();
        Iterator<StudyChapter02.Dish> iterator = StudyChapter02.menu().iterator();
        while (iterator.hasNext()) {
            StudyChapter02.Dish dish = iterator.next();
            names2.add(dish.getName());
        }

        //스트림 내부반복
        List<String> names3 = StudyChapter02.menu().stream()
                .map(StudyChapter02.Dish::getName)
                .collect(Collectors.toList());
    }

    @Test
    public void studyChapter02_Test04() {
        List<StudyChapter02.Dish> menu = StudyChapter02.menu();
        //쇼트서킷, 루프퓨전
        List<String> names =
                menu.stream() //중간연
                        .filter(d -> {
                            System.out.println("filtering : " + d.getName());
                            return d.getCalories() > 300;
                        })
                        .map(d -> {
                            System.out.println("mapping : " + d.getName());
                            return d.getName();
                        })
                        .limit(3)
                        .collect(Collectors.toList()); //최종연산
        System.out.println(names);
    }


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MappingData {
        private String compCode;
        private String hotelCode;
    }

    public enum VendorCode {

        VENDOR_AMADEUS("15001"),
        VENDOR_SABRE("15002"),
        VENDOR_EXPEDIA("15003"),
        VENDOR_HOTELBEDS("15004"),
        VENDOR_FITRUUMS("15005");

        private String vendorCode;

        VendorCode(String vendorCode) {
            this.vendorCode = vendorCode;
        }

        public String getVendorCode() {
            return vendorCode;
        }
    }

    private static final String VENDOR_AMADEUS = "15001";
    private static final String VENDOR_SABRE = "15002";
    private static final String VENDOR_EXPEDIA = "15003";
    private static final String VENDOR_HOTELBEDS = "15004";
    private static final String VENDOR_FITRUUMS = "15005";

    @Test
    public void studyChapter02_Test05() {

        List<MappingData> dataList = new ArrayList<>();
        dataList.add(new MappingData(VENDOR_AMADEUS, "1081451"));
        dataList.add(new MappingData(VENDOR_SABRE, "1081462"));
        dataList.add(new MappingData(VENDOR_EXPEDIA, "1050187"));
        dataList.add(new MappingData(VENDOR_HOTELBEDS, "1050184"));
        dataList.add(new MappingData(VENDOR_FITRUUMS, "1050185"));
        //더좋은 방법...
        MappingData data = dataList.stream()
                .filter(d -> d.compCode.equals(VendorCode.VENDOR_EXPEDIA.getVendorCode()))
                .findAny()
                .orElse(
                        dataList.stream()
                                .filter(d -> d.compCode.equals(VendorCode.VENDOR_HOTELBEDS.getVendorCode()))
                                .findAny()
                                .orElse(
                                        dataList.stream()
                                                .filter(d -> d.compCode.equals(VendorCode.VENDOR_FITRUUMS.getVendorCode()))
                                                .findAny()
                                                .orElse(
                                                        dataList.stream()
                                                                .filter(d -> d.compCode.equals(VendorCode.VENDOR_AMADEUS.getVendorCode()))
                                                                .findAny()
                                                                .orElse(
                                                                        dataList.stream()
                                                                                .filter(d -> d.compCode.equals(VendorCode.VENDOR_SABRE.getVendorCode()))
                                                                                .findAny()
                                                                                .orElse(null)
                                                                )
                                                )
                                )
                );

    }

    @Test
    public void test() throws JSONException {
        String data =  "{\"carLocations\":[  \n" +
                "   {  \n" +
                "      \"address\":\"string\",\n" +
                "      \"companyCode\":\"string\",\n" +
                "      \"companyName\":\"string\",\n" +
                "      \"latitude\":0,\n" +
                "      \"locationCode\":\"string\",\n" +
                "      \"locationName\":\"string\",\n" +
                "      \"locationNameEn\":\"string\",\n" +
                "      \"locationTypeCode\":\"string\",\n" +
                "      \"longitude\":0,\n" +
                "      \"openingHours\":[  \n" +
                "         {  \n" +
                "            \"beginTime\":\"string\",\n" +
                "            \"dayType\":\"string\",\n" +
                "            \"dayValue\":0,\n" +
                "            \"endTime\":\"string\"\n" +
                "         }\n" +
                "      ],\n" +
                "      \"vendorCompCode\":\"string\"\n" +
                "   }\n" +
                "],\n" +
                "\"carRates\":[  \n" +
                "   {  \n" +
                "      \"accessLevel\":\"string\",\n" +
                "      \"baseRateTypeAmount\":0,\n" +
                "      \"baseRateTypeCode\":\"string\",\n" +
                "      \"carModel\":\"string\",\n" +
                "      \"carNo\":0,\n" +
                "      \"carTypeCode\":\"string\",\n" +
                "      \"carTypeName\":\"string\",\n" +
                "      \"companyCode\":\"string\",\n" +
                "      \"companyName\":\"string\",\n" +
                "      \"confirmationStatus\":\"string\",\n" +
                "      \"corporateRateType\":\"string\",\n" +
                "      \"doorCount\":0,\n" +
                "      \"fareType\":\"string\",\n" +
                "      \"fuelType\":\"string\",\n" +
                "      \"guaranteeTypeCode\":\"string\",\n" +
                "      \"isAirConditioned\":true,\n" +
                "      \"isCorporateRate\":true,\n" +
                "      \"isInPolicy\":true,\n" +
                "      \"isUnlimitedMileage\":true,\n" +
                "      \"marketingInfo\":\"string\",\n" +
                "      \"officeId\":\"string\",\n" +
                "      \"passengerCount\":0,\n" +
                "      \"photoName\":\"string\",\n" +
                "      \"pickupAirportCode\":\"string\",\n" +
                "      \"pickupAirportTerminal\":\"string\",\n" +
                "      \"pickupDateTime\":\"string\",\n" +
                "      \"pickupLocationCode\":\"string\",\n" +
                "      \"pickupLocationName\":\"string\",\n" +
                "      \"pickupLocationNameEn\":\"string\",\n" +
                "      \"pickupLocationTypeCode\":\"string\",\n" +
                "      \"pickupShuttleTypeCode\":\"string\",\n" +
                "      \"pickupShuttleTypeName\":\"string\",\n" +
                "      \"rateCategoryCode\":\"string\",\n" +
                "      \"rateIdentifier\":\"string\",\n" +
                "      \"referenceCode\":\"string\",\n" +
                "      \"referenceNumber\":\"string\",\n" +
                "      \"referenceQualifier\":\"string\",\n" +
                "      \"returnAirportCode\":\"string\",\n" +
                "      \"returnAirportTerminal\":\"string\",\n" +
                "      \"returnDateTime\":\"string\",\n" +
                "      \"returnLocationCode\":\"string\",\n" +
                "      \"returnLocationName\":\"string\",\n" +
                "      \"returnLocationNameEn\":\"string\",\n" +
                "      \"returnLocationTypeCode\":\"string\",\n" +
                "      \"returnShuttleTypeCode\":\"string\",\n" +
                "      \"returnShuttleTypeName\":\"string\",\n" +
                "      \"sippCode\":\"string\",\n" +
                "      \"sortKey\":\"string\",\n" +
                "      \"transmissionType\":\"string\",\n" +
                "      \"validMinutes\":0,\n" +
                "      \"validType\":\"string\",\n" +
                "      \"vehicleName\":\"string\",\n" +
                "      \"vehicleTypeOwner\":\"string\",\n" +
                "      \"vendorCompCode\":\"string\",\n" +
                "      \"vendorCurrencyCode\":\"string\",\n" +
                "      \"vendorTotalAmount\":0\n" +
                "   }\n" +
                "],\n" +
                "\"filterData\":{  \n" +
                "   \"carCompanies\":[  \n" +
                "      {  \n" +
                "         \"code\":\"string\",\n" +
                "         \"name\":\"string\"\n" +
                "      }\n" +
                "   ],\n" +
                "   \"carTypes\":[  \n" +
                "      {  \n" +
                "         \"code\":\"string\",\n" +
                "         \"name\":\"string\"\n" +
                "      }\n" +
                "   ],\n" +
                "   \"pickupLocations\":[  \n" +
                "      {  \n" +
                "         \"code\":\"string\",\n" +
                "         \"name\":\"string\",\n" +
                "         \"type\":\"string\"\n" +
                "      }\n" +
                "   ],\n" +
                "   \"returnLocations\":[  \n" +
                "      {  \n" +
                "         \"code\":\"string\",\n" +
                "         \"name\":\"string\",\n" +
                "         \"type\":\"string\"\n" +
                "      }\n" +
                "   ]\n" +
                "}\n" +
                "}\n";
        JSONObject jsonObj = new JSONObject(data);
        System.out.println(jsonObj);

    }
}
