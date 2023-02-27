package com.change;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;
import com.change.service.CoinMachineService;

@SpringBootTest
class ChangeMachineApplicationTests {

	   @Test
	    public void testValidateBill() {
	    
		   	// Initialize test properties
	        int bill = 10;
	        boolean response;
	    
	        // Run test
	        CoinMachineService service = new CoinMachineService();
	        response = service.getAcceptedBills().contains(bill);
	        Assertions.assertEquals(false, response);

	   }	   

}
