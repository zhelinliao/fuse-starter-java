package org.galatea.starter.domain;

import java.io.Serializable;
import lombok.Data;

@Data
public class IexPriceID implements Serializable {
  private String date;
  private String symbol;

  protected IexPriceID(){};

  public IexPriceID(String date, String symbol) {
    this.date = date;
    this.symbol = symbol;
  }
}