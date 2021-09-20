package org.galatea.starter.domain;

import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import lombok.Data;

@Data
@Entity
@IdClass(IexPriceID.class)
public class IexHistoricalPrice {
  @Id
  private String symbol;
  @Id
  private String date;
  private BigDecimal close;
  private BigDecimal high;
  private BigDecimal low;
  private BigDecimal open;
  private Integer volume;

  protected IexHistoricalPrice() {
  }

  public IexHistoricalPrice(BigDecimal close, BigDecimal high, BigDecimal low,
      BigDecimal open, String symbol, Integer volume, String date) {
    this.close = close;
    this.high = high;
    this.low = low;
    this.open = open;
    this.symbol = symbol;
    this.volume = volume;
    this.date = date;
  }

}
