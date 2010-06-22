package com.darekzon.bookstore.dao;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import com.darekzon.bookstore.domain.Account;
import com.darekzon.bookstore.domain.AccountRole;
import com.darekzon.bookstore.exception.UserNotFoundException;

public interface AccountDao{	
	public Account findUsername(String username) throws UserNotFoundException;

	public void save(Account account);

	public List<Account> listAccounts(List<AccountRole> roles);
	
	
}
