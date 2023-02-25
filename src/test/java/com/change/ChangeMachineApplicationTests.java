package com.change;

import java.util.Collections;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.change.controller.CoinMachineController;

@SpringBootTest
class ChangeMachineApplicationTests {

	   @Test
	    public void testGetChange() {
	        // Initialize test data
	        int bill = 3;
	        CoinMachineController cm = new CoinMachineController();
	        
	        // Call the method being tested
	        ResponseEntity<?> response = cm.getChange(bill);
	        
	        // Verify the response
	        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	        Assertions.assertEquals("Not valid bill", response.getBody());
	   }	   


}
