package com.revature.rpm.entities;

import java.util.Date;

import javax.persistence.*;

@Entity
@DiscriminatorColumn(name = "Content_Type")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Notification {

	@Id
	@Column(name = "notification_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int notification_id;
	
	@Column(name = "project_id")
	private int project_id;
	
	@Column(name = "is_read", columnDefinition = "boolean default false")
	private boolean is_read;
	
	@Column(name = "title")
	private String title;
	
	@Column
	@Temporal(TemporalType.DATE)
	private Date date_created;
}
