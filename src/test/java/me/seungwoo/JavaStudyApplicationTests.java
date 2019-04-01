package me.seungwoo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.function.Function;


@ExtendWith(SpringExtension.class)
@SpringBootTest
public class JavaStudyApplicationTests {

    private static Logger logger = LoggerFactory.getLogger(JavaStudyApplicationTests.class);

    @Test
    public void contextLoads() {
        StudyChapter01 studyChapter01 = new StudyChapter01();
        logger.info(studyChapter01.studyChapterTest1());
        proccess(() -> System.out.println("This is awesome!"));

    }

    public void proccess(Runnable r) {
        r.run();
    }

    @Test
    public void proccessTest001() throws IOException {
        String oneLine = StudyChapter01.processFile(
                (BufferedReader br) -> br.readLine()
        );

        String twoLine = StudyChapter01.processFile(
                (BufferedReader br) -> br.readLine() + br.readLine()
        );
        logger.info("oneLine : {}, twoLine : {}", oneLine, twoLine);
    }

    @Test
    public void functionTest() {
        Function<BufferedReader, String> f =
                (br) -> {
                    try {
                        return br.readLine();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                };
    }
}
