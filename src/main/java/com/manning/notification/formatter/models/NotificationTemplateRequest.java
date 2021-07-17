package com.manning.notification.formatter.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class NotificationTemplateRequest {
    private List<NotificationParameter> notificationParameters;
    private String notificationTemplateName;
    private String notificationMode;

    @Data
    public static class NotificationParameter {
        private String notificationParameterName;
        private String notificationParameterValue;
    }
}
