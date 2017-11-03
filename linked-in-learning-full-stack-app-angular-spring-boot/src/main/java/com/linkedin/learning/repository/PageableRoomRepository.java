package com.linkedin.learning.repository;

import com.linkedin.learning.entity.RoomEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * The interface Pageable room repository. Pageable repository.
 */
public interface PageableRoomRepository extends PagingAndSortingRepository<RoomEntity, Long> {

    /**
     * Find by id page.
     *
     * @param id   the id
     * @param page the page
     * @return the page
     */
    Page<RoomEntity> findById(Long id, Pageable page);
}
