package org.galatea.starter.service;

import java.util.Collections;
import java.util.List;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.galatea.starter.domain.IexLastTradedPrice;
import org.galatea.starter.domain.IexSymbol;
import org.galatea.starter.domain.IexHistoricalPrice;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

/**
 * A layer for transformation, aggregation, and business required when retrieving data from IEX.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class IexService {

  @NonNull
  private IexClient iexClient;
  @NonNull
  private IexClientExtension iexClientExtension;


  /**
   * Get all stock symbols from IEX.
   *
   * @return a list of all Stock Symbols from IEX.
   */
  public List<IexSymbol> getAllSymbols() {
    return iexClient.getAllSymbols();
  }

  /**
   * Get the last traded price for each Symbol that is passed in.
   *
   * @param symbols the list of symbols to get a last traded price for.
   * @return a list of last traded price objects for each Symbol that is passed in.
   */
  public List<IexLastTradedPrice> getLastTradedPriceForSymbols(final List<String> symbols) {
    if (CollectionUtils.isEmpty(symbols)) {
      return Collections.emptyList();
    } else {
      return iexClient.getLastTradedPriceForSymbols(symbols.toArray(new String[0]));
    }
  }

  /**
   *
   */
  public List<IexHistoricalPrice> getHistoricalPricesForSymbol(final String symbol, final String range, final String date, final String token) {
    if(symbol.equals(""))
      return Collections.emptyList();
    else if(range == null || range.equals(""))
      return iexClientExtension.getHistoricalPricesForSymbolDefault(symbol, token);
    else if(!range.equals("date"))
      return iexClientExtension.getHistoricalPricesForSymbol(symbol, range, token);
    else
      if(date == null || date.equals(""))
        return Collections.emptyList();
      else
        return iexClientExtension.getHistoricalPricesForSymbolByDate(symbol, range, date, token);
  }

}
