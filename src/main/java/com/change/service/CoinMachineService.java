package com.change.service;


import java.util.Arrays;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CoinMachineService {

	//@Value("${app.bills}")
	private List<Integer> acceptedBills = Arrays.asList(1, 2, 5, 10, 20, 50, 100);

	//@Value("#{${app.coins}}")
	private TreeMap<Double, Integer> coins = new TreeMap<>();
	
	private Double balance;

	public CoinMachineService() {
		initialize();
	}

	public void initialize() {
		coins.put(0.01, 100);
		coins.put(0.05, 100);
		coins.put(0.10, 100);
		coins.put(0.25, 100);
		
		updateBalance();
	}

	public Map<Double, Integer> calculateChange(int bill) {
		Map<Double, Integer> change = new TreeMap<>();
		double remaining = bill;


		for (Double coin : coins.descendingMap().keySet()) {
			int count = 0;
			while (remaining >= coin && coins.get(coin) > count) {
				remaining -= coin;
				remaining = Math.round(remaining * 100.0) / 100.0;
				count++;
			}
			
			// Only add to the response the coins returning as change
			if (count > 0 ) change.put(coin, count);
				
		}

		// Updating the map with the new balance of coins after the last change.
		change.forEach((k,v) -> coins.computeIfPresent(k, (key, value) -> value - v));
				
		updateBalance();

		return change;
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
	
	public void updateBalance() {
		balance = 0.0;
		coins.keySet().stream().forEach(entry -> balance = balance + (coins.get(entry) * entry));
		
	}

}
