package org.wds.taskmanager.service;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomMessageSource {

    private final MessageSource message;

    public String getMessage(String key, Object... params) {
        return message.getMessage(key, params, Locale.forLanguageTag("pt-BR"));
    }
    
}

