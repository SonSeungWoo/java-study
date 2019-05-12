package me.seungwoo.study;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Leo.
 * User: sonseungwoo
 * Date: 2019-05-12
 * Time: 13:53
 */
public class DishDto {
    @Data
    @AllArgsConstructor
    public static class Dish {
        private final String name;
        private final boolean vegetarian;
        private final int calories;
        private final DishDto.Type type;
    }

    public enum Type {
        MEAT, FISH, OTHER
    }

    public enum CaloricLevel {
        DIET, NORMAL, FAT
    }


    public static List<DishDto.Dish> menu() {
        List<DishDto.Dish> menu = Arrays.asList(
                new DishDto.Dish("pork",false,800, DishDto.Type.MEAT),
                new DishDto.Dish("beef",false,700, DishDto.Type.MEAT),
                new DishDto.Dish("chicken",false,400, DishDto.Type.MEAT),
                new DishDto.Dish("french fries",true,530, DishDto.Type.OTHER),
                new DishDto.Dish("rice",true,350, DishDto.Type.OTHER),
                new DishDto.Dish("season",true,120, DishDto.Type.OTHER),
                new DishDto.Dish("pizza",true,550, DishDto.Type.OTHER),
                new DishDto.Dish("prawns",false,300, DishDto.Type.FISH),
                new DishDto.Dish("salmon",false,450, DishDto.Type.FISH)
        );
        return menu;
    }
}
