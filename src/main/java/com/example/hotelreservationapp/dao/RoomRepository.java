package com.example.hotelreservationapp.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.hotelreservationapp.entity.Room;

public interface RoomRepository extends JpaRepository<Room, Long> {

	Optional<Room> getRoomByRoomNumber(int roomNumber);
	
	List<Room> findByHotelId(Long hotelId);
	
}
