package com.change.controller;


import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class CoinMachineController {

    private static Map<Double, Integer> coins = new TreeMap<>();
    private static List<Integer> acceptedBills = Arrays.asList(1, 2, 5, 10, 20, 50, 100);
    private static Double balance;

    
    // Start
    public CoinMachineController() {
    	init();
	}
        
	/* 
	 * Define type of coins and amount available for each
	 * Reset and calculate the balance (multiple coin type by amount)
	 */    
    private static void init() {	
    	coins.put(0.01, 100);
        coins.put(0.05, 100);
        coins.put(0.10, 100);
        coins.put(0.25, 100);
        
        balance = 0.0;
        coins.keySet().stream().forEach(entry -> balance = balance + (coins.get(entry) * entry));
    }
    

    
    @GetMapping("/change/{bill}")
    public ResponseEntity<?> getChange(@PathVariable int bill) {       
    
   	
    	// Check if the bill is valid
        if (!(acceptedBills.contains(bill))) {
        	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Not valid bill");
        }
    	
        // Check if there is enough balance
        if (bill > balance) {
        	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Not enough balance.");
        }
        
        
    	Map<Double, Integer> change = calculateChange(bill);
       
       
        updateBalanceAndAvailableCoins(change);
        return ResponseEntity.ok(change);
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
    
    
    @GetMapping("/change/balance")
    public ResponseEntity<String> getBalance() { 
    	String response = ("Your current balance in dollars is $" + balance) + "\n" + ("Your current amount for each coin is " + coins);
    	return ResponseEntity.ok(response);
    }
    
    
    @GetMapping("/change/reset")
    public ResponseEntity<String> Default() {
    	init();
        return ResponseEntity.ok("Change machine resetted to factory settings....");
    }
    
	
	
}
