package com.wipro.fundtransfer.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wipro.fundtransfer.exception.FixedDepositExistException;
import com.wipro.fundtransfer.exception.InsufficientFundsException;
import com.wipro.fundtransfer.exception.PinDoesNotMatchException;
import com.wipro.fundtransfer.model.Account;
import com.wipro.fundtransfer.model.FixedDeposit;
import com.wipro.fundtransfer.repository.AccountRepository;
import com.wipro.fundtransfer.repository.DepositRepository;
import com.wipro.fundtransfer.repository.TransactionsRepository;
import com.wipro.fundtransfer.service.DepositService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class DepositControllerTest {
	@Autowired
	MockMvc mockMvc;
	@Autowired
	ObjectMapper objectmapper;
	@MockBean
	private DepositService depositService; 
	@Mock
	private RestTemplate restTemplate;
	@Mock
	private DepositRepository depositrepo;
	@Mock
	private AccountRepository accountrepo;
	@Mock
	private TransactionsRepository transactionrepo;
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
	public void getacc() throws InsufficientFundsException, PinDoesNotMatchException, Exception {
		Mockito.when(depositService.createFD(fixedDeposit)).thenReturn(fixedDeposit);
		this.mockMvc.perform(MockMvcRequestBuilders
				.post("/deposit/createdeposit")
				.content(objectmapper.writeValueAsString(fixedDeposit))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
	@Test
	public void getaccFDException() throws InsufficientFundsException, PinDoesNotMatchException, Exception {
//		Mockito.when(depositService.createFD(fixedDeposit)).thenThrow(FixedDepositExistException.class);
		doThrow(new InsufficientFundsException()).when(depositService).createFD(any());
		this.mockMvc.perform(MockMvcRequestBuilders
				.post("/deposit/createdeposit")
				.content(objectmapper.writeValueAsString(fixedDeposit))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isConflict());
	}
	@Test
	public void getaccPinException() throws InsufficientFundsException, PinDoesNotMatchException, Exception {
//		Mockito.when(depositService.createFD(fixedDeposit)).thenThrow(PinDoesNotMatchException.class);
		doThrow(new PinDoesNotMatchException()).when(depositService).createFD(any());
		this.mockMvc.perform(MockMvcRequestBuilders
				.post("/deposit/createdeposit")
				.content(objectmapper.writeValueAsString(fixedDeposit))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isConflict());
	}
	@Test
	public void getaccFundException() throws InsufficientFundsException, PinDoesNotMatchException, Exception {
//		Mockito.when(depositService.createFD(fixedDeposit)).thenThrow(InsufficientFundsException.class);
		doThrow(new FixedDepositExistException()).when(depositService).createFD(any());
		this.mockMvc.perform(MockMvcRequestBuilders
				.post("/deposit/createdeposit")
				.content(objectmapper.writeValueAsString(fixedDeposit))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isConflict());
	}
	@Test
	public void getinterest() throws JsonProcessingException, Exception {
		Mockito.when(depositService.calculateInterest(any())).thenReturn(listdeposit);
		this.mockMvc.perform(MockMvcRequestBuilders
				.get("/deposit/getFDaccount/{adharnumber}",fixedDeposit.getAdharnumber())
				.content(objectmapper.writeValueAsString(fixedDeposit))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
		
	}
	@Test
	public void getinterestException() throws InsufficientFundsException, PinDoesNotMatchException, Exception {
		doThrow(new FixedDepositExistException()).when(depositService).calculateInterest(any());
		this.mockMvc.perform(MockMvcRequestBuilders
				.get("/deposit/getFDaccount/{adharnumber}",fixedDeposit.getAdharnumber())
				.content(objectmapper.writeValueAsString(fixedDeposit))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isConflict());
	}
	@Test
	public void breakDeposit() throws JsonProcessingException, Exception {
		Mockito.when(depositService.breakDeposit(fixedDeposit.getAccountnumber())).thenReturn(account);
		this.mockMvc.perform(MockMvcRequestBuilders
				.delete("/deposit/breakDeposit/{adharnumber}",fixedDeposit.getAccountnumber())
				.content(objectmapper.writeValueAsString(fixedDeposit))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
	@Test
	public void breakDepositException() throws JsonProcessingException, Exception {
		doThrow(new FixedDepositExistException()).when(depositService).breakDeposit(fixedDeposit.getAccountnumber());
		this.mockMvc.perform(MockMvcRequestBuilders
				.delete("/deposit/breakDeposit/{adharnumber}",fixedDeposit.getAccountnumber())
				.content(objectmapper.writeValueAsString(fixedDeposit))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isConflict());
	}
	
}
