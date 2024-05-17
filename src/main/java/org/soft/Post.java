package org.soft;

import com.alibaba.excel.annotation.ExcelProperty;

public class Post {
    @ExcelProperty("URL")
    private String url;

    @ExcelProperty("岗位名称")
    private String jobinfo_Name;

    @ExcelProperty("薪资")
    private String salary;

    @ExcelProperty("标签")
    private String tag;

    @ExcelProperty("地址")
    private String address;

    @ExcelProperty("学历")
    private String education;

    @ExcelProperty("公司名称")
    private String companyinfo_name;

    @ExcelProperty("经验")
    private String experience;

    public Post(String url, String jobinfo_Name, String salary, String tag, String address, String education, String companyinfo_name, String experience) {
        this.url = url;
        this.jobinfo_Name = jobinfo_Name;
        this.salary = salary;
        this.tag = tag;
        this.address = address;
        this.education = education;
        this.companyinfo_name = companyinfo_name;
        this.experience = experience;
    }

    public Post() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getJobinfo_Name() {
        return jobinfo_Name;
    }

    public void setJobinfo_Name(String jobinfo_Name) {
        this.jobinfo_Name = jobinfo_Name;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getCompanyinfo_name() {
        return companyinfo_name;
    }

    public void setCompanyinfo_name(String companyinfo_name) {
        this.companyinfo_name = companyinfo_name;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }
}
