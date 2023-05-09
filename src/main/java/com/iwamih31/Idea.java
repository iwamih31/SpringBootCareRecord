package com.iwamih31;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "idea")
public class Idea {

  // ID
  @Id
  private Integer id;

  // 行事計画
  @Column(name = "contents", nullable = false)
  @Length(max = 2000)
  private String contents;
}
