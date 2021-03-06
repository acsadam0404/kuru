package hu.kuru
;

import javax.persistence.Column
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.MappedSuperclass

@MappedSuperclass
abstract class BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique = true)
	protected Long id

	Long getId() {
		return id
	}

	void setId(Long id) {
		this.id = id
	}

	@Override
	public boolean equals(Object obj) {
		if (obj.is(this)) {
			return true
		}
		if (obj && obj instanceof BaseEntity && obj.getClass() == getClass() && ((BaseEntity) obj).getId() == id) {
			return true
		}
		return false
	}

	@Override
	public int hashCode() {
		if (id) {
			return (int) id
		}
		return super.hashCode()
	}

	@Override
	String toString() {
		return getClass().getSimpleName() + "(" + id + ")";
	}
}