package me.seungwoo;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Leo.
 * User: ssw
 * Date: 2019-04-02
 * Time: 14:59
 */
@Slf4j
@RestController
public class CalendarController {

    private static final ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    @GetMapping("/calendar")
    public ResponseEntity<CalendarDto.CalendarData> getCalendarOneYearLater() {
        StopWatch sw = new StopWatch();
        sw.start();
        LocalDate startDate = LocalDate.now().plusDays(1);
        LocalDate endDate = startDate.plusYears(1);
        //LocalDate endDate = LocalDate.of(2019, 06, startDate.lengthOfMonth());
        //내일부터 1년뒤 전체 날짜 목록
        List<LocalDate> dateList = fromStartEndDate(startDate, endDate);
        //년도월
        LinkedHashSet<String> months = dateList.stream()
                .map(d -> d.getYear() + "/" + d.getMonthValue())
                .collect(Collectors.toCollection(LinkedHashSet::new));
        //공휴일, 출장규정날짜
        Map<String, Map<String, CalendarDto.DayData>> decemberHolidays = getPublicHolidayDecember(months);
        CalendarDto.CalendarData totalDecemberData = getTotalMonthData(dateList, months, decemberHolidays);
        CalendarDto.ItemData itemData = new CalendarDto.ItemData(startDate, endDate);
        totalDecemberData.setItem(itemData);
        sw.stop();
        double total = sw.getTotalTimeSeconds();
        log.info("TotalTimeSeconds : {}", total);
        return ResponseEntity.ok(totalDecemberData);
    }

    @GetMapping("/test")
    public JSONObject test() throws ParseException {
        JSONParser jsonParser = new JSONParser();
        String madData = testData();
        JSONObject jobj = (JSONObject) jsonParser.parse(madData);
        return jobj;
    }

