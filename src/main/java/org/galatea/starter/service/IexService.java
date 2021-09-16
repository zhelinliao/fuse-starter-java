package org.galatea.starter.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.galatea.starter.domain.IexLastTradedPrice;
import org.galatea.starter.domain.IexPriceID;
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

  private static final String RANGE_YEAR_TO_DATE = "ytd";
  private static final String RANGE_MAX = "max";
  private static final String RANGE_DAY = "d";
  private static final String RANGE_MONTH = "m";
  private static final String RANGE_YEAR = "y";
  private static final String RANGE_DEFAULT = "1m";
  private static final int MAX_YEAR = 15;

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
    if (symbol.equals("") || token.equals(""))
      return Collections.emptyList();

    if (range != null && range.equals("date"))
      return getHistoricalPricesForSymbolByDate(symbol, range, date, token);
    else
      return getHistoricalPricesForSymbolByRange(symbol, range, token);
  }


  private List<IexHistoricalPrice> getHistoricalPricesForSymbolByDate(final String symbol, final String range, final String date, final String token){
    if (date == null || date.equals(""))
      return Collections.emptyList();
    else
      return iexClientExtension.getHistoricalPricesForSymbolByDate(symbol, range, date, token);
  }

  private List<IexHistoricalPrice> getHistoricalPricesForSymbolByRange(final String symbol, final String range, final String token) {
    List<IexHistoricalPrice> historicalPrice = new ArrayList<>();
    LocalDate currentDate = LocalDate.now();
    LocalDate startingDate = null;
    List<LocalDate> dates = null;
    List<IexHistoricalPrice> prices;

    if (range == null || range.equals(""))
      startingDate = checkStartingDate(currentDate, RANGE_DEFAULT);
    else
      startingDate = checkStartingDate(currentDate, range);

    if (startingDate == null)
      throw new IllegalArgumentException("Invalid range over date: Null pointer for date.");
    log.info("Today = " + currentDate.toString() + " startingDate = " + startingDate.toString());
    dates = startingDate.datesUntil(currentDate, Period.ofDays(1)).collect(Collectors.toList());
    prices = getHistoricalPricesForSymbolCache(symbol, dates);
    if (!prices.isEmpty())
      return prices;
    else
     // return getHistoricalPricesForSymbolIEX();
      return null;
/*
    if (range == null || range.equals(""))
      return iexClientExtension.getHistoricalPricesForSymbolDefault(symbol, token);
    else
      return iexClientExtension.getHistoricalPricesForSymbol(symbol, range, token);
*/
  }

  private LocalDate checkStartingDate(LocalDate today, String range){
    if (range.equals(RANGE_YEAR_TO_DATE))
      return LocalDate.of(today.getYear(), 1, 1);
    else if (range.equals(RANGE_MAX))
      return today.minusYears(MAX_YEAR);

    String[] range_part = range.split("(?<=\\d)(?=\\D)");
    int time_num = 0;
    String time_span = null;
    try {
      time_num= Integer.parseInt(range_part[0]);
      time_span = range_part[1];
    }
    catch (Exception e) {
      log.info(e.toString());
    }

    LocalDate startingDate;
    switch (time_span) {
      case RANGE_DAY:
        startingDate = today.minusDays(time_num);
        break;
      case RANGE_MONTH:
        startingDate = today.minusMonths(time_num);
        break;
      case RANGE_YEAR:
        startingDate = today.minusYears(time_num);
        break;
      default:
        startingDate = null;
        break;
    }
    return startingDate;
  }

  public List<IexHistoricalPrice> getHistoricalPricesForSymbolCache(final String symbol, final List<LocalDate> dates) {
    List<IexHistoricalPrice> historicalPrice = new ArrayList<>();
    //log.info(historicalPriceRpsy.findAll().toString());
    for (LocalDate date:dates) {
      if (date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY)
        continue;
      IexPriceID iexPriceID = new IexPriceID(symbol, date.toString());
      log.info("check id: "+ iexPriceID.toString());
      Optional<IexHistoricalPrice> price = historicalPriceRpsy.findById(iexPriceID);
      if (price.isPresent())
        historicalPrice.add(price.get());
      else
        return Collections.emptyList();
    }
    return historicalPrice;
  }
  /*
  public List<IexHistoricalPrice> getHistoricalPricesForSymbolIEX(final String symbol, final String range, final String token) {
    List<IexHistoricalPrice> historicalPrice = new ArrayList<>();
    historicalPrice = iexClientExtension.getHistoricalPricesForSymbols(symbol, range, token);
    // save to rpsy
    return historicalPrice;
  }
*/
}
