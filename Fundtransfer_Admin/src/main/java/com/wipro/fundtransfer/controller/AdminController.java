package com.wipro.fundtransfer.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wipro.fundtransfer.service.copy.AdminService;

@RestController
@CrossOrigin
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	private AdminService adminService;
	@GetMapping("/getallAccounts")
	public ArrayList getallaccounts() {
		ArrayList obj =adminService.getallaccounts();
		return obj;
	}
	
}
