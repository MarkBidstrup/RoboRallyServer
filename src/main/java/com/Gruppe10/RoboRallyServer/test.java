package com.Gruppe10.RoboRallyServer;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class test {
    testDTO testDTO = new testDTO(1,"Deniz");

    @GetMapping("/test")
    public testDTO test(){
        return testDTO;
    }
}
