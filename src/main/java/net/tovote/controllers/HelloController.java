package net.tovote.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
public class HelloController {

    @RequestMapping("/")
    public String hello(){
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss z");
        Date date = new Date(System.currentTimeMillis());
        var time = formatter.format(date);
        return "Hello!" + "It's "+ time.toString()+ ".";
    }
}
