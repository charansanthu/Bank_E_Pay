package com.wipro.fundtransfer.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wipro.fundtransfer.model.FixedDeposit;

@Repository
public interface DepositRepository extends JpaRepository<FixedDeposit,Long> {

	public Optional<FixedDeposit> findByadharnumber(String adharnumber);
	public Optional<List<FixedDeposit>> findAllByadharnumber(String adharnumber);
}
