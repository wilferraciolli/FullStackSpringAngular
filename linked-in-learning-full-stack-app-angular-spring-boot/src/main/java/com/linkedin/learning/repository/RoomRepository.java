package com.linkedin.learning.repository;

import com.linkedin.learning.entity.RoomEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * The interface Room repository.
 */
public interface RoomRepository extends CrudRepository<RoomEntity, Long> {

    /**
     * Find by id list.
     *
     * @param id the id
     * @return the list
     */
    List<RoomEntity> findById(Long id);
}
