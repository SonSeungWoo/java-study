package me.seungwoo;

import com.sun.xml.internal.xsom.impl.util.Uri;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Leo.
 * User: sonseungwoo
 * Date: 2019-03-24
 * Time: 14:18
 */
public class StudyChapter01 {

    public String studyChapterTest1(){
        String str = "seungwoo";
        Person person = new Person("son","seungwoo", "test@test.com");
        //Runnable r1 = () -> System.out.println("Hello World 1");
        return person.toString();
    }

    public static String processFile(BufferedReaderProcessor p) throws IOException {
        File file = new File("/Users/sonseungwoo/Downloads/studyTest.rtf");
        try (BufferedReader br = new BufferedReader(new FileReader(file))){
            return p.process(br);
        }
    }
}
