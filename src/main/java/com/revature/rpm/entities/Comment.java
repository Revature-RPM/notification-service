package com.revature.rpm.entities;

import javax.persistence.*;

@Entity
@DiscriminatorValue(value = "Comment")
public class Comment extends Notification{

	@Column(name = "short_description")
	private String shortDescription;

	@Column(name = "full_description")
	private String fullDescription;

	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public String getFullDescription() {
		return fullDescription;
	}

	public void setFullDescription(String fullDescription) {
		this.fullDescription = fullDescription;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((fullDescription == null) ? 0 : fullDescription.hashCode());
		result = prime * result + ((shortDescription == null) ? 0 : shortDescription.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Comment other = (Comment) obj;
		if (fullDescription == null) {
			if (other.fullDescription != null)
				return false;
		} else if (!fullDescription.equals(other.fullDescription))
			return false;
		if (shortDescription == null) {
			if (other.shortDescription != null)
				return false;
		} else if (!shortDescription.equals(other.shortDescription))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Comment [shortDescription=" + shortDescription + ", fullDescription=" + fullDescription + "]";
	}

	public Comment(String shortDescription, String fullDescription) {
		super();
		this.shortDescription = shortDescription;
		this.fullDescription = fullDescription;
	}

	public Comment() {
		super();
		// TODO Auto-generated constructor stub
	}


	
}
