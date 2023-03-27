package com.wipro.fundtransfer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients("com.wipro.fundtransfer")
public class FundtransferAccountOperationsApplication {

	public static void main(String[] args) {
		SpringApplication.run(FundtransferAccountOperationsApplication.class, args);
	}

}
