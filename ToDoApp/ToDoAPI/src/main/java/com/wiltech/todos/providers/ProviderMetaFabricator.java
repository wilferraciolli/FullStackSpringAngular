package com.wiltech.todos.providers;

import static com.wiltech.Meta.HIDDEN_AND_READ_ONLY_MAP;
import static com.wiltech.Meta.MANDATORY_MAP;
import static com.wiltech.Meta.generateEmbeddedValues;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.wiltech.EmbeddedMetadata;
import com.wiltech.IMetaFabricator;
import com.wiltech.Meta;

/**
 * The type Provider meta fabricator.
 */
@Service
public class ProviderMetaFabricator implements IMetaFabricator {

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
        meta.getValues().put("baseUrl", MANDATORY_MAP);

        return meta;
    }

    private Meta buildCollectionMeta() {

        final Meta meta = new Meta();
        meta.getValues().put("id", HIDDEN_AND_READ_ONLY_MAP);
        meta.getValues()
                .put("test", generateEmbeddedValues(Map.of(Meta.MANDATORY, Meta.TRUE, Meta.HIDDEN, Meta.TRUE), generatePersonGenderEmbedded()));

        return meta;
    }

    private List<EmbeddedMetadata> generatePersonGenderEmbedded() {
        return new ArrayList<>();
    }
}
