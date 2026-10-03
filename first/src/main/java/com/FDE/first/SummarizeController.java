package com.FDE.first;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class SummarizeController {


    SummarizeService summarizeService;

    public SummarizeController (SummarizeService summarizeService)
    {
        this.summarizeService = summarizeService;
    }

    @PostMapping("/chat")
    public String summarize(@RequestBody  String message ) {
        return summarizeService.chat(message);
    }
}
