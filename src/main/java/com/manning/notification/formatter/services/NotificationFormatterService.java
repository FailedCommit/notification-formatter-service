package com.manning.notification.formatter.services;

import com.manning.notification.formatter.models.NotificationTemplateRequest;
import com.manning.notification.formatter.models.NotificationTemplateRequest.NotificationParameter;
import com.manning.notification.formatter.models.NotificationTemplateResponse;
import lombok.AllArgsConstructor;
import org.apache.commons.text.StringSubstitutor;
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

    public NotificationTemplateResponse merge(NotificationTemplateRequest request) {
        if ("EMAIL".equalsIgnoreCase(request.getNotificationMode())) {
            return prepareEmailTemplate(request);
        } else if ("SMS".equalsIgnoreCase(request.getNotificationMode())) {
            return prepareSMSTemplate(request);
        }

        return new NotificationTemplateResponse();
    }

    private NotificationTemplateResponse prepareSMSTemplate(NotificationTemplateRequest request) {
        NotificationTemplateResponse response = new NotificationTemplateResponse();
        String smsTemplateString = "";
        if ("ViewBalance".equalsIgnoreCase(request.getNotificationTemplateName())) {
            smsTemplateString = this.getBalanceSMSTemplate();
        } else if ("PhoneNumberChanged".equalsIgnoreCase(request.getNotificationTemplateName())) {
            smsTemplateString = this.getPhoneNumberChanged();
        }
        StringSubstitutor sub = new StringSubstitutor(prepareNotificationParamsMap(request));
        String notificationContent = sub.replace(smsTemplateString);
        response.setSmsContent(notificationContent);
        return response;
    }

    private NotificationTemplateResponse prepareEmailTemplate(NotificationTemplateRequest request) {
        String notificationContent = prepareNotificationContent(request);
        NotificationTemplateResponse response = new NotificationTemplateResponse();
        response.setEmailContent(notificationContent);
        if ("ViewBalance".equalsIgnoreCase(request.getNotificationTemplateName())) {
            response.setEmailSubject("Your Bank Balance");
        } else if ("PhoneNumberChanged".equalsIgnoreCase(request.getNotificationTemplateName())) {
            response.setEmailSubject("Your Phone Number Changed");
        }
        return response;
    }

    private String prepareNotificationContent(NotificationTemplateRequest request) {
        String notificationContent = "";
        Context context = new Context();
        Map<String, Object> notificationParametersMap = prepareNotificationParamsMap(request);
        context.setVariables(notificationParametersMap);
        File emailTemplateFile = new File("./src/main/resources/templates/" + request.getNotificationTemplateName() + ".html");
        if (emailTemplateFile.exists()) {
            notificationContent = templateEngine.process(request.getNotificationTemplateName() + ".html", context);
        }
        return notificationContent;
    }

    private Map<String, Object> prepareNotificationParamsMap(NotificationTemplateRequest request) {
        Map<String, Object> notificationParametersMap =
                request.getNotificationParameters().
                        stream()
                        .collect(Collectors
                                .toMap(NotificationParameter::getNotificationParameterName, NotificationParameter::getNotificationParameterValue));
        return notificationParametersMap;
    }

    public static String getBalanceSMSTemplate() {
        return "Hello ${name}"
                .concat("\n")
                .concat("Welcome to the Citizen Bank\n")
                .concat("Your balance is ${balance}\n")
                .concat("Thanks");
    }

    private String getPhoneNumberChanged() {
        return "Hello ${name}"
                .concat("\n")
                .concat("Welcome to the Citizen Bank\n")
                .concat("Your Phonenumber is changed from ${oldPhoneNumber} to ${newPhoneNumber}\n");
    }
}