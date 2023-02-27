package com.change.controller;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.change.service.CoinMachineService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@RestController
public class CoinMachineController {

    @Autowired
    public CoinMachineService service;
    

	@GetMapping("/change/{bill}")
    public ResponseEntity<?> getChange(@PathVariable int bill) {       
    
    	Map<Double, Integer> change;
    	ObjectMapper objectMapper = new ObjectMapper();
    	String formattedJson = null;
   	
    	// Return ACCEPTED but with a message saying the bill is not valid.
        if (!(service.getAcceptedBills().contains(bill))) {
        	return ResponseEntity.status(HttpStatus.ACCEPTED).body("Not a valid bill. Accepted bills are " + service.getAcceptedBills());
        }
    	
        // Return ACCEPTED but with a message saying there is not enough balance to give change.
        if (bill > service.getBalance()) {
        	return ResponseEntity.status(HttpStatus.ACCEPTED).body("Not enough balance to give change. Your current balance is " + service.getBalance());
        }
         

		try {
	    	change = service.calculateChange(bill);
			formattedJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(change);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
    	
        //return ResponseEntity.ok(change);
    	return ResponseEntity.ok(formattedJson);
    }
   
    
    @GetMapping("/change")
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
