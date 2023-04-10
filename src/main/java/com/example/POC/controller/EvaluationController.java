package com.example.POC.controller;

import java.io.FileWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.POC.service.EvaluationServices;

@RestController
@RequestMapping("/test")
public class EvaluationController {

  @Autowired
  EvaluationServices evaluationServices;


  @GetMapping("/data")
  String getThymeleafData() {

    Long inTime = System.currentTimeMillis();
    String result = evaluationServices.evaluateThymeleaf();
    Long outTime = System.currentTimeMillis();


    try {
      FileWriter fw = new FileWriter("/home/fa059089/Desktop/timeDump.txt", true);
      fw.write((outTime - inTime) + "\n");
      fw.close();
    } catch (Exception e) {
      System.out.println(e);
    }


    return result;

  }

  @GetMapping("/data2")
  String getJtwigData() {
    Long inTime = System.currentTimeMillis();
    String result = evaluationServices.evaluateJtwig();
    Long outTime = System.currentTimeMillis();


    try {
      FileWriter fw = new FileWriter("/home/fa059089/Desktop/timeDump.txt", true);
      fw.write((outTime - inTime) + "\n");
      fw.close();
    } catch (Exception e) {
      System.out.println(e);
    }


    return result;
  }

  @GetMapping("/data3")
  String getFreemarkerData() {
    Long inTime = System.currentTimeMillis();
    String result = evaluationServices.evaluateFreemarker();
    Long outTime = System.currentTimeMillis();

    try {
      FileWriter fw = new FileWriter("/home/fa059089/Desktop/timeDump.txt", true);
      fw.write((outTime - inTime) + "\n");
      fw.close();
    } catch (Exception e) {
      System.out.println(e);
    }

    return result;
  }

  @GetMapping("/data4")
  String getPebbleData() {
    String result = evaluationServices.evaluatePebble();

    return result;
  }


}
