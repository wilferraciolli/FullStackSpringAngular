package com.wiltech.todos.todos;

import static com.wiltech.Meta.HIDDEN_AND_READ_ONLY_MAP;
import static com.wiltech.Meta.MANDATORY_MAP;
import static com.wiltech.Meta.generateEmbeddedValues;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.wiltech.EmbeddedMetadata;
import com.wiltech.EmbeddedMetadataSimple;
import com.wiltech.IMetaFabricator;
import com.wiltech.Meta;

@Service
public class TodoMetaFabricator implements IMetaFabricator {

    @Override
    public Meta createMetaForTemplate() {
        return buildBasicMeta();
    }

    @Override
    public Meta createMetaForSingleResource() {
        return buildBasicMeta();
    }

    @Override
    public Meta createMetaForCollectionResource() {
        return buildCollectionMeta();
    }

    private Meta buildBasicMeta() {
        final Meta meta = new Meta();
        meta.getValues().put("id", HIDDEN_AND_READ_ONLY_MAP);
        meta.getValues().put("name", MANDATORY_MAP);
        meta.getValues().put("stateId", MANDATORY_MAP);

        return meta;
    }

    private Meta buildCollectionMeta() {

        final Meta meta = new Meta();
        meta.getValues().put("id", HIDDEN_AND_READ_ONLY_MAP);
        meta.getValues().put("stateId", generateEmbeddedValues(generateTodoStateEmbedded()));

        return meta;
    }

    private List<EmbeddedMetadata> generateTodoStateEmbedded() {

        return TodoStateType.stream()
                .map(value -> new EmbeddedMetadataSimple(value.name(), value.getDescription()))
                .collect(Collectors.toList());
    }
}
