package com.wipro.fundtransfer.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Random;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.wipro.fundtransfer.exception.FixedDepositExistException;
import com.wipro.fundtransfer.exception.InsufficientFundsException;
import com.wipro.fundtransfer.exception.PinDoesNotMatchException;
import com.wipro.fundtransfer.model.Account;
import com.wipro.fundtransfer.model.FixedDeposit;
import com.wipro.fundtransfer.model.Transactions;
import com.wipro.fundtransfer.repository.AccountRepository;
import com.wipro.fundtransfer.repository.DepositRepository;
import com.wipro.fundtransfer.repository.TransactionsRepository;

@Service
public class DepositServiceImpl implements DepositService {
	@Autowired
	DepositRepository depositRepo;
	@Autowired
	AccountRepository accountrepo;
	@Autowired
	TransactionsRepository transactionrepo;
	@Autowired
	private RestTemplate restTemplate;
	@Override
	public FixedDeposit createFD(FixedDeposit FD) throws Exception {
//		try {
			Account ac=restTemplate.getForObject("http://localhost:7072/bankepay/getbyaccno/{accountnumber}", Account.class,FD.getAccountnumber());
			FD.setInterestrate(5);
			System.out.println(ac.getAccountnumber());
			Optional<FixedDeposit> fd=depositRepo.findById(FD.getAccountnumber());
	//		System.out.println(fd);
	//		System.out.println(fd.get().getAccountnumber());
			if(fd.isPresent()) {
				throw new FixedDepositExistException();
			}
			if(!ac.getPin().equals(FD.getPin())) {
				throw new PinDoesNotMatchException();
			}
			if(ac.getBalance()>=FD.getAmount()) {
				ac.setBalance(ac.getBalance()-FD.getAmount());
				accountrepo.save(ac);
			}else {
				throw new InsufficientFundsException();
			}
			Random random = new Random();
			long id = 100000 + random.nextLong(999999999);
			Transactions t= new Transactions(id,String.valueOf(FD.getAccountnumber()),String.valueOf(FD.getAccountnumber()),FD.getAmount(),FD.getPin(), LocalDate.now(),"Fixed Deposit");
			transactionrepo.save(t);
			return depositRepo.save(FD);
//		}catch(Exception e) {
//			throw new Exception();
//		}
	}
	@Override
	public List<FixedDeposit> calculateInterest(String adharnumber) throws FixedDepositExistException {
		List<FixedDeposit> allfd =depositRepo.findAllByadharnumber(adharnumber).orElseThrow(()-> new FixedDepositExistException());
		for(int i=0;i<allfd.size();i++) {
			int year=Math.abs(allfd.get(i).getTransactiondate().getYear()-allfd.get(i).getTimeperiod().getYear());
			float month = Math.abs(allfd.get(i).getTimeperiod().getMonthValue()- allfd.get(i).getTransactiondate().getMonthValue())%12;
			float time=year+month;
			allfd.get(i).setInterest((allfd.get(i).getAmount()*time*allfd.get(i).getInterestrate())/100);
			depositRepo.save(allfd.get(i));
		}
		return allfd;
	}
	@Override
	public Account breakDeposit(long accountnumber) throws Exception {
//		try {
			Account ac=restTemplate.getForObject("http://localhost:7072/bankepay/getbyaccno/{accountnumber}", Account.class,accountnumber);
			Optional<FixedDeposit> FD= Optional.ofNullable(depositRepo.findById(accountnumber)).orElseThrow(()-> new FixedDepositExistException());
			FixedDeposit fd=FD.get();
			int year=Math.abs(fd.getTransactiondate().getYear()-fd.getTimeperiod().getYear());
			float month = Math.abs(fd.getTimeperiod().getMonthValue()- fd.getTransactiondate().getMonthValue())%12;
			float time=year+month;
			double i=(fd.getAmount()*time*fd.getInterestrate())/100;
			ac.setBalance(ac.getBalance()+i+fd.getAmount());
			depositRepo.delete(fd);
			Random random = new Random();
			long id = 100000 + random.nextLong(999999999);
			Transactions t= new Transactions(id,String.valueOf(accountnumber),String.valueOf(accountnumber),fd.getAmount(),fd.getPin(), LocalDate.now(),"Fixed Deposit Credit");
			transactionrepo.save(t);
			return accountrepo.save(ac);
//		}catch(Exception e) {
//			throw new Exception();
//		}
	}
}
