package com.wiltech.todos.providers;

import org.springframework.data.jpa.repository.JpaRepository;

//@RepositoryRestResource(path = "providers", collectionResourceRel = "providers", itemResourceRel = "providers")
public interface ProviderRepository extends JpaRepository<Provider, Long> {
}
