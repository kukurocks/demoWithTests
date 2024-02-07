package com.example.demowithtests.util.annotation.dto;

import javax.validation.Payload;

public interface Severity {
    interface Info extends Payload {}
    interface Error extends Payload {}

}
