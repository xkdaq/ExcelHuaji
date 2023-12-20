package kuozhi;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.data.RichTextStringData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import kuozhi.bean.KBean;
import kuozhi.bean.QuestionContent;
import org.apache.commons.lang3.StringEscapeUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

//滑记模板
public class Main4 {

    private static String index = "6";
    private static String chuchu = "2024肖秀荣《4套卷》时政";

    public static void main(String[] args) {
        File file = new File("/Users/kexu/xukee/java/ExcelTest/src/main/java/kuozhi/shizheng/"+index+".txt");
        //System.out.println("-----"+getJson(file));
        String jsonStr = getJson(file);
        JSONObject json = JSONObject.parseObject(jsonStr);
        //System.out.println(JSONObject.toJSONString(json, true));
        KBean kBean = JSON.parseObject(jsonStr, KBean.class);
        List<QuestionContent> timu = kBean.getQuestionContent();
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


    private static void wirte(List<QuestionContent> timus) {
        List<DemoData4> list = new ArrayList<>();  //all
        List<DemoData4> list1 = new ArrayList<>();  //单选题
        List<DemoData4> list2 = new ArrayList<>();  //多选题

        for (int i = 0; i < timus.size(); i++) {
            QuestionContent timu = timus.get(i);

            DemoData4 data = new DemoData4();
            //ID
            String timuid = timu.getGlobalid();
            data.setId("1"+timuid);
            //出处
            String chuchuNew = chuchu + "第"+index+"套";
            data.setChuchu(chuchuNew);
            //题型 多选题
            int issingle = timu.getIssingle();
            data.setTixing(issingle == 1 ?"单选题":"多选题");
            //题干
            data.setTigan("");
            //题目
            String title = timu.getQuestion();
            title = (i + 1) + "." + title;
            data.setTimu(chuli(title));
            //选项
            String xuanText = timu.getChoiceA() + "|" + timu.getChoiceB()+ "|" + timu.getChoiceC()+"|" + timu.getChoiceD();
            xuanText = chulihuiche(xuanText);
            data.setXuanxiang(xuanText);
            //答案
            String rightText = timu.getAnswer();
            data.setDaan(rightText);
            //解析
            String jiexi = timu.getExplain();
            jiexi = StringEscapeUtils.unescapeHtml4(jiexi);
            System.out.println("-----" + jiexi);
            jiexi = removeHtmlTags(jiexi);
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


            if (issingle == 1) {
                list1.add(data);
            }else{
                list2.add(data);
            }
            list.add(data);
        }

        String fileName0 = index + ".all"+System.currentTimeMillis() + ".xlsx";
        EasyExcel.write(fileName0, DemoData4.class).sheet("模板").doWrite(list);

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

    private static String chulihuiche(String str){
        String jiexi = str;
        jiexi = jiexi.replaceAll("\n", "");
        return jiexi;
    }

}
