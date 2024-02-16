package com.example.demowithtests.service;

import com.example.demowithtests.domain.PassportEvent;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PassportHistoryListener {


    private PassportHistoryService passportHistoryService;

    @EventListener(PassportEvent.class)
    public void handlePassportEvent(PassportEvent event) {
        switch (event.getAction()) {
            case HANDOVER:
                passportHistoryService.createHistory(event);
                break;
            case CANCEL:
                passportHistoryService.updateHistory(event);
                break;
            case UPDATE:
                // Will be realized if necessary
                break;
        }
    }
}
