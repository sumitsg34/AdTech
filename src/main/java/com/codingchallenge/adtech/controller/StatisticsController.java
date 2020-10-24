package com.codingchallenge.adtech.controller;

import com.codingchallenge.adtech.exception.InvalidInputException;
import com.codingchallenge.adtech.exception.PlatformException;
import com.codingchallenge.adtech.model.OverallStatisticsResponse;
import com.codingchallenge.adtech.model.StatisticsResponse;
import com.codingchallenge.adtech.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
public class StatisticsController {

    @Autowired
    private StatisticService statisticService;

    @GetMapping("/statistics/time/{start}/{end}/overall")
    public ResponseEntity<OverallStatisticsResponse> getOverallStatistics(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssZ") Date start,
                                                                          @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssZ") Date end) {
        try {
            OverallStatisticsResponse overallStatisticsResponse = statisticService.getOverallAdsStatistics(start, end);
            return new ResponseEntity(overallStatisticsResponse, HttpStatus.OK);
        } catch (InvalidInputException invalidInputException) {
            return new ResponseEntity(invalidInputException.getMessage(), HttpStatus.NOT_FOUND);
        } catch (PlatformException platformException) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/statistics/time/{start}/{end}")
    public ResponseEntity<StatisticsResponse> getStatisticByCategories(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssZ") Date start,
                                                                              @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssZ") Date end,
                                                                              @RequestBody List<String> categories) {
        try {
            StatisticsResponse statisticsResponse = statisticService.getAdsStatistics(start, end, categories);
            return new ResponseEntity(statisticsResponse, HttpStatus.OK);
        } catch (InvalidInputException invalidInputException) {
            return new ResponseEntity(invalidInputException.getMessage(), HttpStatus.NOT_FOUND);
        } catch (PlatformException platformException) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
