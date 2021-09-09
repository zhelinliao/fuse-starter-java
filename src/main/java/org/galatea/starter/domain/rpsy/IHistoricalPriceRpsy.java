package org.galatea.starter.domain.rpsy;

import java.util.List;
import org.galatea.starter.domain.IexHistoricalPrice;
import org.springframework.data.repository.CrudRepository;

public interface IHistoricalPriceRpsy extends CrudRepository<IexHistoricalPrice, Long>{
  /**
   * Retrieves all entities with the given symbol.
   */
  List<IexHistoricalPrice> findBySymbol(String symbol);

}
