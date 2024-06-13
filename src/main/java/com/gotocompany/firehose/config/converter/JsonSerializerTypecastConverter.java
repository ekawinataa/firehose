package com.gotocompany.firehose.config.converter;

import com.gotocompany.firehose.exception.JsonParseException;
import com.gotocompany.firehose.serializer.constant.NumericType;
import lombok.*;
import org.aeonbits.owner.Converter;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class JsonSerializerTypecastConverter implements Converter<Map<String, Function<String, Number>>> {

    private final ObjectMapper objectMapper;

    public JsonSerializerTypecastConverter() {
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public Map<String, Function<String, Number>> convert(Method method, String s) {
        try {
            List<JsonTypecastField> jsonTypecastFields =
                    objectMapper.readValue(s, new TypeReference<List<JsonTypecastField>>(){});
            return jsonTypecastFields.stream()
                    .collect(Collectors.toMap(JsonTypecastField::getJsonPath, jtf -> jtf.getType()::getValue));
        } catch (IOException e) {
            throw new JsonParseException(e.getMessage(), e.getCause());
        }
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    @Builder
    private static class JsonTypecastField {
        private String jsonPath;
        private NumericType type;
    }

}