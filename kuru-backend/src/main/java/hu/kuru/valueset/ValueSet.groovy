package hu.kuru.valueset

import hu.kuru.BaseEntity
import hu.kuru.ServiceLocator

import javax.persistence.Entity
import javax.persistence.Table
import javax.validation.constraints.NotNull

@Entity
@Table(name = "valueset")
class ValueSet extends BaseEntity {

	public static final String QUANTITY = "quantity"

	private static ValueSetRepo repo

	ValueSet() {
		if (ServiceLocator.loaded && !repo)  {
			repo = ServiceLocator.getBean(ValueSetRepo)
		}
	}

	@NotNull
	private String name;

	@NotNull
	private String valuesString;

	/* TODO stringToList-et használjon, már van ilyen a baseentity-n */
	public List<String> getValues() {
		StringTokenizer st = new StringTokenizer(valuesString, "|");
		List<String> values = new ArrayList<>();
		while (st.hasMoreTokens()) {
			values.add(st.nextToken());
		}
		return values;
	}

	ValueSet findByName(String name) {
		repo.findByName(name);
	}
}