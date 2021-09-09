package org.galatea.starter.domain;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class IexHistoricalPrice {
  @Id
  @GeneratedValue(strategy= GenerationType.AUTO)
  private long id;
  private BigDecimal close;
  private BigDecimal high;
  private BigDecimal low;
  private BigDecimal open;
  private String symbol;
  private Integer volume;
  private Date date;

  protected IexHistoricalPrice() {
  }

  public IexHistoricalPrice(BigDecimal close, BigDecimal high, BigDecimal low,
      BigDecimal open, String symbol, Integer volume, Date date) {
    this.close = close;
    this.high = high;
    this.low = low;
    this.open = open;
    this.symbol = symbol;
    this.volume = volume;
    this.date = date;
  }

  public long getId() {
    return id;
  }

  public BigDecimal getClose() {
    return close;
  }

  public BigDecimal getHigh() {
    return high;
  }

  public BigDecimal getLow() {
    return low;
  }

  public BigDecimal getOpen() {
    return open;
  }

  public String getSymbol() {
    return symbol;
  }

  public Integer getVolume() {
    return volume;
  }

  public Date getDate() {
    return date;
  }

  @Override
  public String toString() {
    return "IexHistoricalPrice{" +
        "id=" + id +
        ", close=" + close +
        ", high=" + high +
        ", low=" + low +
        ", open=" + open +
        ", symbol='" + symbol + '\'' +
        ", volume=" + volume +
        ", date=" + date +
        '}';
  }
}
