package entity;

public abstract class Asset {
	// ATTRIBUTI
	protected Long id;
	
	// COSTRUTTORI
	protected Asset() {}
	
	// GETTERS AND SETTERS
	public Long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	// METODI
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Asset other = (Asset) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	

	
}
