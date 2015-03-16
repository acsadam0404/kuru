package hu.kuru.user

import org.springframework.data.repository.CrudRepository

interface UserRepo extends CrudRepository<User, Long>{
	User findByUsername(String username);
	
	User findByUsernameAndPassword(String username, String password);
}