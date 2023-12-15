package project.wallet.models;

import lombok.*;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@ToString
public class SumGroupedTypes {
  private String category;
  private Double total;
}
