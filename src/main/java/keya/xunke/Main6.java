package keya.xunke;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.fastjson.JSON;
import keya.xunke.bean.Beans6;
import keya.xunke.bean.Datass6;
import keya.xunke.bean.Questions;
import org.apache.commons.lang3.StringEscapeUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//小程序模板
public class Main6 {

    private static String index = "";

    private static boolean isTypes = false; //开关 控制是否自动分基础和强化，开关打开时，按照下面types的个数排序
    private static int types = 7; //表示前面7个是基础 其余的是强化


    private static int muluIndex = 1; //表示一级目录的索引
    private static String[] mulu1 = {
            "0xxxxxxx",
            "01.模拟卷1：会议题",
            "02.模拟卷2：思想解放题",
            "03.模拟卷3：人物题",
            "04.模拟卷4：文献著作题",
            "05.模拟卷5：新大纲及时政题",
            "06.模拟卷6：周年纪念题",
            "07.模拟卷7：keywords专项题",
            "08.模拟卷8：土地政策题",
            "09.模拟卷9：全真模拟"
    };


    public static void main(String[] args) {
//        //获取单独文件
//        start("0" + 3);

        //遍历文件夹
        for (int i = 1; i < 10; i++) {
            muluIndex = i;
            if (i < 10) {
                start("0" + i);
            } else {
                start("" + i);
            }
        }

    }

