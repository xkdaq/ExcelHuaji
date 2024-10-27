package huaji.nanshan;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.google.gson.Gson;
import connnect.HttpClient;
import huaji.nanshan.bean.Datum;
import huaji.nanshan.bean.ListBen;
import huaji.nanshan.bean.ListItBean;
import huaji.nanshan.bean.NanBean;
import org.apache.commons.lang3.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//滑记模板（Data来源：南南小程序）
public class Main55 {

    private static String index = "1";
    private static String chuchu = "25米鹏《6套卷》";

    private static int muluIndex = 0; //表示一级目录的索引

    private static List<String> mulu1 = new ArrayList<>();

    public static void main(String[] args) {
        //获取单独文件
        //start("0" + 1);
        //muluIndex = i;

        //遍历文件夹
//        for (int i = 1 ; i < 3;i++){
//            muluIndex = i;
//            if (i<10) {
//                start("0" + i);
//            }else{
//                start(""+i);
//            }
//        }
        getMulu();
    }
    public static ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public static void getMulu() {
        File file = new File("/Users/kexu/xukee/java/ExcelTest/src/main/java/data-2/mi/list.txt");
        String jsonStr = getJson(file);
        Gson gson = new Gson();
        ListBen mBeans = gson.fromJson(jsonStr, ListBen.class);
        if (mBeans != null) {
            List<ListItBean> data = mBeans.getData();
            mulu1.clear();
            for (int i = 0; i < data.size(); i++) {
                int index = i;
                scheduler.schedule(() -> {
                    muluIndex = index;
                    ListItBean listItem = data.get(index);
                    mulu1.add(index, listItem.getName());
                    startNet("" + listItem.getid());
                }, i * 3, TimeUnit.SECONDS);
            }
        }

    }

    public static void startNet(String id) {
        String response = HttpClient.get("api/v1/tk/teackerTk/getQueTkByTeaTkid?tertkid=" + id);
        System.out.println("GET Response: " + response);
        excJson(response);
    }


    public static void start(String ins) {
        index = ins;
        File file = new File("/Users/kexu/xukee/java/ExcelTest/src/main/java/data-2/tuijie/" + index + ".txt");
        String jsonStr = getJson(file);
        //NanBean mBeans = JSON.parseObject(jsonStr, NanBean.class);
        excJson(jsonStr);
    }


    public static void excJson(String jsonStr) {
        Gson gson = new Gson();
        NanBean mBeans = gson.fromJson(jsonStr, NanBean.class);

        List<Datum> timu = mBeans.getData();

        //System.out.println("-----" + cangBean.getDetail().getTimu().size());
        wirte(timu);
    }


