package com.wipro.fundtransfer.service.copy;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class AdminServiceImpl implements AdminService{
	@Autowired
	private RestTemplate restTemplate;
	@Override
	public ArrayList getallaccounts() {
		ArrayList obj=restTemplate.getForObject("http://localhost:7072/bankepay/getallusers", ArrayList.class);
		return  obj;
	}
	
}
