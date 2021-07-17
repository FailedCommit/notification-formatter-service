package com.manning.notification.formatter.controllers;

import com.manning.notification.formatter.models.NotificationTemplateRequest.NotificationParameter;
import com.manning.notification.formatter.models.NotificationTemplateResponse;
import com.manning.notification.formatter.models.NotificationTemplateRequest;
import com.manning.notification.formatter.services.NotificationFormatterService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(("/notification/preferences"))
@AllArgsConstructor
public class NotificationFormatterApi {
    private final NotificationFormatterService notificationFormatterService;

    @PostMapping
    public NotificationTemplateResponse getNotificationPreferences(
            @RequestBody NotificationTemplateRequest request) {
        final NotificationTemplateResponse response = notificationFormatterService.merge(request);
        return response;
//        for(NotificationParameter param : request.getNotificationParameters()) {
//            if("name".equalsIgnoreCase(param.getNotificationParameterName())) {
//                model.addAttribute("name", param.getNotificationParameterValue());
//            } else if("accountNumber".equalsIgnoreCase(param.getNotificationParameterName())) {
//
//            } else if("balance".equalsIgnoreCase(param.getNotificationParameterName())) {
//
//            }
//        }
//        model.addAttribute("name", request.get);
//        model.addAttribute("", );
//        model.addAttribute("", );
//        model.addAttribute("", );

    }
}
