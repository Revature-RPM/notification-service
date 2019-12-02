package com.revature.rpm.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.rpm.dto.NotificationDTO;
import com.revature.rpm.entities.Notification;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer>{

	Page<NotificationDTO> findAllByOrderByDate(Pageable page);

}
