package stockmarket;



import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;



/**
 * Unit test for simple App.
 */
@ExtendWith(MockitoExtension.class)
public class StocksPortfolioTest 
{
    // step 1 - igual a ter dentro do teste -> IStock... marketService = mock(Istock....class)
    @Mock
    IStockmarketService marketService;

    // step 2
    @InjectMocks
    StocksPortfolio stocksPortfolio;

    @Test
    void testGetTotalValue()
    {
        // step 3 -> the actual test (getTotalValue)
        when(marketService.lookUpPrice(any(String.class))).thenReturn(5.0);
        
        stocksPortfolio.addStock(new Stock("Um produto", 5));
        stocksPortfolio.addStock(new Stock("Outro produto", 3));

        assertEquals(stocksPortfolio.getTotalValue(), 25.0+15.0);

        verify(marketService, times(1)).lookUpPrice("Um produto");
        verify(marketService, times(1)).lookUpPrice("Outro produto");
    }

    @Test
    void testGetTotalValueb(){
        when(marketService.lookUpPrice(any(String.class))).thenReturn(5.0);
        
        stocksPortfolio.addStock(new Stock("Um produto", 5));
        stocksPortfolio.addStock(new Stock("Outro produto", 3));

        assertThat(stocksPortfolio.getTotalValue(), equalTo(40.0));

        verify(marketService, times(1)).lookUpPrice("Um produto");
        verify(marketService, times(1)).lookUpPrice("Outro produto");
    }
}
