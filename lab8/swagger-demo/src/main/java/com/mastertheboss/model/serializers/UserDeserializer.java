package com.mastertheboss.model.serializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.mastertheboss.model.Film;
import com.mastertheboss.model.User;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class UserDeserializer  extends StdDeserializer<User> {
    public UserDeserializer(Class<?> vc) {
        super(vc);
    }

    public UserDeserializer() {
        super(User.class);
    }
    @Override
    public User deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        User user = new User();
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        if (node.get("id").isNull()) {
            user.setId(null);
        } else {
            Long id = node.get("id").asLong();
            user.setId(id);
        }
        if (!node.get("name").isNull() && !node.get("name").textValue().isBlank()) {
            String name = node.get("name").asText();
            user.setName(name);
        }

        if (!node.get("name").isNull()) {
            Integer age = node.get("age").asInt();
            user.setAge(age);
        }
        String base64 = node.get("avatar").asText();
        if (!node.get("avatar").isNull() && !node.get("name").textValue().isBlank()) {
            byte[] avatar = Base64.getDecoder().decode(base64);
            user.setAvatar(avatar);
        }
        JsonNode arrayNode = node.get("films");
        List<Film> films = new ArrayList<>();
        if (arrayNode.isArray()) {
            Iterator<JsonNode> i = arrayNode.iterator();
            while(i.hasNext()) {
                JsonNode jsonNode = i.next();
                Long filmId = jsonNode.get("id").asLong();
                String filmTitle = jsonNode.get("title").asText();
                String filmUri = jsonNode.get("uri").asText();
                Film film = new Film();
                film.setId(filmId);
                film.setTitle(filmTitle);
                film.setUri(filmUri);
                films.add(film);
            }
        }
        user.setFilms(new HashSet<>(films));
        return user;
    }
}
