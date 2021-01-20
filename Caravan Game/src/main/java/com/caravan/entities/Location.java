package com.caravan.entities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.stream.Stream;

public enum Location {

    CITY, BASTION, CARAVAN, ;

    private static void loadStatic(String fileName) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        Map map = mapper.readValue(fileContentToString(fileName), Map.class);
    }

    //Read file content into the string with - Files.lines(Path path, Charset cs)

    private static String fileContentToString(String filePath)
    {
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines( Paths.get(filePath), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s).append("\n"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contentBuilder.toString();
    }

}
