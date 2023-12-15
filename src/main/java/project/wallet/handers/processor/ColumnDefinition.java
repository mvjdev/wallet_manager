package project.wallet.handers.processor;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import project.wallet.annotations.Column;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class ColumnDefinition {
  private String javaColumnName;
  private String postgresColumnName;
  private Column columnAnnotation;
  private String value;

  private String javaType;
  private String postgresType;
}
