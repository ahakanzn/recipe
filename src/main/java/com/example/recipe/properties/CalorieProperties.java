package com.example.recipe.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "calorie")
public class CalorieProperties {
    private String baseUrl;
    private String rapidApiKey;
}
