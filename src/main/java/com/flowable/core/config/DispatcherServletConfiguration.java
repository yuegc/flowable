//package com.flowable.core.config;
//
//import lombok.extern.slf4j.Slf4j;
//import org.flowable.ui.modeler.rest.app.EditorGroupsResource;
//import org.flowable.ui.modeler.rest.app.EditorUsersResource;
//import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.FilterType;
//import org.springframework.scheduling.annotation.EnableAsync;
//import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
//import org.springframework.web.servlet.i18n.SessionLocaleResolver;
//import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
//
//@Configuration
//@EnableAsync
//@ComponentScan(value = {"org.flowable.ui.modeler.rest.app"},
//        excludeFilters = {
//                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = EditorUsersResource.class),
//                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = EditorGroupsResource.class),
//        })
//@Slf4j
//public class DispatcherServletConfiguration implements WebMvcRegistrations {
//    @Bean
//    public SessionLocaleResolver localeResolver() {
//        return new SessionLocaleResolver();
//    }
//
//    @Bean
//    public LocaleChangeInterceptor localeChangeInterceptor() {
//        log.debug("Configuring localeChangeInterceptor");
//        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
//        localeChangeInterceptor.setParamName("language");
//        return localeChangeInterceptor;
//    }
//
//    @Override
//    public RequestMappingHandlerMapping getRequestMappingHandlerMapping() {
//        log.debug("Creating requestMappingHandlerMapping");
//        RequestMappingHandlerMapping requestMappingHandlerMapping = new RequestMappingHandlerMapping();
//        requestMappingHandlerMapping.setUseSuffixPatternMatch(false);
//        requestMappingHandlerMapping.setRemoveSemicolonContent(false);
//        Object[] interceptors = { localeChangeInterceptor() };
//        requestMappingHandlerMapping.setInterceptors(interceptors);
//        return requestMappingHandlerMapping;
//    }
//}
