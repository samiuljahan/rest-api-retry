package com.example.demorest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExampleController {

    @GetMapping("/example")
    public ResponseEntity<String> example(@RequestParam(required = false) String error) {
        if(error!=null && error.equals("500")) {
            return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if(error!=null && error.equals("400")) {
            return new ResponseEntity<>("Internal Server Error", HttpStatus.BAD_REQUEST);
        }
        if(error!=null && error.equals("403")) {
            return new ResponseEntity<>("Internal Server Error", HttpStatus.FORBIDDEN);
        }

        else {
            return new ResponseEntity<>("{\"example\":\"data\"}", HttpStatus.OK);
        }
    }
}

