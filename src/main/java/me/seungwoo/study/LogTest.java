package me.seungwoo.study;

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

    /**
     * 최다호출횟수 최대200인것만 카운트 apikey
     * 상위 3개 service id
     * 브라우저 점유율
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        Pattern p = Pattern.compile("\\[(.*?)\\]");
        List<LogDto> logList = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader("../../Downloads/test/input.log"));
        while (!isEmpty(br.readLine())) {
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
        //logWriter(logList);

        URL aURL = new URL("http://apis.daum.net/search/image?apikey=2jdc&q=daum");
        String param = aURL.getPath().replace("/","").replace("search","");
        System.out.println(param);

    }

    /**
     * 로그분석후 파일생성
     *
     * @param logList
     */
    public static void logWriter(List<LogDto> logList) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter("../../Downloads/test/output.log"));
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

        //apikey 최다호출 상태코드 200인것만 카운트, search/ 뒤에붙는 서비스아이디
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
        String dispPattern = "0";
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

    public static boolean isEmpty(Object str) {
        return str == null || "".equals(str);
    }


    public static class LogDto {
        private String statusCode;
        private String requestUrl;
        private String browser;
        private String dateTime;

        public String getStatusCode() {
            return statusCode;
        }

        public void setStatusCode(String statusCode) {
            this.statusCode = statusCode;
        }

        public String getRequestUrl() {
            return requestUrl;
        }

        public void setRequestUrl(String requestUrl) {
            this.requestUrl = requestUrl;
        }

        public String getBrowser() {
            return browser;
        }

        public void setBrowser(String browser) {
            this.browser = browser;
        }

        public String getDateTime() {
            return dateTime;
        }

        public void setDateTime(String dateTime) {
            this.dateTime = dateTime;
        }
    }
}

