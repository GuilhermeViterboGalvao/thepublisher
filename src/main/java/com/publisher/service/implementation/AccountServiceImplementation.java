package com.publisher.service.implementation;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.List;
import javax.persistence.Query;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.publisher.entity.Account;
import com.publisher.service.AccountService;

public class AccountServiceImplementation extends AbstractServiceImplementation<Account> implements AccountService {

	private static Log log = LogFactory.getLog(AccountServiceImplementation.class);
	
	@Override
	@SuppressWarnings("unchecked")
	public Account authenticate(String email, String password) {
        Account account = null;
        Query query = entityManager.createQuery("from Account where email=:email").setParameter("email", email);
        List<Account> result = query.getResultList();
        if (result != null && !result.isEmpty()) {
            account = result.iterator().next();
            if (!account.getHash().equals(hash(password))) {
                account = null;
            }
        }
        return account;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Account getByEmail(String email) {
        Query query = entityManager.createQuery("from Account where email = :email").setParameter("email", email);
		List<Account> result = query.getResultList();
        if (result == null || result.isEmpty()) {
        	return null;
        }
        return result.get(0);
	}

	@Override
	public String hash(String password) {
        String newPassword = "ThePublisher" + password;
        String hash = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(newPassword.getBytes(), 0, newPassword.length());
            hash = new BigInteger(1, md5.digest()).toString(16);
        } catch (Exception e) {
        	log.error(e);
            e.printStackTrace();
        }
        return hash;
	}
}