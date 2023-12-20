package cangdun;

import cangdun.bean.CangBean;
import cangdun.bean.Text;
import cangdun.bean.Timu;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.data.RichTextStringData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringEscapeUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

//滑记模板
public class Main3 {

    private static String index = "6";
    private static String chuchu = "2024米鹏《3套卷》";

    public static void main(String[] args) {
        File file = new File("/Users/kexu/xukee/java/ExcelTest/src/main/java/cangdun/maozi/"+index+".txt");
        //System.out.println("-----"+getJson(file));
        String jsonStr = getJson(file);
        JSONObject json = JSONObject.parseObject(jsonStr);
        //System.out.println(JSONObject.toJSONString(json, true));
        CangBean cangBean = JSON.parseObject(jsonStr, CangBean.class);
        List<Timu> timu = cangBean.getDetail().getTimu();
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


    private static void wirte(List<Timu> timus) {
        List<DemoData3> list = new ArrayList<>();  //all
        List<DemoData3> list1 = new ArrayList<>();  //单选题
        List<DemoData3> list2 = new ArrayList<>();  //多选题
        for (int i = 0; i < timus.size(); i++) {
            Timu timu = timus.get(i);

            DemoData3 data = new DemoData3();
            //ID
            String timuid = timu.getTimuid();
            data.setId("1"+timuid);
            //出处
            //String chuchuNew = chuchu + "第"+index+"套";
            String chuchuNew = timu.getChuchu();
            data.setChuchu(chuchuNew);
            //题型 多选题
            data.setTixing(timu.getType().equals("单选")?"单选题":"多选题");
            //题干
            data.setTigan("");
            //题目
            String title = timu.getTitle();
            title = (i + 1) + "." + title;
            data.setTimu(chuli(title));
            //选项
            String xuanText = timu.getXuanText();
//            StringBuilder xuanxiang = new StringBuilder();
//            String[] arr = xuanText.split("\\|"); //拆分字符串
//            List<String> strings = Arrays.asList(arr);
//            for (String option : strings) {
//                option = option.replaceAll(" ", "");
//                option = option.replaceAll("A.", "");
//                option = option.replaceAll("B.", "");
//                option = option.replaceAll("C.", "");
//                option = option.replaceAll("D.", "");
//                xuanxiang.append(option).append("|");
//            }
//            String s1 = xuanxiang.toString();
            xuanText = xuanText.replaceAll(" ", "");
            xuanText = xuanText.replaceAll("A.", "");
            xuanText = xuanText.replaceAll("B.", "");
            xuanText = xuanText.replaceAll("C.", "");
            xuanText = xuanText.replaceAll("D.", "");
            data.setXuanxiang(xuanText);
            //答案
            Text rightText = timu.getRightText();
            String s2 = rightText.toStrValue();
            data.setDaan(s2);
            //解析
            String jiexi = timu.getJiexi();
            jiexi = StringEscapeUtils.unescapeHtml4(jiexi);
            System.out.println("-----" + jiexi);
            //jiexi = removeHtmlTags(jiexi);
            jiexi = jiexi + "";
            jiexi = jiexi.replaceAll("干扰项辨识","【干扰项辨识】");
            jiexi = jiexi.replaceAll("复盘指南","【复盘指南】");
            System.out.println("-----" + jiexi);
            WriteCellData<String> cellData3 = new WriteCellData<>();
            cellData3.setType(CellDataTypeEnum.RICH_TEXT_STRING);
            RichTextStringData richTextStringData3 = new RichTextStringData();
            richTextStringData3.setTextString(jiexi);
            cellData3.setRichTextStringDataValue(richTextStringData3);
            data.setJiexi(cellData3);

            if (timu.getType().equals("单选")) {
                list1.add(data);
            }else{
                list2.add(data);
            }
            list.add(data);
        }

        String fileName0 = index + ".all"+System.currentTimeMillis() + ".xlsx";
        EasyExcel.write(fileName0, DemoData3.class).sheet("模板").doWrite(list);

//        String fileName1 = index + ".dan"+System.currentTimeMillis() + ".xlsx";
//        EasyExcel.write(fileName1, DemoData3.class).sheet("模板").doWrite(list1);
//
//        String fileName2 = index + ".duo"+System.currentTimeMillis() + ".xlsx";
//        EasyExcel.write(fileName2, DemoData3.class).sheet("模板").doWrite(list2);
    }


    public static String removeHtmlTags(String html) {
        // 定义HTML标签的正则表达式
        String regex = "<[^>]+>";
        // 使用正则表达式替换HTML标签
        String plainText = html.replaceAll(regex, "");
        return plainText;
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

}
