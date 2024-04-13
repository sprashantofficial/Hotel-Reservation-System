package com.example.hotelreservationapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.hotelreservationapp.entity.Room;
import com.example.hotelreservationapp.service.RoomService;

@Controller
@RequestMapping("/rooms")
public class RoomController {

	@Autowired
	private RoomService roomService;

	@GetMapping
	public String getAllRooms(Model model) {
		model.addAttribute("rooms", roomService.getAllRooms());
		return "room-list";
	}

	@GetMapping("/{id}")
	public String getRoomById(@PathVariable Long id, Model model) {
		Room room = roomService.getRoomById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid room id: " + id));
		model.addAttribute("room", room);
		return "room-details";
	}

	@GetMapping("/room-number/{roomNumber}")
	public String getRoomByRoomNumber(@PathVariable int roomNumber, Model model) {
		Room room = roomService.getRoomByRoomNumber(roomNumber)
				.orElseThrow(() -> new IllegalArgumentException("Invalid room number: " + roomNumber));
		model.addAttribute("room", room);
		return "room-details";
	}
	
	@GetMapping("/new")
	public String showAddRoomForm(Model model) {
	    model.addAttribute("room", new Room());
	    return "room-form";
	}

	@PostMapping
	public String addRoom(Room room, BindingResult result, RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			return "room-form";
		}
		roomService.saveOrUpdateRoom(room);
		redirectAttributes.addFlashAttribute("successMessage", "Room added successfully");
		return "redirect:/rooms";
	}

	@GetMapping("/edit/{id}")
	public String showEditForm(@PathVariable Long id, Model model) {
		Room room = roomService.getRoomById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid room id: " + id));
		model.addAttribute("room", room);
		return "room-form";
	}

	@PostMapping("/edit/{id}")
	public String updateRoom(@PathVariable Long id, Room room, BindingResult result,
			RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			return "room-form";
		}
		room.setId(id);
		roomService.saveOrUpdateRoom(room);
		redirectAttributes.addFlashAttribute("successMessage", "Room updated successfully");
		return "redirect:/rooms";
	}
	
	@GetMapping("/delete/{id}")
    public String deleteRoom(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        roomService.deleteRoom(id);
        redirectAttributes.addFlashAttribute("successMessage", "Room deleted successfully");
        return "redirect:/rooms";
    }

}
