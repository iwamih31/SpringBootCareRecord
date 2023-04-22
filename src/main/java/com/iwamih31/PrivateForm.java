package com.iwamih31;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrivateForm {

  // 部屋番号
  private Integer room;

  // 名前
  private String name;

  // 日付
  private String date;

  // 時間
  private String time;

  // 睡眠
  private String sleep;

  // 水分
  private String water;

  // 排尿1
  private String pee1;

  // 排尿2
  private String pee2;

  // 排便
  private String poop;

  // 下剤
  private String laxative;

  // 服薬
  private String medicine;

  // 処置
  private String ointment;

  // 様子
  private String situation;
}
