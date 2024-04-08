package xunke;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.data.RichTextStringData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import xunke.bean.Beans;
import xunke.bean.Datass;
import xunke.bean.Questions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//滑记模板
public class Main5 {

    private static String index = "10";
    private static String chuchu = "2025徐涛《优题库》";

    public static void main(String[] args) {
        File file = new File("/Users/kexu/xukee/java/ExcelTest/src/main/java/xunke/xutao/1/"+index+".txt");
        //System.out.println("-----"+getJson(file));
        String jsonStr = getJson(file);
        //JSONObject json = JSONObject.parseObject(jsonStr);
        //System.out.println(JSONObject.toJSONString(json, true));
        Beans mBeans = JSON.parseObject(jsonStr, Beans.class);
        Datass data = mBeans.getData();
        List<Questions> timu = data.getQuestions();
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

            DemoData5 data = new DemoData5();
            //ID
            String timuid = ""+timu.getId();
            data.setId(timuid);
            //出处
            //String chuchuNew = chuchu + "第"+index+"套";
            data.setChuchu(chuchu);
            //题型 多选题
            String issingle = timu.getType();
            data.setTixing( "1".equals(issingle)  ?"单选题":"多选题");
            //题干
            data.setTigan("");
            //题目
            String title = timu.getStem();
            title = (i + 1) + "." + title;
            data.setTimu(chuli(title));
            //选项
            //String xuanText = timu.getChoiceA() + "|" + timu.getChoiceB()+ "|" + timu.getChoiceC()+"|" + timu.getChoiceD();
            //xuanText = chulihuiche(xuanText);

            StringBuilder xuanxiang = new StringBuilder();
            List<String> options = timu.getOptions();
            for (String option : options) {
                option = removeHtmlTags(option);
                xuanxiang.append(option).append("|");
            }
            String s1 = xuanxiang.toString();
            data.setXuanxiang(s1);
            //答案
            List<String> answers = timu.getAnswer();
            String s2 = convertToString(answers);
            data.setDaan(s2);
            //解析
            String jiexi = timu.getAnalysis();

            jiexi = StringEscapeUtils.unescapeHtml4(jiexi);
            System.out.println("-----" + jiexi);
            jiexi = filterTagsNewJiexi(jiexi);
            System.out.println("-----" + jiexi);
            WriteCellData<String> cellData3 = new WriteCellData<>();
            cellData3.setType(CellDataTypeEnum.RICH_TEXT_STRING);
            RichTextStringData richTextStringData3 = new RichTextStringData();
            richTextStringData3.setTextString(jiexi);
            cellData3.setRichTextStringDataValue(richTextStringData3);
            data.setJiexi(cellData3);


            if ("1".equals(issingle)) {
                list1.add(data);
            }else{
                list2.add(data);
            }
            list.add(data);
        }

//        String fileName0 = index + ".all"+System.currentTimeMillis() + ".xlsx";
//        EasyExcel.write(fileName0, DemoData4.class).sheet("模板").doWrite(list);

        String fileName1 = index + ".dan"+System.currentTimeMillis() + ".xlsx";
        EasyExcel.write(fileName1, DemoData5.class).sheet("模板").doWrite(list1);

        String fileName2 = index + ".duo"+System.currentTimeMillis() + ".xlsx";
        EasyExcel.write(fileName2, DemoData5.class).sheet("模板").doWrite(list2);
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
        // 定义HTML标签的正则表达式
        String regex = "<[^>]+>";
        // 使用正则表达式替换HTML标签
        String plainText = html.replaceAll(regex, "");
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
