package com.lokapos.configs;


import com.lokapos.annotations.PublicAccess;
import lombok.Getter;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.HashSet;
import java.util.Set;

@Getter
@Component
public class PublicApiScanner {
    private final Set<String> publicUrls = new HashSet<>();

    public PublicApiScanner(ApplicationContext context) {
        RequestMappingHandlerMapping requestMappingHandlerMapping = context.getBean(RequestMappingHandlerMapping.class);
        for (var entry : requestMappingHandlerMapping.getHandlerMethods().entrySet()) {
            HandlerMethod handlerMethod = entry.getValue();

            if (handlerMethod.getMethodAnnotation(PublicAccess.class) != null) {
                publicUrls.addAll(entry.getKey().getPatternValues());
            }
        }
    }

}