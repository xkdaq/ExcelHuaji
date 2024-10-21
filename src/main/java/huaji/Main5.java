package huaji;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import huaji.bean.Beans;
import huaji.bean.Datass;
import huaji.bean.Questions;

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

//滑记模板
public class Main5 {

    private static String index = "";
    private static String chuchu = "25肖秀荣《背诵手册》190题";

    private static boolean isTypes = false; //开关 控制是否自动分基础和强化，开关打开时，按照下面types的个数排序
    private static int types = 7; //表示前面7个是基础 其余的是强化

    public static void main(String[] args) {
//        //获取单独文件
        start("0" + 1);

        //遍历文件夹
//        for (int i = 0 ; i < 4;i++){
//            if (isTypes) {
//                switch (i) {
//                    case 1:
//                        types = 20;
//                        break;
//                    case 2:
//                        types = 18;
//                        break;
//                    case 3:
//                        types = 22;
//                        break;
//                    case 4:
//                        types = 19;
//                        break;
//                    case 5:
//                        types = 24;
//                        break;
//                    case 6:
//                        types = 36;
//                        break;
//                    case 7:
//                        types = 38;
//                        break;
//                    case 8:
//                        types = 24;
//                        break;
//                    case 9:
//                        types = 23;
//                        break;
//                    case 10:
//                        types = 13;
//                        break;
//                }
//            }
//            if (i<10) {
//                start("0" + i);
//            }else{
//                start(""+i);
//            }
//        }

    }

    public static void start(String ins){
        index = ins;
        File file = new File("/Users/kexu/xukee/java/ExcelTest/src/main/java/keya/zhengzhi/chongci/xindagang/"+index+".txt");
        //System.out.println("-----"+getJson(file));
        String jsonStr = getJson(file);
        //JSONObject json = JSONObject.parseObject(jsonStr);
        //System.out.println(JSONObject.toJSONString(json, true));
        Beans mBeans = JSON.parseObject(jsonStr, Beans.class);
        Datass data = mBeans.getData();
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


    private static void wirte(List<Questions> timus) {

        List<DemoData5> list = new ArrayList<>();  //all
        List<DemoData5> list1 = new ArrayList<>();  //单选题
        List<DemoData5> list2 = new ArrayList<>();  //多选题

        for (int i = 0; i < timus.size(); i++) {
            Questions timu = timus.get(i);

            String type = timu.getType();
            //只筛选单选题和多选题
            if ("1".equals(type)||"2".equals(type)) {
                DemoData5 data = new DemoData5();
                //1.ID
                String timuid = "" + timu.getId();
                data.setId(timuid);

                //2.出处
                String chuchuNew = "";
                if("00".equals(index)){
                    chuchuNew = "333教育综合大纲样卷";
                }else{
                    chuchuNew = chuchu + "第"+index+"套";
                }
                //data.setChuchu(chuchuNew);
                data.setChuchu(chuchu);


                //3.题型 单选题、多选题
                String issingle = timu.getType();
                data.setTixing("1".equals(issingle) ? "单选题" : "多选题");

                //4.题干
                //data.setTigan("");

                //5.题目
                String title = timu.getStem();
                //添加序号
                title = (i + 1) + "." + title;
                //去除转义
                title = StringEscapeUtils.unescapeHtml4(title);
                //去除所有标签 包括换行
                title = removeHtmlTags(title);
                data.setTimu(chuli(title));

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
                String join = String.join("|", optionlist);
                data.setXuanxiang(join);

                //7.答案
                List<String> answers = timu.getAnswer();
                String s2 = convertToString(answers);
                data.setDaan(s2);

                //8.解析
                String jiexi = timu.getAnalysis();

                jiexi = jiexi.replaceAll("☺", "");
                jiexi = jiexi.replaceAll("精研", "猴哥");
                jiexi = jiexi.replaceAll("精讲出处", "精讲出处：");
                jiexi = jiexi.replaceAll("4BACC6", "00B0F0");
                jiexi = jiexi.replaceAll("E36C09", "E36C09");
                jiexi = jiexi.replaceAll("00B0F0", "00B0F0");

                //去除转义符号
                jiexi = StringEscapeUtils.unescapeHtml4(jiexi);
                System.out.println("-----" + jiexi);
                //去除标签
                jiexi = filterTagsNewJiexi(jiexi);
                //System.out.println("-----" + jiexi);
                //jiexi = jiexi + "【公众号：猴子不吃柠檬】";
//            WriteCellData<String> cellData3 = new WriteCellData<>();
//            cellData3.setType(CellDataTypeEnum.RICH_TEXT_STRING);
//            RichTextStringData richTextStringData3 = new RichTextStringData();
//            richTextStringData3.setTextString(jiexi);
//            cellData3.setRichTextStringDataValue(richTextStringData3);
//            data.setJiexi(cellData3);
                data.setJiexi(jiexi);

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

        //第一版是转成excel的标准格式xlsx 然后手动转成csv
//        String fileName0 = index + ".all"+System.currentTimeMillis() + ".xlsx";
//        EasyExcel.write(fileName0, DemoData5.class).sheet("模板").doWrite(list);

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
        //String fileName0 = index + ".all"+System.currentTimeMillis() + ".csv";
        //EasyExcel.write(fileName0, DemoData5.class).excelType(ExcelTypeEnum.CSV).sheet("模板").doWrite(list);


        if (!list1.isEmpty()) {
            String fileName1 = index + ".dan" + System.currentTimeMillis() + ".csv";
            EasyExcel.write(fileName1, DemoData5.class).excelType(ExcelTypeEnum.CSV).sheet("模板").doWrite(list1);
        }

        if (!list2.isEmpty()) {
            String fileName2 = index + ".duo" + System.currentTimeMillis() + ".csv";
            EasyExcel.write(fileName2, DemoData5.class).excelType(ExcelTypeEnum.CSV).sheet("模板").doWrite(list2);
        }
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

    public static String removeHtmlTags(String html) {
        //定义HTML标签的正则表达式
        String regex = "<[^>]+>";
        //使用正则表达式替换HTML标签
        String plainText = html.replaceAll(regex, "");
        //去掉换行
        plainText = plainText.replaceAll("\n", "");
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


    private static String chuli(String str){
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

    private static String chulihuiche(String str){
        String jiexi = str;
        jiexi = jiexi.replaceAll("\n", "");
        return jiexi;
    }

}
