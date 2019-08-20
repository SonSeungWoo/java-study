package me.seungwoo.study;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.*;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

/**
 * Created by Leo.
 * User: ssw
 * Date: 2019-08-20
 * Time: 09:36
 */
public class LogTest {

    private static Logger logger = LoggerFactory.getLogger(LogTest.class);

    public static void main(String[] args) throws IOException {
        Pattern p = Pattern.compile("\\[(.*?)\\]");
        List<LogDto> logList = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader("/Users/traport/Downloads/test/input.log"));
        while (!StringUtils.isEmpty(br.readLine())) {
            String s = br.readLine();
            Matcher m = p.matcher(s);
            List<String> list = new ArrayList<>();
            //수정해야함
            while (m.find()) {
                list.add(s.substring(m.start() + 1, m.end() - 1));
            }
            LogDto logDto = new LogDto();
            logDto.setStatusCode(list.get(0));
            logDto.setRequestUrl(list.get(1));
            logDto.setBrowser(list.get(2));
            logDto.setDateTime(list.get(3));
            logList.add(logDto);
            //여기까지 수정
        }
        br.close();
        logWriter(logList);
    }

    /**
     * 로그분석후 파일생성
     *
     * @param logList
     */
    public static void logWriter(List<LogDto> logList) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter("/Users/traport/Downloads/test/output.log"));
        StringBuilder sb = new StringBuilder();

        //상위 3개
        Map<String, Long> codeCount = logList.stream().collect(groupingBy(LogDto::getStatusCode, counting()));
        codeCount.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue()
                        .reversed()).limit(3).forEachOrdered(it -> sb.append(it.getKey() + "=" + it.getValue() + "\n"));

        //브라우저 점유율
        Map<String, Long> browserCount = logList.stream().collect(groupingBy(LogDto::getBrowser, counting()));
        browserCount.forEach((browser, count) -> sb.append(browser + "=" + browserPercent(count, logList.size()) + "\n"));
        //url 검사 best 파라미터 apikey 또는 q 필수

        bw.write(sb.toString());
        bw.close();
        URL aURL = new URL("http://apis.daum.net/search/image?apikey=2jdc&q=daum");
        Map<String, String> map = getQueryMap(aURL.getQuery());
        System.out.println("test");

    }

    /**
     * 브라우저 점유율
     *
     * @param count
     * @param total
     * @return
     */
    public static String browserPercent(double count, double total) {
        String dispPattern = "0.##";
        DecimalFormat decimalFormat = new DecimalFormat(dispPattern);
        String percent = decimalFormat.format((count / total) * 100);
        return percent;
    }

    /**
     * URL에서 파라미터를 파싱한다.
     *
     * @param query
     * @return
     */
    public static Map<String, String> getQueryMap(String query) {
        String[] params = query.split("&");
        Map<String, String> map = new HashMap<>();
        for (String param : params) {
            String name = param.split("=")[0];
            String value = param.split("=")[1];
            if (name.equals("apikey") || name.equals("q")) map.put(name, value);
        }
        return map;
    }


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LogDto {
        private String statusCode;
        private String requestUrl;
        private String browser;
        private String dateTime;
    }
}

