package me.seungwoo.calendar;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by Leo.
 * User: ssw
 * Date: 2019-03-27
 * Time: 14:37
 */
public class CalendarTestDto {

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
}
