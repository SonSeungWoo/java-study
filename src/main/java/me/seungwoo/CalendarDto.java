package me.seungwoo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by Leo.
 * User: ssw
 * Date: 2019-04-02
 * Time: 15:01
 */
public class CalendarDto {

    //현재+1일 부터 1년치 달력 데이터
    @Data
    public static class CalendarData {
        private List<MonthData> list;
        private ItemData item;
        private String weekdayTitle[] = new String[]{"SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT"};
    }

    //월별 담기
    @Data
    public static class MonthData {
        private int year;
        private int month;
        private List<List<DayData>> days;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ItemData {
        private LocalDate firstDate;
        private LocalDate lastDate;
    }

    //주차별 담기 6주차
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DayData {
        private int day;
        private Boolean isPastMonth;
        private Boolean selectable;
        private Boolean isHoliday;
        private int weekday;
        private String holidayName;
    }

    @Data
    public static class HolidayDate {
        private String date;
        private String name;
        private String category; //sundry, holiday, divisions
    }
}
