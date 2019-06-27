package me.seungwoo.study;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Scanner;

/**
 * Created by Leo.
 * User: ssw
 * Date: 2019-06-27
 * Time: 13:57
 */
@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class AlgorithmTests {

    /**
     * 문자열 s의 길이가 4혹은 6이고, 숫자로만 구성되있는지 확인해주는 함수
     * 예를들어 s가 a234이면 false를 리턴하고 1234라면 true를 리턴
     */
    @Test
    public void verification() {
        String str = "A23456";
        log.info("numberVerification_001 : {}", numberVerification_001(str));
        log.info("numberVerification_002 : {}", numberVerification_002(str));
    }

    //NumberFormatException
    public boolean numberVerification_001(String str) {
        if (str.length() == 4 || str.length() == 6) {
            try {
                int s = Integer.valueOf(str);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        } else return false;
    }

    //정규식
    public boolean numberVerification_002(String str) {
        if (str.length() == 4 || str.length() == 6) return str.matches("(^[0-9]*$)");
        return false;
    }

    @Test
    public void 약수합구하기() {
        int num = 12;
        int sum = 0;
        for (int i = 1; i <= num; i++) {
            if (num % i == 0) sum = sum + i;
        }
        System.out.println(sum);
    }

    public static void main(String[] args) {
        String message;
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("입력하세요.");
            message = scanner.nextLine();
            System.out.println(message);
            System.out.println("계속 : 1  종료 : 2");
            if (Integer.parseInt(scanner.nextLine()) == 2) {
                System.out.println("종료");
                break;
            }
        }
    }
}
