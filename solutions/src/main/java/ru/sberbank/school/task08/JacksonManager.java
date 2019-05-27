package ru.sberbank.school.task08;

import com.sun.org.apache.bcel.internal.generic.RETURN;
import lombok.NonNull;
import ru.sberbank.school.task08.state.GameObject;
import ru.sberbank.school.task08.state.InstantiatableEntity;
import ru.sberbank.school.task08.state.MapState;
import ru.sberbank.school.task08.state.Savable;
import ru.sberbank.school.util.Solution;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import sun.java2d.loops.DrawGlyphListAA;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Solution(8)
public class JacksonManager extends SaveGameManager<MapState<GameObject>, GameObject> {
    private ObjectMapper mapper;

    public JacksonManager(@NonNull String filesDirectoryPath) {
        super(filesDirectoryPath);
    }

    @Override
    public void initialize() {
        mapper = new ObjectMapper();
    }

    @Override
    public void saveGame(String filename, MapState<GameObject> gameState) throws SaveGameException {
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        try {
            mapper.writeValue(new File(filesDirectory + File.separator + filename), gameState);
            //System.out.println(mapper.createArrayNode());
        } catch (JsonGenerationException exc) {
            exc.printStackTrace();
        } catch (JsonMappingException exc) {
            exc.printStackTrace();
        } catch (IOException exc) {
            exc.printStackTrace();
        }

    }

    @Override
    public MapState<GameObject> loadGame(String filename) throws SaveGameException {
        try {
            return mapper.readValue(new File(filesDirectory + File.separator + filename), MapState.class);
        } catch (JsonGenerationException exc) {
            exc.printStackTrace();
        } catch (JsonMappingException exc) {
            exc.printStackTrace();
        } catch (IOException exc) {
            exc.printStackTrace();
        }

        return null;
    }

    @Override
    public GameObject createEntity(InstantiatableEntity.Type type,
                                             InstantiatableEntity.Status status,
                                             long hitPoints) {
        return new GameObject(type, status, hitPoints);
    }

    @Override
    public MapState<GameObject> createSavable(String name, List<GameObject> entities) {
        return new MapState<GameObject>(name, entities);
    }

}
