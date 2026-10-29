package com.cloudsyncx.listener;

import com.cloudsyncx.event.VersionCreatedEvent;
import com.cloudsyncx.websocket.FileUpdateMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VersionEventListener {

    private final SimpMessagingTemplate messagingTemplate;

    @EventListener
    public void handleVersionCreated(
            VersionCreatedEvent event) {

        String msg =
                "File "
                + event.getFileId()
                + " updated to version "
                + event.getVersionNumber();

        System.out.println(msg);

        messagingTemplate.convertAndSend(
                "/topic/files",
                new FileUpdateMessage(msg)
        );
    }
}