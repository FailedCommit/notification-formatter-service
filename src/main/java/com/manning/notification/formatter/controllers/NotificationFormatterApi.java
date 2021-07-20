package com.manning.notification.formatter.controllers;

import com.manning.notification.formatter.models.NotificationTemplateRequest.NotificationParameter;
import com.manning.notification.formatter.models.NotificationTemplateResponse;
import com.manning.notification.formatter.models.NotificationTemplateRequest;
import com.manning.notification.formatter.models.enums.Status;
import com.manning.notification.formatter.services.NotificationFormatterService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static com.manning.notification.formatter.models.enums.Status.SUCCESS;

@RestController
@RequestMapping(("/notification/preferences"))
@AllArgsConstructor
public class NotificationFormatterApi {
    private final NotificationFormatterService notificationFormatterService;

    @PostMapping
    public NotificationTemplateResponse getNotificationPreferences(
            @RequestBody NotificationTemplateRequest request) {
        final NotificationTemplateResponse response = notificationFormatterService.merge(request);
        response.setStatus(SUCCESS);
        response.setStatusDescription("Successfully merged the template with the template parameters");
        return response;
    }

    @GetMapping("/healthcheck")
    public String healthCheck (){
        return "UP";
    }
}
