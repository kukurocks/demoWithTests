package com.example.demowithtests.service;

import com.example.demowithtests.domain.Passport;
import com.example.demowithtests.domain.PassportEvent;

public interface PassportHistoryService {
   void createHistory(PassportEvent event);
   void updateHistory(PassportEvent event);
}
