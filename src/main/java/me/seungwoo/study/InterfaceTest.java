package me.seungwoo.study;

/**
 * Created by Leo.
 * User: sonseungwoo
 * Date: 2019-07-14
 * Time: 14:57
 */
public interface InterfaceTest {

    default String interfaceTest(){
        return "String";
    }

    String test004();
}
