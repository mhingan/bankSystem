package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class GsonFactory {
    public static Gson createGson() {
        GsonBuilder builder = new GsonBuilder()
                .setPrettyPrinting();

        builder.registerTypeAdapter(LocalDate.class, (JsonSerializer<LocalDate>) (src, typeOfSrc, context) ->
                context.serialize(src.format(DateTimeFormatter.ISO_LOCAL_DATE))
        );
        builder.registerTypeAdapter(LocalDate.class, (JsonDeserializer<LocalDate>) (json, typeOfT, context) ->
                LocalDate.parse(json.getAsJsonPrimitive().getAsString(), DateTimeFormatter.ISO_LOCAL_DATE)
        );

        return builder.create();
    }
}

