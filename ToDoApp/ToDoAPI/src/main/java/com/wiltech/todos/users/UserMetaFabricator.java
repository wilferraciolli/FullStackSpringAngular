/*
 * (c) Midland Software Limited 2019
 * Name     : UserMetaFabricator.java
 * Author   : ferraciolliw
 * Date     : 18 Sep 2019
 */
package com.wiltech.todos.users;

import static com.wiltech.Meta.HIDDEN_AND_READ_ONLY_MAP;
import static com.wiltech.Meta.MANDATORY_MAP;
import static com.wiltech.Meta.generateEmbeddedValues;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.wiltech.EmbeddedMetadata;
import com.wiltech.EmbeddedMetadataSimple;
import com.wiltech.IMetaFabricator;
import com.wiltech.Meta;

/**
 * The type User meta fabricator.
 */
@Service
public class UserMetaFabricator implements IMetaFabricator {

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
        meta.getValues().put("userName", MANDATORY_MAP);
        meta.getValues().put("password", MANDATORY_MAP);
        meta.getValues().put("roleIds", generateEmbeddedValues(Map.of(Meta.MANDATORY, Meta.TRUE), generateUserRoleEmbedded()));

        return meta;
    }

    /**
     * Build collection meta meta.
     * @return the meta
     */
    private Meta buildCollectionMeta() {

        final Meta meta = new Meta();
        meta.getValues().put("id", HIDDEN_AND_READ_ONLY_MAP);
        meta.getValues().put("roleIds", generateEmbeddedValues(Map.of(Meta.MANDATORY, Meta.TRUE), generateUserRoleEmbedded()));

        return meta;
    }

    private List<EmbeddedMetadata> generateUserRoleEmbedded() {
        return UserRoleType.stream()
                .map(value -> new EmbeddedMetadataSimple(value.name(), value.getDescription()))
                .collect(Collectors.toList());
    }

}
