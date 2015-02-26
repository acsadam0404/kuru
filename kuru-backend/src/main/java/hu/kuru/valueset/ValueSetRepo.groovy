package hu.kuru.valueset

import org.springframework.data.jpa.repository.JpaRepository

interface ValueSetRepo extends JpaRepository<ValueSet, Long> {

	ValueSet findByName(String name);
}
