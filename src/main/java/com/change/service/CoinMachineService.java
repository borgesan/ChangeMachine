package com.change.service;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.stereotype.Service;


@Service
public class CoinMachineService {
	
	
	//@Value("${app.acceptedBills}")
	//private List<Integer> acceptedBills;
	private List<Integer> acceptedBills = Arrays.asList(1, 2, 5, 10, 20, 50, 100);
	
	private Map<Double, Integer> coins = new TreeMap<>();
    
	private Double balance;
	    
    
    // Start
    public CoinMachineService() {
    	initialize();
	}
    
	
	/* 
	 * Define type of coins and amount available for each
	 * Reset and calculate the balance (multiple coin type by amount)
	 */    
    public void initialize() {	
    	coins.put(0.01, 100);
        coins.put(0.05, 100);
        coins.put(0.10, 100);
        coins.put(0.25, 100);
        
        balance = 0.0;
        coins.keySet().stream().forEach(entry -> balance = balance + (coins.get(entry) * entry));
    }
	    
	
	
	 public Map<Double, Integer> calculateChange(int bill) {
	        Map<Double, Integer> change = new TreeMap<>();
	        double remaining = bill;

	        TreeMap<Double, Integer> invertedCoins = new TreeMap<>(Comparator.reverseOrder());
	        invertedCoins.putAll(coins);
	        
	        for (Double coin : invertedCoins.keySet()) {
	            int count = 0;
	            while (remaining >= coin && invertedCoins.get(coin) > count) {
	                remaining -= coin;
	                remaining = Math.round(remaining * 100.0) / 100.0;
	                count++;
	            }
	            change.put(coin, count);
	        }

	        updateBalanceAndAvailableCoins(change);
	        
	        return change;
	    }
	 
	 
	 
	 private void updateBalanceAndAvailableCoins(Map<Double, Integer> change) {
	    	
	    	// Update the number of coins available after the last change
	    	for (Double coin : change.keySet()) {
	            coins.put(coin, coins.get(coin) - change.get(coin));
	        }
	    	
	    	// Update the balance
	    	balance = 0.0;
	    	coins.keySet().stream().forEach(entry -> balance = balance + (coins.get(entry) * entry));
	    
	    }
	 
	
	    public List<Integer> getAcceptedBills() {
	    	return acceptedBills;
	    }
	    
	    public Map<Double, Integer> getCoins() {
			return coins;
		}


		public Double getBalance() {
			return balance;
		}

	 
	
	

}
