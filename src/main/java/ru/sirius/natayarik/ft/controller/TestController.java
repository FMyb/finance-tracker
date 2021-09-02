package ru.sirius.natayarik.ft.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author Yaroslav Ilin
 */

@RestController
public class TestController {

    @GetMapping("/echo")
    @ResponseBody
    public String echo(@RequestParam(name = "s") String s) {
        return s;
    }
}
