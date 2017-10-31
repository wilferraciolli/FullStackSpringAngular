package com.linkedin.learning;

import com.linkedin.learning.entity.RoomEntity;
import com.linkedin.learning.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * The type H2 bootstrap. This is the helper class to auto populate data onto the H2 in memory database.
 */
@Component
public class H2Bootstrap implements CommandLineRunner {

    @Autowired
    RoomRepository roomRepository;

    @Override
    public void run(String... args) throws Exception {
        //check when entities are being persisted
        System.out.println("Bootstrapping data ");

        roomRepository.save(new RoomEntity(405, "200"));
        roomRepository.save(new RoomEntity(406, "220"));
        roomRepository.save(new RoomEntity(407, "250"));

        //loop through every entity persisted
       Iterable<RoomEntity> iterable =  roomRepository.findAll();
       for (RoomEntity room : iterable){
           System.out.println(room.getRoomNumber());
       }
    }
}
