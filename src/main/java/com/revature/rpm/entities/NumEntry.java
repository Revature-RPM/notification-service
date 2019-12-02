package com.revature.rpm.entities;

import javax.persistence.*;

@Entity
@DiscriminatorValue(value = "Value")
public class NumEntry extends Notification{

	@Column
	private int value;

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + value;
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
		NumEntry other = (NumEntry) obj;
		if (value != other.value)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "NumEntry [value=" + value + "]";
	}

	public NumEntry(int value) {
		super();
		this.value = value;
	}

	public NumEntry() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
