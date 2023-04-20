package com.web.member.controller;

import com.web.member.service.ApiDataService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@Slf4j
@Tag(name = "TEST용 유기동물 공공 API")
@RestController
@RequestMapping("/api/pets")
@RequiredArgsConstructor
public class ApiController {
    private final ApiDataService apiDataService;

    //    @SecurityRequirements
    @PostMapping("api-compare-data/{pageNo}")
    @Operation(summary = "공공데이터 유기동물API 호출 및 DB 비교 최신화", description = "자세한 설명")
    public String apiCompareData(@PathVariable(value = "pageNo") String pageNo,
                                 @RequestParam(value = "state") String state,
                                 @RequestParam(value = "size") String size) throws IOException {
        log.info("test");

        return apiDataService.apiCompareData(pageNo,state,size);
    }



}
