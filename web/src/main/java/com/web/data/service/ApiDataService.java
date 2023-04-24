package com.web.data.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Service
public class ApiDataService {
    private final ApiClient apiClient;
    private final ApiCompareData apiCompareData;
    public String apiCompareData(String pageNo, String state, String size) throws IOException {
        String apiUrl = apiClient.createPublicDataApiUrl(pageNo, state, size);
        JSONArray itemList = apiClient.fetchDataFromApi(apiUrl);

        if (itemList != null) {
            apiCompareData.compareData(itemList, state);
        } else {
            log.error("API로부터 데이터를 받아오지 못했습니다.");
        }
        log.info("완료");
        return "성공";
    }
}