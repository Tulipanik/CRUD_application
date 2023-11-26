package com.example.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table
@Setter
@Getter
@SuperBuilder
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Note extends Auditable {

  @Id
  @GeneratedValue(generator = "custom-id", strategy = GenerationType.SEQUENCE)
  @GenericGenerator(name = "custom-id", strategy = "com.example.backend.model.IdGenerator")
  private String id;
  private String title;
  private String content;
  private String userId;
}
