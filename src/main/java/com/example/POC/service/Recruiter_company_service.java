package com.example.POC.service;

import java.util.ArrayList;
import java.util.List;

public class Recruiter_company_service {
  public String groupName;
  public List<String> customizedTags = new ArrayList<>();
  public String overviewUrl;
  public boolean ratingSettings;


  public String getGroupName() {
    return groupName;
  }

  public void setGroupName(String groupName) {
    this.groupName = groupName;
  }

  public void getSetOne() {
    this.groupName = "Group name One";
    this.overviewUrl = "this_is_overviewUrlOne";
    this.customizedTags.add("#tagOne1");
    this.customizedTags.add("#tagOne2");
    this.customizedTags.add("#tagOne3");
    this.ratingSettings = false;
  }

  public void getSetTwo() {
    this.groupName = "Group name Two";
    this.overviewUrl = "this_is_overviewUrlTwo";
    this.customizedTags.add("#tagTwo1");
    this.customizedTags.add("#tagTwo2");
    this.customizedTags.add("#tagTwo3");
    this.ratingSettings = false;
  }

  @Override
  public String toString() {
    return "Recruiter_company_service [groupName=" + groupName + ", customizedTags="
        + customizedTags + ", overviewUrl=" + overviewUrl + ", ratingSettings=" + ratingSettings
        + "]";
  }

}
