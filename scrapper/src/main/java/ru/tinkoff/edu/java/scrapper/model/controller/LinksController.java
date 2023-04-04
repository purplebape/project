package ru.tinkoff.edu.java.scrapper.model.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/links")
@Validated
public class LinksController {

    @PostMapping("/{id}")
    public ResponseEntity<LinkResponse> createLink(@PathVariable Long id, @RequestBody AddLinkRequest request) {
        LinkResponse response = new LinkResponse(1L, "cool.url.com/path");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ListLinksResponse> getAllLinks(@PathVariable Long id) {
        ListLinksResponse response = new ListLinksResponse(new ArrayList<>(), 0);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<LinkResponse> removeLink(@PathVariable Long id, @RequestBody RemoveLinkRequest request) {
        LinkResponse response = new LinkResponse(1L, "cool.url.com/path");
        return ResponseEntity.ok(response);
    }
}