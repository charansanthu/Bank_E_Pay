package com.wipro.fundtransfer.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import com.wipro.fundtransfer.exception.AccountNotFoundException;
import com.wipro.fundtransfer.exception.InsufficientFundException;
import com.wipro.fundtransfer.exception.ResourcenotFoundException;
import com.wipro.fundtransfer.model.Account;
import com.wipro.fundtransfer.model.Transactions;
import com.wipro.fundtransfer.repository.AccountRepository;
import com.wipro.fundtransfer.repository.TransactionsRepository;


@Service
public class TransactionServiceImpl implements TransactionService {
	@Autowired
	TransactionsRepository transactionrepo;
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	AccountRepository accountrepo;
	public Transactions transferfund(Transactions transaction) throws ResourcenotFoundException,InsufficientFundException, AccountNotFoundException{
		
		ResponseEntity<Account> fromac1;
		ResponseEntity<Account> toac1;
		try {
			fromac1=restTemplate.getForEntity("http://localhost:7072/bankepay/getbyaccno/{accountnumber}", Account.class,transaction.getFromaccount());
			toac1=restTemplate.getForEntity("http://localhost:7072/bankepay/getbyaccno/{accountnumber}", Account.class,transaction.getToaccount());
		}catch(RestClientResponseException e) {
			System.out.println("ji");
			throw new AccountNotFoundException();
		}
		Account fromac=fromac1.getBody();
		Account toac=toac1.getBody();
			if (fromac.getBalance() >= transaction.getAmount() && fromac.getPin().equals(transaction.getPin())) {
				if (transaction.getAmount() > 0) {
					fromac.setBalance(fromac.getBalance() - transaction.getAmount());
					toac.setBalance(toac.getBalance() + transaction.getAmount());
					accountrepo.save(fromac);
					accountrepo.save(toac);
					transaction.setTransactiontype("debit");
					transactionrepo.save(transaction);
					Transactions transaction1=transaction;
					transaction1.setTransactionid(transaction.getTransactionid()+1);
					transaction1.setFromaccount(transaction.getToaccount());
					transaction1.setToaccount(transaction.getFromaccount());
					transaction1.setTransactiontype("credit");
					transactionrepo.save(transaction);
					return transaction; 
				} else {
					throw new InsufficientFundException();
				}
			} else {
				throw new InsufficientFundException();
			}
	}
	
	public Transactions fundtransferUpi(Transactions transaction) throws AccountNotFoundException, InsufficientFundException{
		Account fromac=new Account();
		Account toac = new Account();
//		try {
			fromac=restTemplate.getForObject("http://localhost:7072/bankepay/getbyUpi/{upi}", Account.class,transaction.getFromaccount());
			toac=restTemplate.getForObject("http://localhost:7072/bankepay/getbyUpi/{upi}", Account.class,transaction.getToaccount());
			System.out.println(fromac.getAccountnumber());
			System.out.println(toac.getAccountnumber());
//		}catch(Exception e) {
//			System.out.println("kk");
//			throw new AccountNotFoundException();
//		}
		System.out.println("after");
		if(fromac.getBalance()>=transaction.getAmount()  && fromac.getPin().equals(transaction.getPin())) {
			fromac.setBalance(fromac.getBalance() - transaction.getAmount());
			toac.setBalance(toac.getBalance() + transaction.getAmount());
			accountrepo.save(fromac);
			accountrepo.save(toac);
			transaction.setTransactiontype("debit");
			transactionrepo.save(transaction);
			Transactions transaction1=transaction;
			transaction1.setTransactionid(transaction.getTransactionid()+1);
			transaction1.setFromaccount(transaction.getToaccount());
			transaction1.setToaccount(transaction.getFromaccount());
			transaction1.setTransactiontype("credit");
			transactionrepo.save(transaction);
			return transaction;
		}else {
			System.out.println("hi");
			throw new InsufficientFundException();
		}
	}
	public Transactions fundtransferUsingMobile(Transactions transaction) throws Exception {
		try {
			Account fromac=restTemplate.getForObject("http://localhost:7072/bankepay/getbyMbl/{mblno}", Account.class,transaction.getFromaccount());
			Account toac=restTemplate.getForObject("http://localhost:7072/bankepay/getbyMbl/{mblno}", Account.class,transaction.getToaccount());
				if(fromac.getBalance()>=transaction.getAmount()&&fromac.getPin().equals(transaction.getPin())) {
					fromac.setBalance(fromac.getBalance() - transaction.getAmount());
					toac.setBalance(toac.getBalance() + transaction.getAmount());
					accountrepo.save(fromac);
					accountrepo.save(toac);
					transaction.setTransactiontype("debit");
					transactionrepo.save(transaction);
					Transactions transaction1=transaction;
					transaction1.setTransactionid(transaction.getTransactionid()+1);
					transaction1.setFromaccount(transaction.getToaccount());
					transaction1.setToaccount(transaction.getFromaccount());
					transaction1.setTransactiontype("credit");
					transactionrepo.save(transaction);
					return transaction;
				}
				else {
					throw new InsufficientFundException();
				}
		}catch(Exception e) {
			throw new AccountNotFoundException();
		}
	}
}
