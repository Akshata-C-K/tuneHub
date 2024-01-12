package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.entities.Song;
import com.example.demo.services.SongService;

@Controller
public class SongController {
	@Autowired
	SongService service;
	@PostMapping("/addSong")
	public String addSong(@ModelAttribute Song song) {
		//service.addSong(song);
		boolean addSongStatus = service.songNameExists(song.getName());
		if(addSongStatus == false) {
		service.addSong(song);
		System.out.println("song added");
		}else {
			System.out.println("song already exists");
		}
		//return "home";
		return "admin";
	}
	@GetMapping("/viewSongs")
	public String viewSongs(Model model) {
		List <Song> songsList = service.fetchAllSongs();
		model.addAttribute("songs", songsList);
		
		return "displaySongs";
	}
	@GetMapping("/playSongs")
	public String playSongs(Model model) {
		boolean premiumUser = false;
		if(premiumUser==true) {
			List <Song> songsList = service.fetchAllSongs();
			model.addAttribute("songs", songsList);
			return "displaySongs";
		}
		else {
			return "makePayment";
		}
		
		
	}
	
}
