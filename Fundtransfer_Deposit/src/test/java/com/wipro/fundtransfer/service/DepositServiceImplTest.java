package com.wipro.fundtransfer.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import com.wipro.fundtransfer.exception.FixedDepositExistException;
import com.wipro.fundtransfer.exception.InsufficientFundsException;
import com.wipro.fundtransfer.exception.PinDoesNotMatchException;
import com.wipro.fundtransfer.model.Account;
import com.wipro.fundtransfer.model.FixedDeposit;
import com.wipro.fundtransfer.repository.AccountRepository;
import com.wipro.fundtransfer.repository.DepositRepository;
import com.wipro.fundtransfer.repository.TransactionsRepository;

class DepositServiceImplTest {
	@Mock
	private DepositRepository depositrepo;
	@Mock
	private AccountRepository accountrepo;
	@Mock
	private TransactionsRepository transactionrepo;
	@Mock
	private RestTemplate restTemplate;
	@InjectMocks
	private DepositServiceImpl service;
	private FixedDeposit fixedDeposit;
	private List<FixedDeposit> listdeposit;
	private Account account;
//	private Transactions transactions; 
	Optional<FixedDeposit> optionalDeposit;
	Optional<Account> optionalaccount;
	Optional<List<FixedDeposit>> optionallist;
	@BeforeEach
	public void setup() throws Exception{
		MockitoAnnotations.openMocks(this);
		fixedDeposit = new FixedDeposit();
		fixedDeposit.setAccountnumber(915259444);
		fixedDeposit.setAdharnumber("415461663039");
		fixedDeposit.setAmount(100);
		fixedDeposit.setPin("12345");
		fixedDeposit.setTimeperiod(LocalDate.now());
		fixedDeposit.setTransactiondate(LocalDate.now());
		optionalDeposit=Optional.of(fixedDeposit);
		account = new Account();
		account.setAccountnumber(915259444);
		account.setAccounttype("Fixed Deposit");
		account.setBalance(1000);
		account.setBankname("hdfc");
		account.setMobilenumber(6303417971l);
		account.setPin("12345");
		account.setUpiId("6303417971@hdfc");
		optionalaccount = Optional.of(account);
		listdeposit= new ArrayList<>();
		listdeposit.add(fixedDeposit);
		optionallist=Optional.of(listdeposit);
	}
	@AfterEach
    public void tearDown() throws Exception {
	 depositrepo.deleteAll();
	 accountrepo.deleteAll();
	 transactionrepo.deleteAll();
    }
	@Test
	public void createFD() throws Exception {
		Mockito.when(restTemplate.getForObject("http://localhost:7072/fundtransfer/getbyaccno/{accountnumber}", Account.class,fixedDeposit.getAccountnumber())).thenReturn(account);
		Mockito.when(depositrepo.findById(fixedDeposit.getAccountnumber())).thenReturn(Optional.empty());
		Mockito.when(depositrepo.save(fixedDeposit)).thenReturn(fixedDeposit);
		assertEquals("415461663039", service.createFD(fixedDeposit).getAdharnumber());
	}
	@Test
	public void createFDExistException() {
		Mockito.when(restTemplate.getForObject("http://localhost:7072/fundtransfer/getbyaccno/{accountnumber}", Account.class,fixedDeposit.getAccountnumber())).thenReturn(account);
		Mockito.when(depositrepo.findById(fixedDeposit.getAccountnumber())).thenReturn(optionalDeposit);
		Mockito.when(depositrepo.save(fixedDeposit)).thenReturn(fixedDeposit);
		assertThrows(FixedDepositExistException.class, ()->service.createFD(fixedDeposit));
	}
	@Test
	public void createFDPinException() {
		account.setPin("1");
		Mockito.when(restTemplate.getForObject("http://localhost:7072/fundtransfer/getbyaccno/{accountnumber}", Account.class,fixedDeposit.getAccountnumber())).thenReturn(account);
		Mockito.when(depositrepo.findById(fixedDeposit.getAccountnumber())).thenReturn(Optional.empty());
		Mockito.when(depositrepo.save(fixedDeposit)).thenReturn(fixedDeposit);
		assertThrows(PinDoesNotMatchException.class, ()->service.createFD(fixedDeposit));
	}
	@Test
	public void createFDFundException() throws Exception {
		fixedDeposit.setAmount(200000);
		Mockito.when(restTemplate.getForObject("http://localhost:7072/fundtransfer/getbyaccno/{accountnumber}", Account.class,fixedDeposit.getAccountnumber())).thenReturn(account);
		Mockito.when(depositrepo.findById(fixedDeposit.getAccountnumber())).thenReturn(Optional.empty());
		Mockito.when(depositrepo.save(fixedDeposit)).thenReturn(fixedDeposit);
		assertThrows(InsufficientFundsException.class, ()->service.createFD(fixedDeposit));
	}
	@Test
	public void calculateInterest() throws FixedDepositExistException {
		Mockito.when(depositrepo.findAllByadharnumber(fixedDeposit.getAdharnumber())).thenReturn(optionallist);
		Mockito.when(depositrepo.save(fixedDeposit)).thenReturn(null);
		assertEquals("415461663039", service.calculateInterest(fixedDeposit.getAdharnumber()).get(0).getAdharnumber());
	}
	@Test
	public void calculateInterestException() {
		Mockito.when(depositrepo.save(fixedDeposit)).thenReturn(null);
		assertThrows(FixedDepositExistException.class, ()->service.calculateInterest(fixedDeposit.getAdharnumber()));
	}
	@Test
	public void breakDeposit() throws Exception {
		Mockito.when(restTemplate.getForObject("http://localhost:7072/fundtransfer/getbyaccno/{accountnumber}", Account.class,fixedDeposit.getAccountnumber())).thenReturn(account);
		Mockito.when(depositrepo.findById(fixedDeposit.getAccountnumber())).thenReturn(optionalDeposit);
		Mockito.when(accountrepo.save(account)).thenReturn(account);
		assertEquals(915259444, service.breakDeposit(fixedDeposit.getAccountnumber()).getAccountnumber());
	}
	@Test
	public void breakDepositException() {
		Mockito.when(restTemplate.getForObject("http://localhost:7072/fundtransfer/getbyaccno/{accountnumber}", Account.class,fixedDeposit.getAccountnumber())).thenReturn(account);
		assertThrows(NoSuchElementException.class, ()->service.breakDeposit(fixedDeposit.getAccountnumber()));

	}
}