    public String testData(){
        String data = "{\n" +
                "    \"isSucceed\": true,\n" +
                "    \"isWarning\": false,\n" +
                "    \"error\": null,\n" +
                "    \"warning\": null,\n" +
                "    \"totalDurationTime\": 4.359,\n" +
                "    \"vendorDurationTime\": 4.289,\n" +
                "    \"trId\": \"6f2841d887064acc82547f8859484f4d\",\n" +
                "    \"result\": {\n" +
                "        \"carLocations\": [\n" +
                "            {\n" +
                "                \"address\": \"9000 AIRPORT BLVDLOS ANGELES AP 900450000US\",\n" +
                "                \"companyCode\": \"ZE\",\n" +
                "                \"companyName\": \"HERTZ\",\n" +
                "                \"latitude\": 33.93333,\n" +
                "                \"locationCode\": \"LAXT15\",\n" +
                "                \"locationName\": \"\",\n" +
                "                \"locationNameEn\": \"\",\n" +
                "                \"locationTypeCode\": \"T\",\n" +
                "                \"longitude\": -118.4,\n" +
                "                \"openingHours\": [\n" +
                "                    {\n" +
                "                        \"beginTime\": \"00:01\",\n" +
                "                        \"dayType\": \"Days of week\",\n" +
                "                        \"dayValue\": 0,\n" +
                "                        \"endTime\": \"00:00\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"beginTime\": \"00:01\",\n" +
                "                        \"dayType\": \"Days of week\",\n" +
                "                        \"dayValue\": 0,\n" +
                "                        \"endTime\": \"00:00\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"beginTime\": \"00:01\",\n" +
                "                        \"dayType\": \"Days of week\",\n" +
                "                        \"dayValue\": 0,\n" +
                "                        \"endTime\": \"00:00\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"beginTime\": \"00:01\",\n" +
                "                        \"dayType\": \"Days of week\",\n" +
                "                        \"dayValue\": 0,\n" +
                "                        \"endTime\": \"00:00\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"beginTime\": \"00:01\",\n" +
                "                        \"dayType\": \"Days of week\",\n" +
                "                        \"dayValue\": 0,\n" +
                "                        \"endTime\": \"00:00\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"beginTime\": \"00:01\",\n" +
                "                        \"dayType\": \"Days of week\",\n" +
                "                        \"dayValue\": 0,\n" +
                "                        \"endTime\": \"00:00\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"beginTime\": \"00:01\",\n" +
                "                        \"dayType\": \"Days of week\",\n" +
                "                        \"dayValue\": 0,\n" +
                "                        \"endTime\": \"00:00\"\n" +
                "                    }\n" +
                "                ],\n" +
                "                \"vendorCompCode\": \"15001\"\n" +
                "            }\n" +
                "        ],\n" +
                "        \"carRates\": [\n" +
                "            {\n" +
                "                \"accessLevel\": \"CP\",\n" +
                "                \"baseRateTypeAmount\": 31.7,\n" +
                "                \"baseRateTypeCode\": \"Daily\",\n" +
                "                \"carModel\": \"Nissan Versa\",\n" +
                "                \"carNo\": 1,\n" +
                "                \"carTypeCode\": \"C\",\n" +
                "                \"carTypeName\": \"\",\n" +
                "                \"companyCode\": \"ZE\",\n" +
                "                \"companyName\": \"HERTZ\",\n" +
                "                \"confirmationStatus\": \"Available\",\n" +
                "                \"corporateRateType\": \"RCT01\",\n" +
                "                \"doorCount\": 4,\n" +
                "                \"fareType\": \"VADD\",\n" +
                "                \"fuelType\": \"N/A\",\n" +
                "                \"guaranteeTypeCode\": \"GRT01\",\n" +
                "                \"isAirConditioned\": true,\n" +
                "                \"isCorporateRate\": false,\n" +
                "                \"isInPolicy\": true,\n" +
                "                \"isUnlimitedMileage\": true,\n" +
                "                \"marketingInfo\": \"ADVENTURE D VADD\",\n" +
                "                \"officeId\": \"SELK138AE\",\n" +
                "                \"passengerCount\": 5,\n" +
                "                \"photoName\": \"https://post-development.multimedia.testing.amadeus.com/mdc/retrieveCarItem?ctg=VEHICLE&prov=ZE&cnt=US&vehcat=CCAR&item=0&stamp=VEHICLE_0_0_1537784032018&file=5.JPEG\",\n" +
                "                \"pickupAirportCode\": \"LAX\",\n" +
                "                \"pickupAirportTerminal\": \"ALL\",\n" +
                "                \"pickupDateTime\": \"2019-11-06 10:00:00\",\n" +
                "                \"pickupLocationCode\": \"LAXT15\",\n" +
                "                \"pickupLocationName\": \"\",\n" +
                "                \"pickupLocationNameEn\": \"\",\n" +
                "                \"pickupLocationTypeCode\": \"T\",\n" +
                "                \"pickupShuttleTypeCode\": \"AS\",\n" +
                "                \"pickupShuttleTypeName\": \"\",\n" +
                "                \"rateCategoryCode\": \"024\",\n" +
                "                \"rateIdentifier\": \"G0DYLJ4Q02\",\n" +
                "                \"referenceCode\": \"JRTAJ5VLG3EGWNUWE0YO\",\n" +
                "                \"referenceNumber\": \"\",\n" +
                "                \"referenceQualifier\": \"VADD\",\n" +
                "                \"returnAirportCode\": \"LAX\",\n" +
                "                \"returnAirportTerminal\": \"ALL\",\n" +
                "                \"returnDateTime\": \"2019-11-07 10:00:00\",\n" +
                "                \"returnLocationCode\": \"LAXT15\",\n" +
                "                \"returnLocationName\": \"\",\n" +
                "                \"returnLocationNameEn\": \"\",\n" +
                "                \"returnLocationTypeCode\": \"T\",\n" +
                "                \"returnShuttleTypeCode\": \"AS\",\n" +
                "                \"returnShuttleTypeName\": \"\",\n" +
                "                \"sippCode\": \"CCAR\",\n" +
                "                \"sortKey\": \"ZE:C:Automatic:\",\n" +
                "                \"transmissionType\": \"Automatic\",\n" +
                "                \"validMinutes\": 5,\n" +
                "                \"validType\": \"DLD\",\n" +
                "                \"vehicleName\": \"Nissan Versa\",\n" +
                "                \"vehicleTypeOwner\": \"ACR\",\n" +
                "                \"vendorCompCode\": \"15001\",\n" +
                "                \"vendorCurrencyCode\": \"USD\",\n" +
                "                \"vendorTotalAmount\": 31.7\n" +
                "            }\n" +
                "        ],\n" +
                "        \"filterData\": {\n" +
                "            \"carCompanies\": [\n" +
                "                {\n" +
                "                    \"code\": \"ZE\",\n" +
                "                    \"name\": \"HERTZ\"\n" +
                "                }\n" +
                "            ],\n" +
                "            \"carTypes\": [\n" +
                "                {\n" +
                "                    \"code\": \"C\",\n" +
                "                    \"name\": \"\"\n" +
                "                }\n" +
                "            ],\n" +
                "            \"pickupLocations\": [\n" +
                "                {\n" +
                "                    \"code\": \"LAXT15\",\n" +
                "                    \"name\": \"\",\n" +
                "                    \"type\": \"T\"\n" +
                "                }\n" +
                "            ],\n" +
                "            \"returnLocations\": [\n" +
                "                {\n" +
                "                    \"code\": \"LAXT15\",\n" +
                "                    \"name\": \"\",\n" +
                "                    \"type\": \"T\"\n" +
                "                }\n" +
                "            ]\n" +
                "        }\n" +
                "    }\n" +
                "}";
        return data;
    }

    //1년 데이터
    public List<LocalDate> fromStartEndDate(LocalDate start, LocalDate end) {
        List<LocalDate> dateList = new ArrayList<>();
        for (LocalDate date = start; date.isBefore(end.plusDays(1)); date = date.plusDays(1)) {
            dateList.add(date);
        }
        return dateList;
    }

