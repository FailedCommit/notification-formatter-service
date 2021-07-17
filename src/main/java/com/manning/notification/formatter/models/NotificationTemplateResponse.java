package com.manning.notification.formatter.models;

import com.manning.notification.formatter.models.enums.Status;
import lombok.Data;

@Data
public class NotificationTemplateResponse {
    private Status status;
    private String statusDescription;
    private String emailSubject;
    private String emailContent;
    private String smsContent;
}
