package com.Gruppe10.RoboRallyServer;

import com.Gruppe10.RoboRallyServer.Model.BoardTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class test {

    @Autowired

    @GetMapping("/boardTemplate")
    public ResponseEntity<BoardTemplate> getBoard()
    {
        return ResponseEntity.ok().body(new BoardTemplate());
    }
}
