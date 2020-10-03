package com.wiltech.todos.todos;

import static com.wiltech.Meta.HIDDEN_AND_READ_ONLY_MAP;
import static com.wiltech.Meta.MANDATORY_MAP;
import static com.wiltech.Meta.READ_ONLY;
import static com.wiltech.Meta.TRUE;
import static com.wiltech.Meta.generateEmbeddedValues;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wiltech.EmbeddedMetadata;
import com.wiltech.EmbeddedMetadataSimple;
import com.wiltech.IMetaFabricator;
import com.wiltech.Meta;
import com.wiltech.todos.exceptions.DomainException;
import com.wiltech.todos.users.UserDetailsViewRepository;

@Service
public class TodoMetaFabricator implements IMetaFabricator {

    @Autowired
    private UserDetailsViewRepository userDetailsViewRepository;

    public Meta createMetaForTemplate(final Long personId) {
        return buildBasicMeta(personId);
    }

    public Meta createMetaForCollectionResource(final Long personId) {
        return buildCollectionMeta(personId);
    }

    @Override
    public Meta createMetaForTemplate() {
        return null;
    }

    @Override
    public Meta createMetaForSingleResource() {
        return buildBasicMeta(1L);
    }

    @Override
    public Meta createMetaForCollectionResource() {
        return null;
    }

    private Meta buildBasicMeta(Long personId) {
        final Meta meta = new Meta();
        meta.getValues().put("id", HIDDEN_AND_READ_ONLY_MAP);
        meta.getValues().put("personId", generateEmbeddedValues(Map.of(READ_ONLY, TRUE), generatePersonDetailsEmbedded(personId)));
        meta.getValues().put("typeId", generateEmbeddedValues(Map.of(Meta.MANDATORY, TRUE), generateTodoTypeEmbedded()));
        meta.getValues().put("name", MANDATORY_MAP);
        meta.getValues().put("stateId", generateEmbeddedValues(Map.of(Meta.MANDATORY, TRUE), generateTodoStateEmbedded()));

        return meta;
    }

    private Meta buildCollectionMeta(final Long personId) {

        final Meta meta = new Meta();
        meta.getValues().put("id", HIDDEN_AND_READ_ONLY_MAP);
        meta.getValues().put("personId", generateEmbeddedValues(generatePersonDetailsEmbedded(personId)));
        meta.getValues().put("typeId", generateEmbeddedValues(generateTodoTypeEmbedded()));
        meta.getValues().put("stateId", generateEmbeddedValues(generateTodoStateEmbedded()));

        return meta;
    }

    private List<EmbeddedMetadata> generatePersonDetailsEmbedded(final Long personId) {

        List<EmbeddedMetadata> personEmbeddedMeta = new ArrayList<>();

        personEmbeddedMeta.add(new EmbeddedMetadataSimple(String.valueOf(personId), userDetailsViewRepository.findByPersonId(personId)
                .map(u -> String.format("%s %s", u.getFirstName(), u.getLastName()))
                .orElseThrow(() -> new DomainException("Could not find user details for given person id"))));

        return personEmbeddedMeta;
    }

    private List<EmbeddedMetadata> generateTodoStateEmbedded() {

        return TodoStateType.stream()
                .map(value -> new EmbeddedMetadataSimple(value.name(), value.getDescription()))
                .collect(Collectors.toList());
    }

    private List<EmbeddedMetadata> generateTodoTypeEmbedded() {

        return TodoType.stream()
                .map(value -> new EmbeddedMetadataSimple(value.name(), value.getDescription()))
                .collect(Collectors.toList());
    }
}
