package com.transfer.repository;

import com.transfer.domain.Account;
import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<Account,Long> {

    Account findByAccountNumber(Integer accountNumber);

}
