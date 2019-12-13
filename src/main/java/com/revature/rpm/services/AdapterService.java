package com.revature.rpm.services;

import org.springframework.stereotype.Service;

import com.revature.rpm.dto.SQSDTO;
import com.revature.rpm.entities.Comment;
import com.revature.rpm.entities.Notification;

/**
 * This service is meant to accept the DTO from the AWS SQS Message Queue and return a notification
 * of the appropriate type. At the moment, you need to determine the format externally.
 *
 * @author ChrisTroll
 */
@Service
public class AdapterService {
  // TODO Update parseNotification to accept an SQSDTO and return the appropriate notification type, NOT Notification literal
  public Notification parseNotification(SQSDTO dto) {
    if (dto.getContentType() == "Comment") {
      Comment comment = new Comment();
      comment.setDateCreated(dto.getDateCreated());
      comment.setFullDescription(dto.getFullDescription());
      // Notification ID is created when you save a comment, so we omit it here
      comment.setProjectId(dto.getProjectId());
      comment.setRead(false);
      comment.setShortDescription(dto.getShortDescription());
      comment.setTitle(dto.getTitle());
      comment.setUserId(dto.getUserId());

      return comment;
    }
    return null;
  }

  /**
   * Accepts an SQSDTO and returns a mapped Comment object.
   *
   * @param dto
   * @return
   */
  public Comment parseComment(SQSDTO dto) {
    Comment comment = new Comment();
    comment.setDateCreated(dto.getDateCreated());
    comment.setFullDescription(dto.getFullDescription());
    // Notification ID is created when you save a comment, so we omit it here
    comment.setProjectId(dto.getProjectId());
    comment.setRead(false);
    comment.setShortDescription(dto.getShortDescription());
    comment.setTitle(dto.getTitle());
    comment.setUserId(dto.getUserId());

    return comment;
  }
}
