package com.codingchallenge.adtech.controller;

import com.codingchallenge.adtech.exception.DataNotFoundException;
import com.codingchallenge.adtech.exception.PlatformException;
import com.codingchallenge.adtech.model.AdsEvent;
import com.codingchallenge.adtech.model.ClickEvent;
import com.codingchallenge.adtech.model.DeliveryEvent;
import com.codingchallenge.adtech.model.InstallEvent;
import com.codingchallenge.adtech.service.IngestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class IngestController {

    @Autowired
    private IngestService ingestService;

    @PostMapping
    public ResponseEntity<UUID> saveAd(@RequestBody AdsEvent event) {
        try {
            UUID adId = ingestService.save(event);
            return new ResponseEntity(adId, HttpStatus.OK);
        } catch (PlatformException platformException) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/delivery")
    public ResponseEntity saveDelivery(@RequestBody DeliveryEvent event) {
        try {
            ingestService.save(event);
            return new ResponseEntity(HttpStatus.OK);
        } catch (DataNotFoundException dataNotFoundException) {
            return new ResponseEntity(dataNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
        } catch (PlatformException platformException) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/click")
    public ResponseEntity saveClick(@RequestBody ClickEvent event) {
        try {
            ingestService.save(event);
            return new ResponseEntity(HttpStatus.OK);
        } catch (DataNotFoundException dataNotFoundException) {
            return new ResponseEntity(dataNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
        } catch (PlatformException platformException) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/install")
    public ResponseEntity saveInstall(@RequestBody InstallEvent event) {
        try {
            ingestService.save(event);
            return new ResponseEntity(HttpStatus.OK);
        } catch (DataNotFoundException dataNotFoundException) {
            return new ResponseEntity(dataNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
        } catch (PlatformException platformException) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
