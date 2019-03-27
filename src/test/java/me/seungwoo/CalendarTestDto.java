package me.seungwoo;

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

    @Data
    public static class CalendarHolidayData{
        private List<CalendarListData> list;
        private ItemData item;
        private String weekdayTitle[] = new String[]{"SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT"};
    }

    //월별 담기
    @Data
    public static class CalendarListData{
        private int year;
        private int month;
        private List<DayData> days;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ItemData{
        private LocalDate firstDate;
        private LocalDate lastDate;
    }

    //주차별 담기 6주차
    @Data
    public static class DayData{
        private int day;
        private Boolean isPastMonth;
        private Boolean selectable;
        private Boolean isHoliday;
        private int weekday;
        private String holidayName;
    }
}
