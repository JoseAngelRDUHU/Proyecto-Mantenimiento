/*
 * UserAccountService.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Actor;

@Service
@Transactional
public class UserAccountService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private UserAccountRepository	userAccountRepository;


	// Supporting services ----------------------------------------------------

	// Constructors -----------------------------------------------------------

	public UserAccountService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public UserAccount findByActor(final Actor actor) {
		Assert.notNull(actor);

		UserAccount result;

		result = this.userAccountRepository.findByActorId(actor.getId());

		return result;
	}

	// Other business methods -------------------------------------------------

	public UserAccount save(final UserAccount user) {
		Assert.notNull(user);

		UserAccount userAccount;
        final UserAccount existingUser = this.userAccountRepository.findByUsername(user.getUsername());
        if (user.getId() == 0) {
            Assert.isNull(existingUser, "Ya existe un usuario con el mismo nombre de usuario");
		} else {
            Assert.isTrue(existingUser == null || existingUser.getId() == user.getId(),
					"Ya existe un usuario con el mismo nombre de usuario");
		}

		userAccount = this.userAccountRepository.save(user);

		return userAccount;
	}

	public void flush() {
		this.userAccountRepository.flush();
	}
}
