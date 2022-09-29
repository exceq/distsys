package ru.csu.iit.distsys.controllers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class HelloController {

    private final Environment env;

    @GetMapping("/hello")
    public String foo() {
        return "Hello world!";
    }

    @GetMapping("/server-info")
    public ServerInfo getServerInfo(@RequestParam(defaultValue = "HOST") String environmentVariable) {
        log.info("Request for \"" + environmentVariable + "\" env");
        return new ServerInfo(Arrays.asList(env.getActiveProfiles()), env.getProperty(environmentVariable));
    }

    @Data
    @AllArgsConstructor
    static class ServerInfo {
        List<String> activeProfiles;
        String environmentVariable;
    }
}
