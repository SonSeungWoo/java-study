package me.seungwoo.study;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

/**
 * Created by Leo.
 * User: sonseungwoo
 * Date: 2019-05-12
 * Time: 13:54
 */
public class StudyChapter03 {

    public static void main(String[] args) {
        long howManyDishes1 = DishDto.menu().stream().collect(Collectors.counting());
        long howManyDishes2 = DishDto.menu().stream().count();
        Comparator<DishDto.Dish> dishDtoComparator = Comparator.comparingInt(DishDto.Dish::getCalories);
        Optional<DishDto.Dish> mostCaloriseDish =
                DishDto.menu().stream()
                        .collect(maxBy(dishDtoComparator));
        //합계
        int totalCalories = DishDto.menu().stream().collect(summingInt(DishDto.Dish::getCalories));
        //평균
        double avgCalories = DishDto.menu().stream().collect(averagingDouble(DishDto.Dish::getCalories));
        //합계, 평균, 요소수, 최대값, 최소값
        IntSummaryStatistics menuStatistics = DishDto.menu().stream().collect(summarizingInt(DishDto.Dish::getCalories));
        System.out.println(menuStatistics);

        //문자열 연결
        String shortMenu1 = DishDto.menu().stream().map(DishDto.Dish::getName).collect(joining(", "));
        System.out.println(shortMenu1);

        //reducing
        //첫번째 인수는 리듀싱 연산의 시작값이거나 스트림에 인수가 없을 때 반환값
        //두번째 인수는 정수로 변환할때 사용한 변환 함수
        //세번째 인수는 같은 종류의 두 항목을 하나의 값으로 더하는 BinaryOperator. 두개의 int 사용
        int totalCaloriesReducing = DishDto.menu().stream()
                .collect(reducing(0, DishDto.Dish::getCalories, (i, j) -> i + j));
        System.out.println(totalCaloriesReducing);

        //한개의 인수를 가지고 최대값 구하기
        Optional<DishDto.Dish> mostCalorieDish = DishDto.menu().stream()
                .collect(reducing((d1, d2) -> d1.getCalories() > d2.getCalories() ? d1 : d2));

        //그룹화
        Map<DishDto.Type, List<DishDto.Dish>> dishesByType = DishDto.menu()
                .stream()
                .collect(groupingBy(DishDto.Dish::getType));
        System.out.println(dishesByType);

        //다수준 그룹화
        Map<DishDto.Type, Map<DishDto.CaloricLevel, List<DishDto.Dish>>> dishesByTypeCalroricLevel = DishDto.menu()
                .stream()
                .collect(
                        groupingBy(DishDto.Dish::getType,
                                groupingBy(dish -> {
                                    if (dish.getCalories() <= 400) {
                                        return DishDto.CaloricLevel.DIET;
                                    } else if (dish.getCalories() <= 700) {
                                        return DishDto.CaloricLevel.NORMAL;
                                    } else {
                                        return DishDto.CaloricLevel.FAT;
                                    }
                                }))
                );
        System.out.println(dishesByTypeCalroricLevel);

        //서브그룹
        //groupingBy(f) 는 groupingBy(f, toList())의 축양형이다.
        Map<DishDto.Type, Long> typesCount = DishDto.menu()
                .stream()
                .collect(groupingBy(DishDto.Dish::getType, counting()));
        System.out.println(typesCount);

        //type별로 가장높은칼로리 찾음
        Map<DishDto.Type, Optional<DishDto.Dish>> mostCaloricByType = DishDto.menu()
                .stream()
                .collect(groupingBy(DishDto.Dish::getType, maxBy(Comparator.comparingInt(DishDto.Dish::getCalories))));
        System.out.println("mostCaloricByType : " + mostCaloricByType);

        //1.collect는 점선으로 표시되어 있으며 groupingBy는 가장 바깥쪽에 위치하면서 요리의 종류에
        //따라 메뉴 스트림을 세개의 서브스트림으로 그룹화 한다.
        //2.groupingBy collect는 collectingAndThen 컬렉터를 감싼다.
        //따라서 두번째 컬렉터는 그룹화된 세개의 서브스트림에 적용된다.
        //3.collectingAndThen 컬렉터는 세번째 컬렉터 maxBy를 감싼다.
        //4.리듀싱 컬렉터가 서브스트림에 연산을 수행한 결과에 collectingAndThen의 Optional::get
        //변환 함수가 적용된다.
        //5.groupingBy 컬렉터가 반환하는 맵의 분류 키에 대응하는 세 값이 각각의
        //요리 형식에서 가장 높은 칼로리이다.
        //*collectingAndThen 적용할 컬렉터와 변환함수를 인수로 받아 다른 컬렉터를 반환한다.
        Map<DishDto.Type, DishDto.Dish> mostCaloricByType2 = DishDto.menu()
                .stream()
                .collect(groupingBy(DishDto.Dish::getType,
                        collectingAndThen(
                                maxBy(Comparator.comparingInt(DishDto.Dish::getCalories)),
                                Optional::get)));
        System.out.println("mostCaloricByType2 : " + mostCaloricByType2);
    }
    
}
