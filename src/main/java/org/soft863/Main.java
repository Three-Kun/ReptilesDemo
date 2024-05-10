package org.soft863;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.ArrayList;

public class Main {
    protected static final String COOKIE = "";  // 登录账号cookie
    protected static final String FILE_NAME = "";    // 文件保存路径
    protected static final String FIND_NAME = ""; // 搜索关键字

    public static void main(String[] args) {
        try {
            // 省份代码
            ArrayList<Integer> provinces = new ArrayList<>();
            provinces.add(530);
            provinces.add(532);
            provinces.add(545);

            for (Integer province : provinces) {
                int i = 1;
                String soupager_index = getElements(i, province);
                if (i <= Integer.parseInt(soupager_index)) {
                    i++;
                    getElements(i, province);
                }
                Thread.sleep(3000); // 休眠3秒，防止防爬
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getElements(int i, int province) throws IOException {
        ArrayList<Post> data = new ArrayList<>();
        // 使用Jsoup连接到指定的网址，并获取HTML内容
        String urlLong = "https://sou.zhaopin.com/?jl=" + province + "&kw=" + FIND_NAME + "&p=" + i;
        // cookie
//        String cookie = "";
        Document doc = Jsoup.connect(urlLong).cookie("Cookie", COOKIE).get();

        Elements elements = doc.getElementsByClass("positionlist");
        Elements href = elements.first().getElementsByClass("joblist-box__iteminfo");
        // 获取字段，写入实体类
        for (Element content : href) {
            Post post = new Post();
            Element jobinfo__top = content.child(0).child(0);
            Element jobinfo__tag = content.child(0).child(1);
            Element companyinfo__top = content.child(1).child(0);
            Element jobinfo__other_info = jobinfo__tag.lastElementSibling();
            String url = jobinfo__top.children().attr("href");
            String jobinfo_Name = jobinfo__top.child(0).text();
            String salary = jobinfo__top.getElementsByClass("jobinfo__salary").text();
            String tag = content.child(0).getElementsByClass("jobinfo__tag").text() + "\t";
            String address = jobinfo__other_info.child(0).text();
            String experience = jobinfo__other_info.child(1).text();
            String education = jobinfo__other_info.child(2).text();
            String companyinfo_name = companyinfo__top.text();
            post.setUrl(url);
            post.setJobinfo_Name(jobinfo_Name);
            post.setSalary(salary);
            post.setTag(tag + "\t");
            post.setAddress(address);
            post.setExperience(experience);
            post.setEducation(education);
            post.setCompanyinfo_name(companyinfo_name);
            data.add(post);
        }

        // 导出
        String fileName = "";

        File file = new File(FILE_NAME);
        if (file.isFile()) {
            appendDate(FILE_NAME, data);
        } else {
            exportToExcel(data, FILE_NAME);
        }

        // 获取页码
        String soupager_index = elements.first().getElementsByClass("soupager__index").last().html();
        return soupager_index;
    }

    /*追加数据*/
    public static void appendDate(String filename, ArrayList<Post> posts) {
        try {
            FileInputStream fileInputStream = new FileInputStream(filename);
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(fileInputStream);
            XSSFSheet sheet = xssfWorkbook.getSheetAt(0);
            FileOutputStream fileOutputStream = new FileOutputStream(filename);

            for (Post port : posts) {
                XSSFRow row = sheet.createRow(sheet.getLastRowNum() + 1);//新增一行
                row.createCell(0).setCellValue(port.getUrl());
                row.createCell(1).setCellValue(port.getJobinfo_Name());
                row.createCell(2).setCellValue(port.getSalary());
                row.createCell(3).setCellValue(port.getTag());
                row.createCell(4).setCellValue(port.getAddress());
                row.createCell(5).setCellValue(port.getEducation());
                row.createCell(6).setCellValue(port.getCompanyinfo_name());
                row.createCell(7).setCellValue(port.getExperience());
            }

            fileOutputStream.flush();
            xssfWorkbook.write(fileOutputStream);
            fileOutputStream.close();

        } catch (FileNotFoundException e) {
            System.out.println("excel导出异常");
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void exportToExcel(ArrayList<Post> posts, String a) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Posts");

        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("URL");
        headerRow.createCell(1).setCellValue("岗位名称");
        headerRow.createCell(2).setCellValue("薪资");
        headerRow.createCell(3).setCellValue("标签");
        headerRow.createCell(4).setCellValue("地址");
        headerRow.createCell(5).setCellValue("学历");
        headerRow.createCell(6).setCellValue("公司名称");
        headerRow.createCell(7).setCellValue("经验");

        // 填充数据
        int rowNum = 1;
        for (Post person : posts) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(person.getUrl());
            row.createCell(1).setCellValue(person.getJobinfo_Name());
            row.createCell(2).setCellValue(person.getSalary());
            row.createCell(3).setCellValue(person.getTag());
            row.createCell(4).setCellValue(person.getAddress());
            row.createCell(5).setCellValue(person.getEducation());
            row.createCell(6).setCellValue(person.getCompanyinfo_name());
            row.createCell(7).setCellValue(person.getExperience());
        }

        // 保存文件
        try (FileOutputStream outputStream = new FileOutputStream(a)) {
            workbook.write(outputStream);
        }
        workbook.close();
    }
}




