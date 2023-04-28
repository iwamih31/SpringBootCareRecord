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
@Table(name = "todo")
public class ToDo {

  // ID
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Integer id;

  // 時間
  @Column(name = "time", nullable = false)
  private String time;

  // 行動
  @Column(name = "action", nullable = false)
  private String action;

  // 内容
  @Column(name = "content", nullable = false)
  private String content;
}
