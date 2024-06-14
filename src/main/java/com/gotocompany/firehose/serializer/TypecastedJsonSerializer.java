package com.gotocompany.firehose.serializer;

import com.gotocompany.firehose.config.SerializerConfig;
import com.gotocompany.firehose.exception.DeserializerException;
import com.gotocompany.firehose.message.Message;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.function.Function;

/***
 * MessageSerializer wrapping other JSON MessageSerializer which add capability to typecast
 * some of the fields from the inner serializer
 */
@Slf4j
public class TypecastedJsonSerializer implements MessageSerializer {

    private final MessageSerializer messageSerializer;
    private final SerializerConfig serializerConfig;

    /**
     * Constructor for TypecastedJsonSerializer.
     *
     * @param messageSerializer the inner serializer to be wrapped
     * @param serializerConfig the configuration for typecasting, where each map contains
     *                       a JSON path and the desired type
     */
    public TypecastedJsonSerializer(MessageSerializer messageSerializer,
                                    SerializerConfig serializerConfig) {
        this.messageSerializer = messageSerializer;
        this.serializerConfig = serializerConfig;
    }

    /**
     * Serializes the given message, then applies typecasting to specified fields in the resulting JSON.
     *
     * @param message the message to be serialized
     * @return the serialized and typecasted JSON string
     * @throws DeserializerException if an error occurs during serialization or typecasting
     */
    @Override
    public String serialize(Message message) throws DeserializerException {
        String jsonString = messageSerializer.serialize(message);
        DocumentContext documentContext = JsonPath.parse(jsonString);

        for (Map.Entry<String, Function<String, Object>> entry : serializerConfig.getJsonTypecastMapping()
                .entrySet()) {
            try {
                documentContext.map(entry.getKey(), (currentValue, configuration) -> entry.getValue()
                        .apply(currentValue.toString()));
            } catch (PathNotFoundException e) {
                log.info("Could not find path '" + entry.getKey() + "'");
            }

        }
        return documentContext.jsonString();
    }
}
