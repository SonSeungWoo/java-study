package me.seungwoo.study;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Leo.
 * User: sonseungwoo
 * Date: 2019-04-21
 * Time: 14:19
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class StudyChapter03Tests {

    private static final Logger logger = LoggerFactory.getLogger(StudyChapter03Tests.class);

    @Test
    public void StudyChapter03_test01() {
        List<String> dishName = StudyChapter02.menu().stream()
                .map(StudyChapter02.Dish::getName)
                .collect(Collectors.toList());
        logger.debug("dishName : {}", dishName);

        List<Integer> dishNameLengths = StudyChapter02.menu().stream()
                .map(StudyChapter02.Dish::getName)
                .map(String::length)
                .collect(Collectors.toList());
        logger.debug("dishNameLengths : {}", dishNameLengths);
    }

    @Test
    public void StudyChapter03_test02() {
        List<String> words = Arrays.asList("java", "son", "seung", "woo");
        List<Integer> wordLengths = words.stream()
                .map(String::length)
                .collect(Collectors.toList());
        logger.debug("wordLengths : {}", wordLengths);

        String[] arraOfWords = {"Goodbye", "world"};
        Stream<String> streamOfWords = Arrays.stream(arraOfWords);

        List<Stream<String>> wordStream = words.stream()
                //각단어를 개별 문자열 배욜로 반환
                .map(word -> word.split(""))
                //각 배열을 별도의 스트림으로 생성
                .map(Arrays::stream)
                .distinct()
                .collect(Collectors.toList());
        //map -> flatmap
        List<String> uniqueCharacters = words.stream()
                .map(word -> word.split(""))
                .flatMap(Arrays::stream)
                .distinct()
                .collect(Collectors.toList());
        logger.debug("uniqueCharacters : {}", uniqueCharacters);
    }

    @Test
    public void StudyChapter03_test03_flatMap() {
        String[][] sample = new String[][]{
                {"a", "b"}, {"c", "d"}, {"e", "a"}, {"a", "h"}, {"i", "j"}
        };
        //without .flatMap()
        List<String> stream = Arrays.stream(sample)
                .flatMap(array -> Arrays.stream(array))
                .filter(x -> "a".equals(x))
                .collect(Collectors.toList());
        logger.debug("stream : {}", stream);

        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> square = numbers.stream()
                .map(num -> num * num)
                .collect(Collectors.toList());
        logger.debug("square : {}", square);
    }

    @Test
    public void StudyChapter03_test04_matchAndFind() {
        List<StudyChapter02.Dish> menu = StudyChapter02.menu();
        //match (&&, ||) 스트림 쇼트서킷 기법
        //anyMatch 적어도 한 요소와 일치하는지 확인시
        if (menu.stream().anyMatch(StudyChapter02.Dish::isVegetarian)) {
            logger.debug("The menu is (somewhat) vegetarian friendly!!");
        }

        //allMatch 스트림의 모든 요소가 주어진 프리드케이트와 일치하는 검사
        boolean isHealthy = menu.stream()
                .allMatch(d -> d.getCalories() < 1000);

        //nonMatch allMatch와 반대로 주어진 프레디케이트와 일치하는 요소가 없는지 검사
        boolean nonMatchIsHealthy = menu.stream()
                .noneMatch(d -> d.getCalories() >= 1000);

        //find 요소검색
        //findFirst, findAny 병렬실행에서는 첫번째 요소를 찾기 어렵다.
        //따라서 요소의 반환 순서가 상관없다면 병렬 스트림에서는 제약이 적은 findAny를 사용한다.
        Optional<StudyChapter02.Dish> dish =
                menu.stream()
                        .filter(StudyChapter02.Dish::isVegetarian)
                        .findAny();
        logger.info("find dish : {}", dish);

        //값이 있으면 출력 없으면 아무 일도 일어나지 않는다.
        menu.stream()
                .filter(StudyChapter02.Dish::isVegetarian)
                .findAny()
                .ifPresent(d -> System.out.println(d.getName()));

        List<Integer> someNumbers = Arrays.asList(1, 2, 3, 4, 5);
        Optional<Integer> firstSquareDivisibleByThree =
                someNumbers.stream()
                        .map(x -> x * x)
                        .filter(x -> x % 3 == 0)
                        .findFirst(); //9
    }

    @Test
    public void StudyChapter03_test05_parallel() {
        //자바는 ForkJoinPool 프레임워크를 이용해서 병렬 처리를 한다
        List<String> strList = Arrays.asList("son", "seung", "woo", "병렬처리");
        strList.stream()
                .forEach(s -> System.out.println(s + " : " + Thread.currentThread().getName()));
        strList.parallelStream()
                .forEach(s -> System.out.println(s + " : " + Thread.currentThread().getName()));
    }

    @Test
    public void StudyChapter03_test06_reduce() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);
        //reduce는 두개의 인수를 갖는다.
        //초깃값 0
        //두 요소를 조합해서 새로운 값을 만드는 BinaryOperator<T>. 예제에서는 람다표현식 (a, b) -> a + b를 사용했다.
        int sum1 = numbers.stream()
                .reduce(0, (a, b) -> a + b);
        //sum 메서드 제공
        int sum2 = numbers.stream()
                .reduce(0, Integer::sum);
        //초깃값을 받지 않도록 오버로드된 reduce도 있다. 그러나 이 reduces는 Optional 객체를 반환한다.
        Optional<Integer> sum3 = numbers.stream()
                .reduce(Integer::sum);

        //최소, 최대값 구하기
        Optional<Integer> max = numbers.stream().reduce(Integer::max);
        Optional<Integer> min = numbers.stream().reduce(Integer::min);

        int count = StudyChapter02.menu().stream()
                .map(dish -> 1)
                .reduce(0, (a, b) -> a + b);

        //reduce에 넘겨준 람다의 상태(인스턴스 변수)가 변하지 않아야 한다.
        //연산이 어떤 순서로 실행되더라도 결과가 바뀌지 않는 구조여야 한다.
        int count2 = StudyChapter02.menu().parallelStream()
                .map(dish -> 1)
                .reduce(0, (a, b) -> a + b);

        long menuCount = StudyChapter02.menu().stream().count();
        logger.debug("sum1 : {}, sum2 : {}, sum3 : {}, " +
                "count : {}, menuCount : {}", sum1, sum2, sum3, count, menuCount);
    }

    @Test
    public void manifoldTest() throws IOException {
        Properties myProperties = new Properties();
        myProperties.load(getClass().getResourceAsStream("/application.properties"));
        String myMessage = myProperties.getProperty("my.message");
    }
}
