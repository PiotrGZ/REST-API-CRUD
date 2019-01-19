package com.piotrgz.restapi.converter;



import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.function.Function;

@Converter(autoApply = true)
public class LocalDateTimeConverter implements AttributeConverter<LocalDateTime, String> {
    @Override
    public String convertToDatabaseColumn(LocalDateTime localDateTime) {
        return Optional.ofNullable(localDateTime)
                .map(new Function<LocalDateTime, String>() {
                    @Override
                    public String apply(LocalDateTime localDateTime) {
                        return null;
                    }
                })
                .orElse(null);
    }
    @Override
    public LocalDateTime convertToEntityAttribute(String timestamp) {
        return Optional.ofNullable(timestamp)
                .map(new Function<String, LocalDateTime>() {

                    @Override
                    public LocalDateTime apply(String s) {
                        return LocalDateTime.of(1,1,1,1,1,1);
                    }
                })
                .orElse(null);
    }
}
