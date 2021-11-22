package com.music.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("consumer")
public class Consumer {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String username;
    private String password;
    private int sex;
    private String phoneNum;
    private String email;
    private String birth;
    private String introduction;
    private String location;
    private String avator;
    private String createTime;
    private String updateTime;

    @Override
    public String toString() {
        return "Consumer{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", sex=" + sex +
                ", phoneNum='" + phoneNum + '\'' +
                ", email='" + email + '\'' +
                ", birth='" + birth + '\'' +
                ", introduction='" + introduction + '\'' +
                ", location='" + location + '\'' +
                ", avator='" + avator + '\'' +
                ", createTime='" + createTime + '\'' +
                ", updateTime='" + updateTime + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAvator() {
        return avator;
    }

    public void setAvator(String avator) {
        this.avator = avator;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public Consumer() {
    }

    public Consumer(String username, String password, int sex, String phoneNum, String email, String birth, String introduction, String location, String avator, String createTime, String updateTime) {
        this.username = username;
        this.password = password;
        this.sex = sex;
        this.phoneNum = phoneNum;
        this.email = email;
        this.birth = birth;
        this.introduction = introduction;
        this.location = location;
        this.avator = avator;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }
}
