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
@Table(name = "routine")
public class Routine {

  // ID
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Integer id;

  // 時間
  @Column(name = "time", nullable = false)
  private String time;

  // 予定
  @Column(name = "todo", nullable = false)
  private String todo;

  // 部屋番号
  @Column(name = "room", nullable = false)
  private Integer room;

  // 名前
  @Column(name = "name", nullable = false)
  private String name;

  // 行動
  @Column(name = "action", nullable = false)
  private String action;
}
