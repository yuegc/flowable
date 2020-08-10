package com.flowable.core.config;

import org.flowable.ui.modeler.properties.FlowableModelerAppProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;

/**
 * @Description:
 * @Author: Bruce.liu
 * @Since:11:00 2018/9/20
 * 爱拼才会赢 2018 ~ 2030 版权所有
 */
@Configuration
@EnableConfigurationProperties({FlowableModelerAppProperties.class})
@ComponentScan(basePackages = {
        /*"org.flowable.ui.idm.conf",
        "org.flowable.ui.idm.security",
        "org.flowable.ui.idm.service",*/
        "org.flowable.ui.modeler.repository",
        "org.flowable.ui.modeler.service",
        "org.flowable.ui.common.filter",
        "org.flowable.ui.common.service",
        "org.flowable.ui.common.repository",
        "org.flowable.ui.common.security",
        "org.flowable.ui.common.tenant"}, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = org.flowable.ui.common.service.idm.RemoteIdmService.class)})
public class ApplicationConfiguration {

    @Bean
    public HttpFirewall allowUrlEncodedSlashHttpFirewall() {
        StrictHttpFirewall firewall = new StrictHttpFirewall();
        firewall.setAllowUrlEncodedSlash(true);
        return firewall;
    }
}
