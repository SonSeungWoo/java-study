package me.seungwoo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.StopWatch;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by Leo.
 * User: ssw
 * Date: 2019-03-27
 * Time: 09:25
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CalendarTests {

    @Test
    public void content() {
        StopWatch sw = new StopWatch();
        sw.start();

        Calendar cal = Calendar.getInstance();
        int day[][][] = new int[12][6][7];  //[월][주][요일]
        String week[] = new String[]{"일", "월", "화", "수", "목", "금", "토"};

        for (int month = 0; month < 12; month++) {
            cal.set(Calendar.MONTH, month);
            //이번달 마지막일 maxDay에 저장
            int maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

            for (int i = 1; i <= maxDay; i++) {
                cal.set(Calendar.DATE, i);
                //주와 요일은 1부터 시작하기 때문에 0부터 시작하는 배열 인덱스와 맞지 않다
                //그래서 -1을 해서 배열 인덱스와 맞춰 주었다.
                day[cal.get(Calendar.MONTH)]
                        [cal.get(Calendar.WEEK_OF_MONTH) - 1]
                        [cal.get(Calendar.DAY_OF_WEEK) - 1] = i;
            }
        }

        for (int month = 0; month < 12; month++) {
            int mm = month + 1;
            System.out.println("\t\t\t" + mm + "월");

            for (int i = 0; i < week.length; i++) {
                System.out.print(week[i] + "\t");
            }
            System.out.println();

            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 7; j++) {
                    if (day[month][i][j] != 0)
                        System.out.print(day[month][i][j] + "\t");
                    else
                        System.out.print("\t");
                }
                System.out.println();
            }
        }
        sw.stop();
        System.out.println("TotalTime : " + sw.getTotalTimeSeconds());
    }

    @Test
    public void test() {
        StopWatch sw = new StopWatch();
        sw.start();
        //시작일 DB min(solar_date)  종료일 max(solar_date)
        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDate firstDate = LocalDate.of(localDateTime.getYear(), localDateTime.getMonth(), localDateTime.getDayOfMonth() + 1);
        LocalDate lastDate = LocalDate.of(firstDate.getYear() + 1, firstDate.getMonth(), firstDate.getDayOfMonth());
        //해당 월의 몇주차인지
        TemporalField woy = WeekFields.of(Locale.getDefault()).weekOfMonth();

        //캘린더 휴일데이터
        CalendarTestDto.CalendarHolidayData calendarData = new CalendarTestDto.CalendarHolidayData();
        //TODO firstDate, lastDate 휴일 min,max값 기준 확인필요
        CalendarTestDto.ItemData itemData = new CalendarTestDto.ItemData(firstDate, lastDate);
        calendarData.setItem(itemData);
        List<CalendarTestDto.CalendarListData> list = new ArrayList<>();
        //DB calendar year min < max


        for (int year = firstDate.getYear(); year <= lastDate.getYear(); year++) {
            //DB calendar month min < max
            for (int month = firstDate.getMonthValue(); month <= lastDate.getMonthValue(); month++) {
                CalendarTestDto.CalendarListData calendarListData = new CalendarTestDto.CalendarListData();
                calendarListData.setYear(year);
                calendarListData.setMonth(month);
                LocalDate currentDate = LocalDate.of(year, month, 1);
                //이번달 마지막일 maxDay에 저장
                int maxDay = currentDate.lengthOfMonth();
                List<CalendarTestDto.DayData> dayData = new ArrayList<>();
                for (int i = 1; i <= maxDay; i++) {
                    //주차
                    LocalDate week = LocalDate.of(year, month, i);
                    int weekNum = week.get(woy);
                    CalendarTestDto.DayData data = new CalendarTestDto.DayData();
                    data.setDay(i);
                    data.setIsHoliday(false);
                    data.setIsPastMonth(false);
                    data.setSelectable(false);
                    data.setWeekday(i);
                    dayData.add(data);
                }
                calendarListData.setDays(dayData);
                list.add(calendarListData);
            }
        }
        calendarData.setList(list);
        sw.stop();
        System.out.println("TotalTime : " + sw.getTotalTimeSeconds());
    }

    @Test
    public void test2() {
        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDate firstDate = LocalDate.of(localDateTime.getYear(), localDateTime.getMonth(), localDateTime.getDayOfMonth() + 1);
        LocalDate lastDate = LocalDate.of(firstDate.getYear() + 1, firstDate.getMonth(), firstDate.getDayOfMonth());

        //해당 월의 몇주차인지
        TemporalField woy = WeekFields.of(Locale.getDefault()).weekOfMonth();

        //캘린더 휴일데이터
        CalendarTestDto.CalendarHolidayData calendarData = new CalendarTestDto.CalendarHolidayData();
        CalendarTestDto.ItemData itemData = new CalendarTestDto.ItemData(firstDate, lastDate);
        calendarData.setItem(itemData);
        List<CalendarTestDto.CalendarListData> list = new ArrayList<>();
        int preYear = firstDate.getYear();
        int preMonth = firstDate.getMonthValue();
        int preDay = firstDate.getDayOfMonth();
        while (!firstDate.isAfter(lastDate)) {
            CalendarTestDto.CalendarListData calendarListData = new CalendarTestDto.CalendarListData();
            calendarListData.setYear(firstDate.getYear());
            calendarListData.setMonth(firstDate.getMonthValue());
            LocalDate currentDate = LocalDate.of(firstDate.getYear(), firstDate.getMonthValue(), firstDate.getDayOfMonth());
            //이번달 마지막일 maxDay에 저장
            int maxDay = currentDate.lengthOfMonth();
            List<CalendarTestDto.DayData> dayData = new ArrayList<>();
            System.out.println(firstDate); //2019-03-28



            firstDate = firstDate.plusDays(1);
        }
    }
}
