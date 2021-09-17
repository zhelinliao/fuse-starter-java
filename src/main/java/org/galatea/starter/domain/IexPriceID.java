package org.galatea.starter.domain;

import java.io.Serializable;
import lombok.Data;

@Data
public class IexPriceID implements Serializable {
  private String date;
  private String symbol;

  protected IexPriceID(){};

  public IexPriceID(String symbol, String date) {
    this.date = date;
    this.symbol = symbol;
  }
}