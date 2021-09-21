package org.galatea.starter.service;

import java.util.List;
import org.galatea.starter.domain.IexHistoricalPrice;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * A Feign Declarative REST Client to access endpoints from the Free and Open IEX API to get market
 * data. See https://iextrading.com/developer/docs/
 */
@FeignClient(name = "IEXtoken", url = "${spring.rest.iexBasePathTokenNeeded}")
public interface IexClientExtension {
  /**
   * Get the historical price for the stock symbol and the time range passed in. See https://iexcloud.io/docs/api/#historical-prices.
   *
   * @param symbol stock symbols to get last traded price for.
   * @param range time range for the price query.
   * @param token the token that needed to pass to the IEX API.
   * @return a list of the historical price for the symbols passed in during the chosen time range.
   */
  @GetMapping("/stock/{symbol}/chart/{range}")
  List<IexHistoricalPrice> getHistoricalPricesForSymbol(
      @PathVariable(value = "symbol") String symbol,
      @PathVariable(value = "range", required = false) String range,
      @RequestParam(value = "token") String token);

  @GetMapping("/stock/{symbol}/chart")
  List<IexHistoricalPrice> getHistoricalPricesForSymbolDefault(
      @PathVariable(value = "symbol") String symbol,
      @RequestParam(value = "token") String token);

  @GetMapping("/stock/{symbol}/chart/{range}/{date}")
  List<IexHistoricalPrice> getHistoricalPricesForSymbolByDate(
      @PathVariable(value = "symbol") String symbol,
      @PathVariable(value = "range", required = false) String range,
      @PathVariable(value = "date", required = false) String date,
      @RequestParam(value = "token") String token);
}

