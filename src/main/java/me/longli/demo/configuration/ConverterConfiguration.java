package me.longli.demo.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

@Configuration
public class ConverterConfiguration {

    @Bean
    public StringToLocalDateTime stringToLocalDateTime() {
        return new StringToLocalDateTime();
    }

    static class StringToLocalDateTime implements Converter<String, LocalDateTime> {
        @Override
        public LocalDateTime convert(String from) {
            TemporalAccessor accessor = DateTimeFormatter.ISO_LOCAL_DATE_TIME.parse(from);
            return LocalDateTime.from(accessor);
        }
    }
}