    public static String getJson(File file) {
        try {
            if (file.isFile() && file.exists()) {
                FileInputStream fileInputStream = null;
                fileInputStream = new FileInputStream(file);
                return getString(fileInputStream);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private static String getString(FileInputStream fileInputStream) {
        InputStreamReader is = new InputStreamReader(fileInputStream);
        StringBuilder result = new StringBuilder();
        BufferedReader bufferedReader = new BufferedReader(is);
        String line = "";
        try {
            while ((line = bufferedReader.readLine()) != null) {
                result.append(line);
            }
            return result.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private static List<DemoData55> list = new ArrayList<>();  //all

    private static void wirte(List<Datum> timus) {


        List<DemoData55> list1 = new ArrayList<>();  //单选题
        List<DemoData55> list2 = new ArrayList<>();  //多选题

        for (int i = 0; i < timus.size(); i++) {
            Datum timu = timus.get(i);
            System.out.println("-----" + timu.toString());
            //只筛选单选题和多选题
            if (true) {
                DemoData55 data = new DemoData55();
                //1.ID
                String timuid = "" + timu.getid();
                data.setId(timuid);

                //2.出处
//                String chuchuNew = "";
//                if ("00".equals(index)) {
//                    chuchuNew = chuchu;
//                } else {
//                    chuchuNew = chuchu + "第" + index + "套";
//                }
//                data.setChuchu(chuchuNew);
//                data.setChuchu(chuchu);
                data.setChuchu(chuchu+" "+ mulu1.get(muluIndex));


                //3.题型 单选题、多选题
                boolean isMSelect = timu.getIsMSelect(); //isMSelect true多选 false单选
                data.setTixing(!isMSelect ? "单选题" : "多选题");

                //5.题目
                String title = timu.getTitle();
                //添加序号
                title = (i + 1) + "." + title;
                //去除转义
                title = StringEscapeUtils.unescapeHtml4(title);
                //去除所有标签 包括换行
                title = removeHtmlTags(title);
                data.setTimu(chuli(title));

                //6.选项
                List<String> options = new ArrayList<>();
                String a = timu.getA();
                String b = timu.getB();
                String c = timu.getC();
                String d = timu.getD();
                options.add(a);
                options.add(b);
                options.add(c);
                options.add(d);
                List<String> optionlist = new ArrayList<>();
                for (String option : options) {
                    //去除专义符号
                    option = StringEscapeUtils.unescapeHtml4(option);
                    //去除所有标签 包括换行
                    option = removeHtmlTags(option);
                    optionlist.add(option);
                }
                String join = String.join("|", optionlist);
                data.setXuanxiang(join);

                //7.答案
                String correct = timu.getCorrect();
                String s2 = convertToString(correct);
                data.setDaan(s2);

                //8.解析
                String jiexi = timu.getExplain();

                jiexi = jiexi.replaceAll("考研路透社", "大象不吃草莓");
                jiexi = jiexi.replaceAll("南山小菊花", "猴子不吃柠檬");
                jiexi = jiexi.replaceAll("南南上岸", "可吖");

                //去除转义符号
                jiexi = StringEscapeUtils.unescapeHtml4(jiexi);
                jiexi = jiexi.replaceAll(" ", "");
                System.out.println("-----" + jiexi);
                //去除标签
                //jiexi = filterTagsNewJiexi(jiexi);
                //System.out.println("-----" + jiexi);
                //jiexi = jiexi + "【公众号：猴子不吃柠檬】";
                data.setJiexi(jiexi);

                //一级目录
                data.setMulu1(mulu1.get(muluIndex));
                //二级目录
                data.setMulu2(!isMSelect ? "单选题" : "多选题");

                //默认按照 1单选 2多选
                if (!isMSelect) {
                    list1.add(data);
                } else {
                    list2.add(data);
                }
                list.add(data);
            }
        }

//        if (!list.isEmpty()) {
//            //第一版是转成excel的标准格式xlsx 然后手动转成csv
//            String fileName0 = "1111.all.xlsx";
//            EasyExcel.write(fileName0, DemoData55.class).sheet("模板").doWrite(list);
//        }

//        if (!list1.isEmpty()) {
//            String fileName1 = index + ".dan" + System.currentTimeMillis() + ".xlsx";
//            EasyExcel.write(fileName1, DemoData5.class).sheet("模板").doWrite(list1);
//        }
//
//        if (!list2.isEmpty()) {
//            String fileName2 = index + ".duo" + System.currentTimeMillis() + ".xlsx";
//            EasyExcel.write(fileName2, DemoData5.class).sheet("模板").doWrite(list2);
//        }

        //第二版更新直接转成csv 注意点：修改了解析的类型从WriteCellData改为了String 测试通过
//        String fileName0 = index + ".all"+System.currentTimeMillis() + ".csv";
//        EasyExcel.write(fileName0, DemoData5.class).excelType(ExcelTypeEnum.CSV).sheet("模板").doWrite(list);


        if (!list1.isEmpty()) {
            String fileName1 = (muluIndex+1) + ".dan" + System.currentTimeMillis() + ".csv";
            EasyExcel.write(fileName1, DemoData55.class).excelType(ExcelTypeEnum.CSV).sheet("模板").doWrite(list1);
        }

        if (!list2.isEmpty()) {
            String fileName2 = (muluIndex+1) + ".duo" + System.currentTimeMillis() + ".csv";
            EasyExcel.write(fileName2, DemoData55.class).excelType(ExcelTypeEnum.CSV).sheet("模板").doWrite(list2);
        }
    }

    public static String convertToString(String input) {
        StringBuilder result = new StringBuilder();
        String[] parts = input.split(",");
        for (String value : parts) {
            switch (value) {
                case "1":
                    result.append("A");
                    break;
                case "2":
                    result.append("B");
                    break;
                case "3":
                    result.append("C");
                    break;
                case "4":
                    result.append("D");
                    break;
                default:
                    throw new IllegalArgumentException("Invalid input: " + value);
            }
        }

        return result.toString();
    }

    public static String removeHtmlTags(String html) {
        //定义HTML标签的正则表达式
        String regex = "<[^>]+>";
        //使用正则表达式替换HTML标签
        String plainText = html.replaceAll(regex, "");
        //去掉换行
        plainText = plainText.replaceAll("\n", "");
        //去除空格
        plainText = plainText.replaceAll(" ", "");
        return plainText;
    }

    private static String filterTagsNewJiexi(String input) {
        String result = "";
        if (true) {
            //先把p标签转成换行的文本
            input = filterP(input);

            // 匹配<p>和<span>标签的正则表达式
            String regex = "<p[^>]*>|<\\/p>|<span[^>]*>|<\\/span>|<div[^>]*>|<\\/div>";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(input);
            // 用空字符串替换匹配到的标签
            result = matcher.replaceAll("");
            result = result.replaceAll("<br>", "\r\n");

            // 使用正则表达式替换<u>标签为<strong>标签
            result = result.replaceAll("<u>(.*?)</u>", "<strong>$1</strong>");

        } else {
            result = input;
        }
        return result;
    }


    public static String filterP(String html) {
        // 使用Jsoup解析HTML字符串
        Document doc = Jsoup.parse(html);
        // 选择所有的<p>标签
        Elements paragraphs = doc.select("p");
        // 使用StringBuilder构建结果
        StringBuilder result = new StringBuilder();
        // 遍历每个<p>标签，提取文本内容并添加换行
        for (Element paragraph : paragraphs) {
            String paragraphContent = paragraph.html();
            result.append(paragraphContent).append("\r\n");
        }
        // 删除最后多余的换行
        if (result.length() >= 2) {
            result.delete(result.length() - 2, result.length());
        }
        return result.toString();
    }


    private static String chuli(String str) {
        String jiexi = str;
        jiexi = jiexi.replaceAll(" ", "");
        jiexi = jiexi.replaceAll("<p>", "");
        jiexi = jiexi.replaceAll("</p>", "");
        jiexi = jiexi.replaceAll("<strong>", "");
        jiexi = jiexi.replaceAll("</strong>", "");
        jiexi = jiexi.replaceAll("<br/>", "\r\n");
        jiexi = jiexi.replaceAll("<br />", "\r\n");
        return jiexi;
    }

    private static String chulihuiche(String str) {
        String jiexi = str;
        jiexi = jiexi.replaceAll("\n", "");
        return jiexi;
    }

}
