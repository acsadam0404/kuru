package hu.kuru.user


import hu.kuru.BaseEntity
import hu.kuru.ServiceLocator;

import javax.persistence.Column
import javax.persistence.ElementCollection
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.Table
import javax.validation.constraints.NotNull

@Entity
@Table(name = "user")
class User extends BaseEntity{
	static final String USERNAME = "username"
	static final String PASSWORD = "password"
	static final String ROLES = "roles"

	private static UserRepo repo

	static void setRepo(UserRepo repo) {
		this.repo = repo
	}
	
	User() {
		if (ServiceLocator.loaded && !repo)  {
			repo = ServiceLocator.getBean(UserRepo)
		}
	}
	@Column(unique =  true)
	@NotNull
	String username

	@NotNull
	String password

	@ElementCollection(fetch = FetchType.EAGER)
	Set<String> roles = []

	static List<User> findAll() {
		repo.findAll()
	}

	User save() {
		repo.save(this)
	}

	static boolean valid(String username, String password) {
		def user = repo.findByUsernameAndPassword(username, password)
		user ? true : false
	}
	void delete() {
		repo.delete(id)
	}

	static Set<String> findAllRoles() {
		return ["USER", "ADMIN"] as Set
	}

	boolean hasRole(String role) {
		roles.contains(role)
	}


	static User findByUsername(String username) {
		repo.findByUsername(username)
	}
	
	/**
	 * használjuk a findByUsername-t mert az konvencionálisabb
	 * @param username
	 * @return
	 */
	@Deprecated
	static User get(String username) {
		return repo.findByUsername(username)
	}

	@Override
	String toString() {
		return username
	}
}