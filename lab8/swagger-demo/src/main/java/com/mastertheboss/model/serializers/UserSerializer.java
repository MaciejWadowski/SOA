package com.mastertheboss.model.serializers;


import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.mastertheboss.model.Film;
import com.mastertheboss.model.User;

import java.io.IOException;
import java.util.Base64;

public class UserSerializer extends StdSerializer<User> {
    public UserSerializer(Class<User> aClass) {
        super(aClass);
    }
    public UserSerializer() {
        super(User.class);
    }

    @Override
    public void serialize(User user, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonGenerationException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("id", user.getId());
        jsonGenerator.writeNumberField("age", user.getAge());
        jsonGenerator.writeStringField("name", user.getName());
        jsonGenerator.writeArrayFieldStart("films");
        if (user.getFilms() != null) {
            for (Film f : user.getFilms()) {
                jsonGenerator.writeObject(f);
            }
        }
        jsonGenerator.writeEndArray();
        String base = "";
        if (user.getAvatar() != null) {
            base = Base64.getEncoder().encodeToString(user.getAvatar());
        }
        jsonGenerator.writeStringField("avatar", base);
        jsonGenerator.writeEndObject();

    }
}
