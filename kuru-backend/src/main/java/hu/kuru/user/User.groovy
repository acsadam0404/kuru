package hu.kuru.user

import groovy.transform.EqualsAndHashCode
import hu.kuru.BaseEntity
import hu.kuru.ServiceLocator;

import javax.persistence.Entity
import javax.persistence.Table
import javax.validation.constraints.NotNull

import org.springframework.beans.factory.annotation.Autowire
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Configurable

@Entity
@Table(name = "user")
@EqualsAndHashCode(includes = ["username"])
class User extends BaseEntity {
	private static UserRepo repo

	User() {
		if (ServiceLocator.loaded && !repo)  {
			repo = ServiceLocator.getBean(UserRepo)
		}
	}

	@NotNull
	String username
	@NotNull
	String password

	static User get(String username) {
		repo.findByUsername(username)
	}

	User save() {
		repo.save(this)
	}

	@Override
	String toString() {
		username
	}
}
