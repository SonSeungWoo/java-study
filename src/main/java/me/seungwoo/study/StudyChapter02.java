package me.seungwoo.study;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Leo.
 * User: sonseungwoo
 * Date: 2019-04-01
 * Time: 21:18
 */
public class StudyChapter02 {

    @Data
    @AllArgsConstructor
    public static class Dish {
        private final String name;
        private final boolean vegetarian;
        private final int calories;
        private final Type type;
    }

    public enum Type {
        MEAT, FISH, OTHER
    }

    public static List<Dish> menu() {
        List<Dish> menu = Arrays.asList(
                new Dish("pork", false, 800, Type.MEAT),
                new Dish("beef", false, 700, Type.MEAT),
                new Dish("chicken", false, 400, Type.MEAT),
                new Dish("french fries", true, 530, Type.OTHER),
                new Dish("rice", true, 350, Type.OTHER),
                new Dish("season", true, 120, Type.OTHER),
                new Dish("pizza", true, 550, Type.OTHER),
                new Dish("prawns", false, 300, Type.FISH),
                new Dish("salmon", false, 450, Type.FISH)
        );
        return menu;
    }

    //test01
    public List<String> menuName() {
        //filter, map, limit는 서로 연결되어 파이프라인을 형성한다. (중간연산)
        //collect로 파이프라인을 실행한 다음에 닫는다. (최종연산)
        List<Dish> menu = menu();
        List<String> threeHighCaloricDishNames =
                menu.stream()                           //메뉴(요리리스트에서)스트림을 얻는다.
                        .filter(d -> d.getCalories() > 300) //파이프라인 연산 만들기,첫번째로 고칼로리 요리를 필터링
                        .map(Dish::getName)                 //요리명 추출
                        .limit(3)                           //선착순 세 개만 선택
                        .collect(Collectors.toList());      //결과를 다른 리스트로 저장
        return threeHighCaloricDishNames;
    }
}
