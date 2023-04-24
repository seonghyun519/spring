package com.web.data.controller;

import com.web.data.dto.PublicPetResponseDto;
import com.web.data.service.ApiDataService;
import com.web.data.service.PetInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;


@Slf4j
@Tag(name = "TEST용 유기동물 공공 API")
@RestController
@RequestMapping("/api/pets")
@RequiredArgsConstructor
public class ApiController {
    private final ApiDataService apiDataService;
    private final PetInfoService petInfoService;

    //    @SecurityRequirements
    @PostMapping("api-compare-data/{pageNo}")
    @Operation(summary = "공공데이터 유기동물API 호출 및 DB 비교 최신화", description = "state =NOTICE,PROTECT,END ")
    public String apiCompareData(@PathVariable(value = "pageNo") String pageNo,
                                 @RequestParam(value = "state") String state,
                                 @RequestParam(value = "size") String size) throws IOException {
        log.info("test");

        return apiDataService.apiCompareData(pageNo,state,size);
    }

    @GetMapping("/info-list")
    @Operation(summary = "유기동물 전체 정보 가져오기")
    public List<PublicPetResponseDto> getPublicPet(@RequestParam(value = "page") int page,
                                                   @RequestParam(value = "size") int size) {
        return petInfoService.getPublicPet(page - 1, size);
    }



}
