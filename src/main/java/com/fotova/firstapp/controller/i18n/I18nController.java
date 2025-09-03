package com.fotova.firstapp.controller.i18n;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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


    @Operation(summary = "Retrieve labels and tooltips")
    @ApiResponse(responseCode = "200", description = "Retrieve all labels and tooltips from the i18 file",
            content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = String.class))
            })
    @GetMapping("auth/i18n/{lang}")
    public Map<String, String> getTranslations(
            @Parameter(description = "language to get the right file", required = true, example = "en")
            @PathVariable String lang) {
        Locale locale = new Locale(lang);
        ResourceBundle bundle = ResourceBundle.getBundle("i18n/messages", locale);

        Map<String, String> translations = new HashMap<>();
        for (String key : bundle.keySet()) {
            translations.put(key, bundle.getString(key));
        }
        return translations;
    }
}