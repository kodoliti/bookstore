package com.darekzon.bookstore.service;

import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.darekzon.bookstore.dao.AccountDao;
import com.darekzon.bookstore.domain.Account;
import com.darekzon.bookstore.domain.AccountRole;
import com.darekzon.bookstore.exception.UserExistsException;
import com.darekzon.bookstore.exception.UserNotFoundException;

@Transactional
@Repository
public class AccountServiceImpl implements AccountService {
	@Autowired
	AccountDao accountDao;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	SaltSource saltSource;

	@PersistenceContext
	EntityManager entityManager;

	@Override
	public void registerAccount(Account account, List<AccountRole> ar)
			throws UserExistsException {
		try {
			accountDao.findUsername(account.getUsername());
			throw new UserExistsException();
		} catch (UserNotFoundException une) {
			entityManager.persist(account);
			account.setPassword(passwordEncoder.encodePassword(account
					.getPassword(), saltSource));
			for (AccountRole arole : ar) {
				account.addAccountRole(arole);
			}
//			entityManager.flush();
		}
	}

	@Override
	@Transactional(readOnly = true)
	public Collection<Account> listAccounts(Collection<AccountRole> roles) {
		return accountDao.listAccounts(roles);
	}

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException, DataAccessException {
		try {
			return (UserDetails) accountDao.findUsername(username);
		} catch (UserNotFoundException e) {
			throw new UsernameNotFoundException(username);
		}
	}

}
