package com.example.oca42.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "TestControllerClient", url = "http://localhost:9090")
public interface TestControllerClient {

    @GetMapping("/api/store")
    TestResponseDto getPosts();

}