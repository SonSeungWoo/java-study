package me.seungwoo.study;

/**
 * Created by Leo.
 * User: sonseungwoo
 * Date: 2019-07-14
 * Time: 14:42
 */
public class Test002 extends Test001 implements InterfaceTest{
    @Override
    String test() {
        return null;
    }

    public void test003(){
        test002();
        interfaceTest();
    }

    @Override
    public String test004() {
        return null;
    }
}
