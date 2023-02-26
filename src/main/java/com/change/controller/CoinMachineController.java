package com.change.controller;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.change.service.CoinMachineService;


@RestController
public class CoinMachineController {

    @Autowired
    public CoinMachineService service;
    
    
    @GetMapping("/change/{bill}")
    public ResponseEntity<?> getChange(@PathVariable int bill) {       
    
    	Map<Double, Integer> change;
   	
    	// Check if the bill is valid
        if (!(service.getAcceptedBills().contains(bill))) {
        	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Not valid bill");
        }
    	
        // Check if there is enough balance
        if (bill > service.getBalance()) {
        	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Not enough balance.");
        }
         
    	change = service.calculateChange(bill);
             
        return ResponseEntity.ok(change);
    }
   
    
    @GetMapping("/change/balance")
    public ResponseEntity<String> getBalance() { 
    	String response = ("Your current balance in dollars is $" + service.getBalance()) + "\n" + ("Your current amount for each coin is " + service.getCoins());
    	return ResponseEntity.ok(response);
    }
    
    
    @GetMapping("/change/reset")
    public ResponseEntity<String> Default() {
    	service.initialize();
        return ResponseEntity.ok("Change machine resetted to factory settings....");
    }
    
	
	
}
