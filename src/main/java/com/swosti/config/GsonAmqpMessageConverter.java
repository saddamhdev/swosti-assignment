package com.swosti.config;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.MessageConverter;
public class GsonAmqpMessageConverter implements MessageConverter {

    private final Gson gson = new Gson();

    @Override
    public Message toMessage(Object object, MessageProperties props) {
        props.setContentType(MessageProperties.CONTENT_TYPE_JSON);
        props.setHeader("__TypeId__", object.getClass().getName());

        String json = gson.toJson(object);
        return new Message(json.getBytes(), props);
    }

    @Override
    public Object fromMessage(Message message) {
        try {
            String className = (String) message.getMessageProperties()
                    .getHeaders().get("__TypeId__");

            Class<?> clazz = Class.forName(className);
            String json = new String(message.getBody());

            return gson.fromJson(json, clazz);
        } catch (Exception e) {
            throw new MessageConversionException("JSON conversion failed", e);
        }
    }
}
