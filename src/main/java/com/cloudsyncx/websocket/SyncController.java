package com.cloudsyncx.websocket;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class SyncController {

    @MessageMapping("/sync")
    @SendTo("/topic/files")
    public FileUpdateMessage sync(
            FileUpdateMessage message) {

        return message;
    }
}