    public static void start(String ins) {
        index = ins;
        File file = new File("/Users/kexu/xukee/java/ExcelTest/src/main/java/data/zhengzhi/chongci/腿8/" + index + ".txt");
        //System.out.println("-----"+getJson(file));
        String jsonStr = getJson(file);
        //JSONObject json = JSONObject.parseObject(jsonStr);
        //System.out.println(JSONObject.toJSONString(json, true));
        if(jsonStr.isEmpty()) return;
        Beans6 mBeans = JSON.parseObject(jsonStr, Beans6.class);
        Datass6 data = mBeans.getData();
        List<Questions> timu = data.getQuestions();

        //打标签 前面types个 1表示基础 其余的2表示强化
        if (isTypes) {
            Collections.sort(timu, Comparator.comparingInt(o -> (int) o.getIsDid()));
            for (int i = 0; i < timu.size(); i++) {
                if (i < types) {
                    timu.get(i).setTypes(1);
                } else {
                    timu.get(i).setTypes(2);
                }
            }
        }

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

    private static List<DemoData6> list = new ArrayList<>();  //all

    private static void wirte(List<Questions> timus) {


        List<DemoData6> list1 = new ArrayList<>();  //单选题
        List<DemoData6> list2 = new ArrayList<>();  //多选题

        for (int i = 0; i < timus.size(); i++) {
            Questions timu = timus.get(i);

            String type = timu.getType();
            //只筛选单选题和多选题
            if ("1".equals(type) || "2".equals(type)) {
                DemoData6 data = new DemoData6();
                //1.ID
                String timuid = "" + timu.getId();
                data.setId(timuid);

                //2.题目
                String title = timu.getStem();
                //添加序号
                title = (i + 1) + "." + title;
                //去除转义
                title = StringEscapeUtils.unescapeHtml4(title);
                //去除所有标签 包括换行
                title = removeHtmlTags(title);
                data.setTimu(chuli(title));

                //3.题型 单选题、多选题
                String issingle = timu.getType();
                data.setTixing("1".equals(issingle) ? "单选题" : "多选题");

                //4.分数
                data.setFenshu("1".equals(issingle) ? "1" : "2");

                //5.难度
                data.setNandu("简单");

                //6.选项
                List<String> options = timu.getOptions();
                List<String> optionlist = new ArrayList<>();
                for (String option : options) {
                    //去除专义符号
                    option = StringEscapeUtils.unescapeHtml4(option);
                    //去除所有标签 包括换行
                    option = removeHtmlTags(option);
                    optionlist.add(option);
                }
                data.setXuanxiangA(optionlist.get(0));
                data.setXuanxiangB(optionlist.get(1));
                data.setXuanxiangC(optionlist.get(2));
                data.setXuanxiangD(optionlist.get(3));

                //7.答案
                List<String> answers = timu.getAnswer();
                String s2 = convertToString(answers);
                data.setDaan(s2);

                //8.解析
                String jiexi = timu.getAnalysis();

                jiexi = jiexi.replaceAll("☺", "");
                jiexi = jiexi.replaceAll("精研", "可吖");
                jiexi = jiexi.replaceAll("4BACC6", "666666");
                jiexi = jiexi.replaceAll("E36C09", "666666");
                jiexi = jiexi.replaceAll("00B0F0", "666666");

                //去除转义符号
                jiexi = StringEscapeUtils.unescapeHtml4(jiexi);
                System.out.println("1-----" + jiexi);
                //只去除 span标签
                jiexi = filterJiexi(jiexi);
                System.out.println("2-----" + jiexi);
                //System.out.println("-----" + jiexi);
                //jiexi = jiexi + "【公众号：可吖】";
                data.setJiexi(jiexi);

                //一级目录
                data.setMulu1(mulu1[muluIndex]);
                //二级目录
                data.setMulu2("1".equals(issingle)?"单选题":"多选题");


                if (isTypes) {
                    //1基础 2强化
                    int types = timu.getTypes();
                    if (types == 1) {
                        list1.add(data);
                    } else {
                        list2.add(data);
                    }
                } else {
                    //默认按照 1单选 2多选
                    if ("1".equals(issingle)) {
                        list1.add(data);
                    } else {
                        list2.add(data);
                    }
                }
                list.add(data);
            }
        }

        if (!list.isEmpty()) {
            //String fileName0 = index + ".all" + System.currentTimeMillis() + ".xlsx";
            String fileName0 = "xxxxxx.xlsx";
            EasyExcel.write(fileName0, DemoData6.class).excelType(ExcelTypeEnum.XLSX).sheet("模板").doWrite(list);
        }

//        if (!list1.isEmpty()) {
//            String fileName1 = index + ".dan" + System.currentTimeMillis() + ".xlsx";
//            EasyExcel.write(fileName1, DemoData6.class).excelType(ExcelTypeEnum.XLSX).sheet("模板").doWrite(list1);
//        }
//
//        if (!list2.isEmpty()) {
//            String fileName2 = index + ".duo" + System.currentTimeMillis() + ".xlsx";
//            EasyExcel.write(fileName2, DemoData6.class).excelType(ExcelTypeEnum.XLSX).sheet("模板").doWrite(list2);
//        }
    }

    public static String convertToString(List<String> dataList) {
        StringBuilder result = new StringBuilder();

        for (String value : dataList) {
            switch (value) {
                case "0":
                    result.append("A");
                    break;
                case "1":
                    result.append("B");
                    break;
                case "2":
                    result.append("C");
                    break;
                case "3":
                    result.append("D");
                    break;
                default:
                    result.append(filterXuanXian(value));
                    break;
            }
        }

        return result.toString();
    }

    private static String filterXuanXian(String input) {
        //匹配<p>和<span>标签的正则表达式
        String regex = "<p[^>]*>|<\\/p>|<span[^>]*>|<\\/span>|<div[^>]*>|<\\/div>";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        //用空字符串替换匹配到的标签
        String result = matcher.replaceAll("");
        //去掉换行
        result = result.replaceAll("\n", "");
        return result;
    }

    private static String filterJiexi(String input) {
//        //匹配<p>和<span>标签的正则表达式
//        String regex = "<span[^>]*>|<\\/span>";
//        Pattern pattern = Pattern.compile(regex);
//        Matcher matcher = pattern.matcher(input);
//        //用空字符串替换匹配到的标签
//        String result = matcher.replaceAll("");
//        return result;


        // 移除 <span> 标签中的所有属性
        Pattern patternRemoveSpanAttributes = Pattern.compile("<span[^>]*>(.*?)</span>");
        Matcher matcherSpan = patternRemoveSpanAttributes.matcher(input);
        String result = matcherSpan.replaceAll("<strong>$1</strong>");

        // 移除 <strong> 标签中的所有属性
        Pattern patternRemoveStrongAttributes = Pattern.compile("<strong[^>]*>(.*?)</strong>");
        Matcher matcherStrong = patternRemoveStrongAttributes.matcher(result);
        result = matcherStrong.replaceAll("<strong>$1</strong>");

        // 处理嵌套的 <strong> 标签，保留一个 <strong>
        Pattern patternNestedStrong = Pattern.compile("<strong>(\\s*<strong[^>]*>(.*?)</strong>\\s*)</strong>");
        Matcher matcherNestedStrong = patternNestedStrong.matcher(result);
        result = matcherNestedStrong.replaceAll("<strong>$2</strong>");

        return result;

    }

    public static String removeHtmlTags(String html) {
        //定义HTML标签的正则表达式
        String regex = "<[^>]+>";
        //使用正则表达式替换HTML标签
        String plainText = html.replaceAll(regex, "");
        //去掉换行
        plainText = plainText.replaceAll("\n", "");
        return plainText;
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
