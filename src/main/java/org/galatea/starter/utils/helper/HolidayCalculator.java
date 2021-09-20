package org.galatea.starter.utils.helper;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HolidayCalculator {

  private final int rangeOverYear = 15;
  private HashSet<LocalDate> holidays = new HashSet<>();

  @Bean
  public void generateHolidays() {
    LocalDate currentDate = LocalDate.now();
    LocalDate initDate = currentDate.minusYears(this.rangeOverYear);
    List<LocalDate> dateList = initDate.datesUntil(currentDate, Period.ofYears(1)).collect(
        Collectors.toList());
    dateList.add(currentDate);
    for(LocalDate date:dateList){
      int year = date.getYear();
      holidays.add(getNewYear(year));
      holidays.add(getMartinLutherKingDay(year));
      holidays.add(getWashingtonBirthday(year));
      holidays.add(getChristmas(year));
      holidays.add(getMemorialDay(year));
      holidays.add(getIndependenceDay(year));
      holidays.add(getThanksGiving(year));
      holidays.add(getLaborDay(year));
    }
  }

  public HashSet<LocalDate> getHolidays() {
    return holidays;
  }

  private LocalDate getNewYear(int year) {
    return LocalDate.of(year, Month.JANUARY, 1);
  }

  private LocalDate getMartinLutherKingDay(int year) {
    int offset = getDateOffSet(year, Month.JANUARY, DayOfWeek.MONDAY);
    return LocalDate.of(year, Month.JANUARY, 1 - offset + 3 * 7);
  }

  private LocalDate getWashingtonBirthday(int year) {
    int offset = getDateOffSet(year, Month.FEBRUARY, DayOfWeek.MONDAY);
    return LocalDate.of(year, Month.FEBRUARY, 1 - offset + 3 * 7);
  }

  /*
  private LocalDate getGoodFriday(int year) {
  };
  */

  private LocalDate getMemorialDay(int year) {
    int offset = getDateOffSet(year, Month.JUNE, DayOfWeek.MONDAY);
    return LocalDate.of(year, Month.JUNE, 1 ).minusDays(offset);
  }

  private LocalDate getIndependenceDay(int year) {
    return LocalDate.of(year, Month.JULY, 4);
  }

  private LocalDate getLaborDay(int year) {
    int offset = getDateOffSet(year, Month.SEPTEMBER, DayOfWeek.MONDAY);
    return LocalDate.of(year, Month.SEPTEMBER, 1 - offset + 7);
  }

  private LocalDate getThanksGiving(int year) {
    int offset = getDateOffSet(year, Month.DECEMBER, DayOfWeek.THURSDAY);
    return LocalDate.of(year, Month.DECEMBER, 1).minusDays(offset);
  }

  private LocalDate getChristmas(int year) {
    return LocalDate.of(year, 12, 25);
  }

  private int getDateOffSet(int year, Month month, DayOfWeek day) {
    if (LocalDate.of(year, month, 1).getDayOfWeek() == day)
      return 7;
    else
      return (LocalDate.of(year,month, 1).getDayOfWeek().ordinal() - day.ordinal() + 7) % 7;
  }
}
