package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.entities.Playlist;
import com.example.demo.entities.Song;
import com.example.demo.services.PlaylistService;
import com.example.demo.services.SongService;

@Controller
public class PlaylistController {
	@Autowired
	SongService service;
	
	@Autowired
	PlaylistService playlistservice;
	@GetMapping("/createPlaylist")
	public String createPlaylist(Model model) {
		List <Song> songsList = service.fetchAllSongs();
		model.addAttribute("songs", songsList);
		System.out.println("playlist added");
		return "createPlaylist";
	}
	@PostMapping("/addPlaylist")
	public String addPlaylist(@ModelAttribute Playlist playlist) {
		playlistservice.addPlaylist(playlist);
		List<Song> songList = playlist.getSongs();
		for(Song s : songList) {
			s.getPlaylists().add(playlist);
			//update song object in db
			service.updateSong(s);
		}
		return "admin";
	}
	@GetMapping("/viewPlaylist")
	public String viewPlaylist(Model model) {
		List <Playlist> allPlayLists = playlistservice.fetchAllPlaylists();
		model.addAttribute("allPlayLists", allPlayLists );
		
		return "displayPlaylist";
	}
	
}
