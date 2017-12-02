package com.toan_itc.tn.Model;

import java.util.Date;

/**
 * Created by vantoan on 2/17/17.
 * Email: huynhvantoan.itc@gmail.com
 */
public class User {

  private Builder builder;

  public User(Builder builder){
    this.builder = builder;
  }

  public static class Builder {
    private int id;
    private String name;
    private String email;
    private Date birthday;
    private int weight;
    private int height;
    private int groupId;
    private int companyId;

    public Builder setId(int id) {
      this.id = id;
      return this;
    }

    public Builder setName(String name) {
      this.name = name;
      return this;
    }

    public Builder setEmail(String email) {
      this.email = email;
      return this;
    }

    public Builder setBirthday(Date birthday) {
      this.birthday = birthday;
      return this;
    }
    public Builder setWeight(int weight) {
      this.weight = weight;
      return this;
    }

    public Builder setHeight(int height) {
      this.height = height;
      return this;
    }

    public Builder setGroupId(int groupId) {
      this.groupId = groupId;
      return this;
    }

    public Builder setCompanyId(int companyId) {
      this.companyId = companyId;
      return this;
    }

    public User build(){
      validate();
      return new User(this);
    }

    private void validate(){}

  }

}
