package com.wiltech.todos.providers;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "provider")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Provider implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private String baseUrl;

    @Column
    private String clientId;

    @Column
    private String clientSecret;

    @Column
    private String username;

    @Column
    private String password;

    @Column
    private String email;

    @Column
    private String website;

    @Column
    private Boolean enabled;

    public void updateName(final String name) {
        this.name = name;
    }

    public void update(final ProviderResource providerResource) {
        this.name = providerResource.getName();
        this.description = providerResource.getDescription();
        this.baseUrl = providerResource.getBaseUrl();
        this.clientId = providerResource.getClientId();
        this.clientSecret = providerResource.getClientSecret();
        this.username = providerResource.getUsername();
        this.email = providerResource.getEmail();
        this.website = providerResource.getWebsite();
        this.enabled = providerResource.getEnabled();
    }
}
