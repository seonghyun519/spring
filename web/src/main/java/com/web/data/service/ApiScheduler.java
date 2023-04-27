package com.web.data.service;

import com.web.common.annotation.LogExecutionTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Service
public class ApiScheduler {
    private final ApiClient apiClient;
    private final ApiCompareData apiCompareData;

    @LogExecutionTime
    @Scheduled(cron = "58 24 04 * * *")
    protected void apiSchedule() throws IOException {
        log.info("apiSchedule 동작");
        String[] state = {"NOTICE", "PROTECT", "END"};
        int stateNo = 0;
        int pageNo = 0;
        String size = "1000";
        while (stateNo < 3) {
            pageNo++;
            String apiUrl = apiClient.createPublicDataApiUrl(String.valueOf(pageNo), state[stateNo], size);
            JSONArray itemList = apiClient.fetchDataFromApi(apiUrl);
            if (itemList == null || itemList.isEmpty()) {
                log.info("State: " + state[stateNo] + ",  pageNo: " + pageNo + "을 API로부터 데이터를 받아오지 못했습니다.-------------------------------------------------------------------------");
                stateNo++;
                pageNo = 0;
                continue;
            }
            apiCompareData.compareData(itemList, state[stateNo]);
        }
    }

}
