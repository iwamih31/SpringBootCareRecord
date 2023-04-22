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
@Table(name = "carerecord")
public class CareRecord {

  // ID
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Integer id;

  // 部屋番号
  @Column(name = "room", nullable = false)
  private Integer room;

  // 名前
  @Column(name = "name", nullable = false)
  private String name;

  // 利用状況
  @Column(name = "use", nullable = false)
  private String use;
}
