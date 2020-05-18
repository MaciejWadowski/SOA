package com.mastertheboss.model.serializers;


import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.mastertheboss.model.Film;

import java.io.IOException;

public class FilmSerializer  extends StdSerializer<Film> {
    public FilmSerializer(Class<Film> aClass) {
        super(aClass);
    }
    public FilmSerializer() {
        super(Film.class);
    }

    @Override
    public void serialize(Film film, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonGenerationException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("id", film.getId());
        jsonGenerator.writeStringField("title", film.getTitle());
        jsonGenerator.writeStringField("uri", film.getUri());
        jsonGenerator.writeEndObject();
    }
}
