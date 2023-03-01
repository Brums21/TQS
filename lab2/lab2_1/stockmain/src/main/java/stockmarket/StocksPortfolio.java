package stockmarket;

import java.util.ArrayList;
import java.util.List;

public class StocksPortfolio {
    
    private List<Stock> stocks;
    private IStockmarketService stockmarket;

    public StocksPortfolio(IStockmarketService service){
        this.stockmarket = service;
        this.stocks = new ArrayList<>(); // inicializacao do array 
    }

    public void addStock(Stock stock){
        this.stocks.add(stock);
    }

    public double getTotalValue(){
        Double sum = 0.0;
        for (Stock stock : stocks){
            sum += stock.getQuantity() * stockmarket.lookUpPrice(stock.getLabel());
        }
        return sum;
    }
}
