package com.darekzon.bookstore.service;

import java.util.Collection;
import java.util.List;

import com.darekzon.bookstore.domain.Account;
import com.darekzon.bookstore.domain.AccountRole;
import com.darekzon.bookstore.exception.UserExistsException;

public interface AccountService extends org.springframework.security.core.userdetails.UserDetailsService{
	
	public void registerAccount(Account account, List<AccountRole> ar) throws UserExistsException;

	public Collection<Account> listAccounts(Collection<AccountRole> roles);
	
}
