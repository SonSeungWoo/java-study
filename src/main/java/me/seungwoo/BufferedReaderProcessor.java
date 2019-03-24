package me.seungwoo;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Created by Leo.
 * User: sonseungwoo
 * Date: 2019-03-24
 * Time: 15:34
 */
@FunctionalInterface
public interface BufferedReaderProcessor {
    String process(BufferedReader b) throws IOException;
}
