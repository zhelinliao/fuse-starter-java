package org.galatea.starter.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.galatea.starter.domain.IexLastTradedPrice;
import org.galatea.starter.domain.IexSymbol;
import org.galatea.starter.domain.IexHistoricalPrice;
import org.galatea.starter.domain.rpsy.IHistoricalPriceRpsy;
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
  @NonNull
  private IHistoricalPriceRpsy historicalPriceRpsy;


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
  public List<IexHistoricalPrice> getHistoricalPricesForSymbol(final String symbol, final String range, final String date, final String token) {
    if(symbol.equals("") || token.equals(""))
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
  /**
   *
   */
  public List<IexHistoricalPrice> getHistoricalPricesForSymbol(final String symbol, final String range, final String token) {

    List<IexHistoricalPrice> historicalPrice = new ArrayList<>();
    LocalDate today = LocalDate.now();
    //default range = 1m
    if(range.equals("")) checkStartingdDate(today, "1m");
    else
      checkStartingdDate(today, range)
    //get it from cache first
    historicalPrice = getHistoricalPricesForSymbolsCache();
    if (!historicalPrice.isEmpty()) {
      log.info("");
    } else {   // if the data is not complete, then get it from IEX
      historicalPrice = getHistoricalPricesForSymbolsIEX();
      log.info("");
    }
    return historicalPrice;
  }

  public List<IexHistoricalPrice> getHistoricalPricesForSymbolsCache(final String symbol, final String range, final String token) {
    List<IexHistoricalPrice> historicalPrice = new ArrayList<>();
    historicalPriceRpsy.findBySymbol();
    //check the date
    return historicalPrice;
  }
  public List<IexHistoricalPrice> getHistoricalPricesForSymbolsIEX(final String symbol, final String range, final String token) {
    List<IexHistoricalPrice> historicalPrice = new ArrayList<>();
    historicalPrice = iexClientExtension.getHistoricalPricesForSymbols(symbol, range, token);
    // save to rpsy
    return historicalPrice;
  }
  public checkStartingdDate(String today, String range)
}
