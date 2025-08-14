package com.fotova.firstapp.controller.i18n;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

@RestController
@RequestMapping("api/v1")
public class I18nController {

    @Autowired
    private MessageSource messageSource;

    @GetMapping("auth/i18n/{lang}")
    public Map<String, String> getTranslations(@PathVariable String lang) {
        Locale locale = new Locale(lang);
        ResourceBundle bundle = ResourceBundle.getBundle("i18n/messages", locale);

        Map<String, String> translations = new HashMap<>();
        for (String key : bundle.keySet()) {
            translations.put(key, bundle.getString(key));
        }
        return translations;
    }
}