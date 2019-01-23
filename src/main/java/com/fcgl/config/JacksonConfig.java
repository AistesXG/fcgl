package com.fcgl.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Jackson配置
 *
 * @author furg@senthink.com
 * @date 2018/01/15
 */
@Configuration
public class JacksonConfig {

    @Value("${date.timezone}")
    private String timezone;

    /**
     * 配置Jackson处理Date时使用的时区
     *
     * @return
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return jacksonObjectMapperBuilder ->
                jacksonObjectMapperBuilder.timeZone(timezone);
    }
}
