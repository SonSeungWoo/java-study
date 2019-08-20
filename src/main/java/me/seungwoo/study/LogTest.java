package me.seungwoo.study;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
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
            logDto.setRequestTime(list.get(3));
            logList.add(logDto);
            //여기까지 수정
        }
        br.close();

        //상위 3개
        Map<String, Long> codeCount = logList.stream().collect(groupingBy(LogDto::getStatusCode, counting()));
        codeCount.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue()
                        .reversed()).limit(3).forEachOrdered(e -> System.out.println(e.getKey() + "=" + e.getValue()));

        //브라우저 점유율
        String dispPattern = "0.##";
        DecimalFormat format = new DecimalFormat(dispPattern);
        Map<String, Long> browserCount = logList.stream().collect(groupingBy(LogDto::getBrowser, counting()));
        browserCount.forEach((browser, count) -> System.out.println(browser + "=" + format.format((double) count / (double) logList.size() * 100)));

        //url 검사
        //apikey 또는 q 필수

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LogDto {
        private String statusCode;
        private String requestUrl;
        private String browser;
        private String requestTime;
    }
}

