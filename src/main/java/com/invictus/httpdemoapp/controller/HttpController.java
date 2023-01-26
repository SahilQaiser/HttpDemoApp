package com.invictus.httpdemoapp.controller;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/http")
public class HttpController {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpController.class);

    @Qualifier("getHttpClientWithProxy")
    @Autowired
    private HttpClient httpClientWithProxy;

    @Qualifier("getHttpClientWithNoProxy")
    @Autowired
    private HttpClient httpClientWithNoProxy;


    @GetMapping(value = "/get", produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<String> getUrl(@RequestParam String url) {
        HttpGet httpGet = new HttpGet("http://" + url);
        LOGGER.info("URL : {}", httpGet.getURI());
        long startTime = System.currentTimeMillis();

        try {
            HttpResponse response;
            response = httpClientWithProxy.execute(httpGet);
            String responseString = EntityUtils.toString(response.getEntity());
            long endTime = System.currentTimeMillis();
            long timeTaken = endTime - startTime;
            LOGGER.info("Response: {}, Time Taken: {} ms", responseString, timeTaken);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.TEXT_HTML);
            response.headerIterator().forEachRemaining(header -> {
                Header hd = (Header)header;
                headers.set(hd.getName(), hd.getValue());
            });
            return new ResponseEntity<>(responseString, headers, HttpStatus.OK);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
