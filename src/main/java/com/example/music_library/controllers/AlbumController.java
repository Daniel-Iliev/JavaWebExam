/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.music_library.controllers;

import com.example.music_library.models.Album;
import com.example.music_library.repositories.AlbumRepository;
import com.example.music_library.repositories.ArtistRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author IvanAnemi4niq
 */
@Controller
public class AlbumController {

    @Autowired
    AlbumRepository albumRepository;
    
    @Autowired
    ArtistRepository artistRepository;

    @GetMapping("/albums")
    public String index(Model model) {
        model.addAttribute("albums", albumRepository.findAll());
        return "album-index";
    }
    
    @GetMapping("/addalbumform")
    public String showAddForm(Album album,Model model) {
        model.addAttribute("artists", artistRepository.findAll());
        return "add-album";
    }
    
    @PostMapping("/addalbum")
    public String addAlbum(@Valid Album album, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-album";
        }

        albumRepository.save(album);
        model.addAttribute("albums", albumRepository.findAll());
        return "redirect:/albums";
    }
    @GetMapping("/editalbum/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        Album album = albumRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid album Id:" + id));

        model.addAttribute("album", album);
        model.addAttribute("artists", artistRepository.findAll());
        return "update-album";
    }

    @PostMapping("/updatealbum/{id}")
    public String updateAlbum(@PathVariable("id") Long id, @Valid Album album,
            BindingResult result, Model model) {
        if (result.hasErrors()) {
            album.setId(id);
            return "update-album";
        }

        albumRepository.save(album);
        model.addAttribute("album", albumRepository.findAll());
        return "redirect:/albums";
    }

    @GetMapping("/deletealbum/{id}")
    public String deleteAlbum(@PathVariable("id") Long id, Model model) {
        Album album = albumRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid album Id:" + id));
        albumRepository.delete(album);
        model.addAttribute("album", albumRepository.findAll());
        return "redirect:/albums";
        }
    
    @GetMapping("/searchalbum")
    public String showAlbumsByName(@RequestParam (name = "search", required = false) String name, Model model) {
        model.addAttribute("albums", albumRepository.findByNameStartingWith(name));
        return "album-index";
    }
    
  
}
