package model.entities;

import java.io.Serializable;

public class Spending implements Serializable{

	private static final long serialVersionUID = 1L;

	
	private Integer id;
	
	private String date;

	private Double water; 
	
	private Double light; 
	
	private Double superMarket; 

	private Double creditCard;
	
	private Double others;
	
	public Spending() {
	}

	public Spending(Integer id, String date, Double water, Double light, Double superMarket, Double creditCard) {
		this.id = id;
		this.date = date;
		this.water = water;
		this.light = light;
		this.superMarket = superMarket;
		this.creditCard = creditCard;
	}
	
	public Spending(Integer id, String date, Double water, Double light, Double superMarket, Double creditCard,
			Double others) {
		this.id = id;
		this.date = date;
		this.water = water;
		this.light = light;
		this.superMarket = superMarket;
		this.creditCard = creditCard;
		this.others = others;
	}


	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Double getWater() {
		return water;
	}

	public void setWater(Double water) {
		this.water = water;
	}

	public Double getLight() {
		return light;
	}

	public void setLight(Double light) {
		this.light = light;
	}

	public Double getSuperMarket() {
		return superMarket;
	}

	public void setSuperMarket(Double superMarket) {
		this.superMarket = superMarket;
	}

	public Double getCreditCard() {
		return creditCard;
	}

	public void setCreditCard(Double creditCard) {
		this.creditCard = creditCard;
	}

	public Double getOthers() {
		return others;
	}

	public void setOthers(Double others) {
		this.others = others;
	}

	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((creditCard == null) ? 0 : creditCard.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((light == null) ? 0 : light.hashCode());
		result = prime * result + ((others == null) ? 0 : others.hashCode());
		result = prime * result + ((superMarket == null) ? 0 : superMarket.hashCode());
		result = prime * result + ((water == null) ? 0 : water.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Spending other = (Spending) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (creditCard == null) {
			if (other.creditCard != null)
				return false;
		} else if (!creditCard.equals(other.creditCard))
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (light == null) {
			if (other.light != null)
				return false;
		} else if (!light.equals(other.light))
			return false;
		if (others == null) {
			if (other.others != null)
				return false;
		} else if (!others.equals(other.others))
			return false;
		if (superMarket == null) {
			if (other.superMarket != null)
				return false;
		} else if (!superMarket.equals(other.superMarket))
			return false;
		if (water == null) {
			if (other.water != null)
				return false;
		} else if (!water.equals(other.water))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Spendings [Id=" + id + ", date=" + date + ", water=" + water + ", light=" + light + ", superMarket="
				+ superMarket + ", creditCard=" + creditCard + ", others=" + others + "]";
	}

}