    //공휴일 테이블, 출장규정 날짜, 공휴일 api
    public Map<String, Map<String, CalendarDto.DayData>> getPublicHolidayDecember(LinkedHashSet<String> months) {
        Map<String, Map<String, CalendarDto.DayData>> mapHoliday = new HashMap<>();
        months.stream().forEach(d -> {
            Map<String, CalendarDto.DayData> dayDataMap = new HashMap<>();
            //공휴일 api
            RestTemplate restTemplate = new RestTemplate();
            String baseUrl = "http://api.manana.kr/calendar.json" + "/" + d;
            URI uri = null;
            try {
                uri = new URI(baseUrl);
            } catch (URISyntaxException e) {
                log.error("URISyntaxException : {}", e);
            }
            CalendarDto.HolidayDate[] holidayDate = restTemplate.getForObject(uri, CalendarDto.HolidayDate[].class);
            List<CalendarDto.HolidayDate> list = Arrays.asList(holidayDate).stream()
                    .filter(h -> h.getCategory().equals("holiday"))
                    .collect(Collectors.toList());
            for (CalendarDto.HolidayDate date : list) {
                CalendarDto.DayData dayData = new CalendarDto.DayData();
                String[] str = date.getDate().split("-");
                dayData.setDay(Integer.parseInt(str[2]));
                dayData.setHolidayName(date.getName());
                dayDataMap.put(date.getDate(),dayData);
            }
            //공휴일 테이블

            //출장규정 날짜
            mapHoliday.put(d, dayDataMap);
        });
        return mapHoliday;
    }

    public CalendarDto.CalendarData getTotalMonthData(List<LocalDate> dateList, LinkedHashSet<String> months, Map<String, Map<String, CalendarDto.DayData>> publicHolidays) {
        //해당 월의 몇주차인지
        TemporalField woy = WeekFields.of(Locale.getDefault()).weekOfMonth();
        CalendarDto.CalendarData calendarData = new CalendarDto.CalendarData();
        //월별
        List<CalendarDto.MonthData> monthListData = new ArrayList<>();
        //오늘 날짜
        LocalDate nowLocalDate = LocalDate.now().plusDays(1);
        for (String m : months) {
            CalendarDto.MonthData monthData = new CalendarDto.MonthData();//월별 담기
            List<List<CalendarDto.DayData>> dayDataList = new ArrayList<>();//주차별
            String[] date = m.split("/");
            int year = Integer.parseInt(date[0]);
            int month = Integer.parseInt(date[1]);
            monthData.setYear(year);
            monthData.setMonth(month);
            LocalDate currentDate = LocalDate.of(year, month, 1);
            int maxDay = currentDate.lengthOfMonth();//해당월 총일수
            int startDay = 1;
            int pastDayCnt = currentDate.getDayOfWeek().getValue();//해당 첫째주 전달 일수 계산
            for (int i = 1; i < 6; i++) {
                //주차별
                List<CalendarDto.DayData> weekList = new ArrayList<>();
                //첫째주 전 달 day 추가, isPastMonth true 적용
                if (i < 2) {
                    for (int k = pastDayCnt; k > 0; k--) {
                        LocalDate previousDay = currentDate.minusDays(k);
                        weekList.add(new CalendarDto.DayData(previousDay.getDayOfMonth(), true,
                                false, false, previousDay.getDayOfWeek().getValue() == 7 ? 0 : previousDay.getDayOfWeek().getValue(), null));
                    }
                }
                for (int j = startDay; j <= maxDay; j++) {
                    LocalDate week = LocalDate.of(year, month, j);
                    int weekNum = week.get(woy);
                    if (i == weekNum) {
                        CalendarDto.DayData dayData = new CalendarDto.DayData();
                        Map<String, CalendarDto.DayData> map = publicHolidays.get(m);
                        CalendarDto.DayData holiDate= map.get(week.toString());
                        //공휴일 여부 isHoliday
                        dayData.setDay(j);
                        dayData.setWeekday(week.getDayOfWeek().getValue() == 7 ? 0 : week.getDayOfWeek().getValue());
                        dayData.setIsHoliday(holiDate != null ? true : false);
                        dayData.setIsPastMonth(false);
                        dayData.setHolidayName(holiDate != null ? holiDate.getHolidayName() : null);
                        //현재 시간보다 이후 날짜 데이터인지 체크 오늘 날짜 이후 selectable true
                        dayData.setSelectable(currentDate.isAfter(nowLocalDate));
                        weekList.add(dayData);
                    } else {
                        startDay = j;
                        break;
                    }
                }
                dayDataList.add(weekList);
            }
            monthData.setDays(dayDataList);
            monthListData.add(monthData);
        }
        calendarData.setList(monthListData);
        return calendarData;
    }
}
