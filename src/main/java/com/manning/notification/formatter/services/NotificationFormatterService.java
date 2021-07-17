package com.manning.notification.formatter.services;

import com.manning.notification.formatter.models.NotificationTemplateRequest;
import com.manning.notification.formatter.models.NotificationTemplateRequest.NotificationParameter;
import com.manning.notification.formatter.models.NotificationTemplateResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import java.io.File;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class NotificationFormatterService {
    private final SpringTemplateEngine templateEngine;

    public NotificationTemplateResponse format(NotificationTemplateRequest request) {
        return null;
    }

    public NotificationTemplateResponse merge(NotificationTemplateRequest request) {
        Map<String, Object> notificationParametersMap =
                request.getNotificationParameters().
                        stream()
                        .collect(Collectors
                                .toMap(NotificationParameter::getNotificationParameterName, NotificationParameter::getNotificationParameterValue));
        NotificationTemplateResponse notificationTemplateResponse = new NotificationTemplateResponse();
        String notificationContent = null;

        if ("EMAIL".equals(request.getNotificationMode())) {
            Context context = new Context();
            context.setVariables(notificationParametersMap);
            File emailTemplateFile = new File("./src/main/resources/templates/email/" + request.getNotificationTemplateName() + ".html");
            NotificationTemplateResponse response = new NotificationTemplateResponse();
            if (emailTemplateFile.exists()) {
                notificationContent = templateEngine.process("./templates/email/" + request.getNotificationTemplateName() + ".html", context);
                notificationTemplateResponse.setEmailContent(notificationContent);
            }
        }

        return notificationTemplateResponse;
    }
}