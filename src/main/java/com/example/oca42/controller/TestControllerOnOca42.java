package com.example.oca42.controller;

import com.example.oca42.client.TestControllerClient;
import com.example.oca42.client.TestResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/oca42")
@PreAuthorize("permitAll()")
@RequiredArgsConstructor
public class TestControllerOnOca42 {

    private final TestControllerClient client;

    @GetMapping
    public void getTest() {

        TestResponseDto posts = client.getPosts();

        System.out.println(posts);

    }
}
