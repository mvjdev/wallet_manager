package project.wallet.models;

import lombok.Getter;

@Getter
public class Currency {
  private Long id;
  private String name;
  private String country;

  public Currency setId(Long id) {
    this.id = id;
    return this;
  }

  public Currency setName(String name) {
    this.name = name;
    return this;
  }

  public Currency setCountry(String country) {
    this.country = country;
    return this;
  }
}
