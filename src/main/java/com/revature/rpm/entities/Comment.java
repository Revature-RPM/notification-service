package com.revature.rpm.entities;

import javax.persistence.*;

@Entity
@DiscriminatorValue(value = "Content")
public class Comment extends Notification{
	
	@Column
	private String content;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((content == null) ? 0 : content.hashCode());
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
		Comment other = (Comment) obj;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Comment [content=" + content + "]";
	}

	public Comment(String content) {
		super();
		this.content = content;
	}

	public Comment() {
		super();
		// TODO Auto-generated constructor stub
	}

	

}
