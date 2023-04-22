package com.iwamih31;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "action")
public class Action {

  // ID
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Integer id;

  // ユーザーID
  @Column(name = "user_id", nullable = false)
  private Integer user_id;

  // 日付
  @Column(name = "date", nullable = false)
  private String date;

  // 時間
  @Column(name = "time", nullable = false)
  private String time;

  // 睡眠
  @Column(name = "sleep", nullable = false)
  private String sleep;

  // 水分
  @Column(name = "water", nullable = false)
  private String water;

  // 排尿1
  @Column(name = "pee1", nullable = false)
  private String pee1;

  // 排尿2
  @Column(name = "pee2", nullable = false)
  private String pee2;

  // 排便
  @Column(name = "poop", nullable = false)
  private String poop;

  // 下剤
  @Column(name = "laxative", nullable = false)
  private String laxative;

  // 服薬
  @Column(name = "medicine", nullable = false)
  private String medicine;

  // 処置
  @Column(name = "ointment", nullable = false)
  private String ointment;

  // 様子
  @Column(name = "situation", nullable = false)
  private String situation;
}
