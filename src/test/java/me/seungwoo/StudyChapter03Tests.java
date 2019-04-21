package me.seungwoo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collector;
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
    public void StudyChapter03_test03() {
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


}
