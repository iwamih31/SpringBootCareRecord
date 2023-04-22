package com.iwamih31;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "detail")
public class Detail {

  // ID
  @Id
  private Integer id;

  // 生年月日
  @Column(name = "birthday", nullable = false)
  private String birthday;

  // 介護度
  @Column(name = "level", nullable = false)
  private String level;

  // 入居日
  @Column(name = "move_in", nullable = false)
  private String move_in;
}
