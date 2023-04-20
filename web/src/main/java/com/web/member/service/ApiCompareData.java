package com.web.member.service;

import com.web.common.entity.PetInfoByAPI;
import com.web.member.repository.PublicPetInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Log4j2
@Component
@RequiredArgsConstructor
public class ApiCompareData {

    private final PublicPetInfoRepository publicPetInfoRepository;
    private final ApiClient apiClient;
//    private final SseService sseService;
//    private final ScrapRepository scrapRepository;

    @Transactional
    protected void compareData(JSONArray itemList, String state) {
        for (int i = 0; i < itemList.length(); i++) {
            JSONObject itemObject = itemList.getJSONObject(i);
            Optional<PetInfoByAPI> petInfoByAPIOptional = publicPetInfoRepository.findByDesertionNo(itemObject.optLong("desertionNo"));
            List<String> compareDataList = new ArrayList<>();
            if (petInfoByAPIOptional.isEmpty()) {
                PetInfoByAPI petInfo = PetInfoByAPI.of(itemObject, state);
                publicPetInfoRepository.save(petInfo);
            } else {
                PetInfoByAPI petInfoByAPI = petInfoByAPIOptional.get();
                if (petInfoByAPI.getProcessState().contains("종료") && petInfoByAPI.getPetStateEnum().equals("END")){
                    continue;
                }
                if (!petInfoByAPI.getPetStateEnum().equals(state)) {
                    if (state.equals("END") && itemObject.optString("processState").contains("종료")) {
                        compareDataList.add("state");
                    }
                    if ((state.equals("PROTECT") || state.equals("NOTICE")) && itemObject.optString("processState").contains("보호")) {
                        compareDataList.add("state");
                    }
                    if (!compareDataList.isEmpty()) {
                        String compareDataKey = String.join(", ", compareDataList);
                        petInfoByAPI.update(itemObject, state);
                        publicPetInfoRepository.saveAndFlush(petInfoByAPI);
                        continue;
                    }
                }
                //entity 클래스 필드 전체를 조회하기 보다 하드코딩으로 비교하는게 비용적으로 유리하다고 판단되지만 일단 보류
                Field[] fields = petInfoByAPI.getClass().getDeclaredFields();
                for (Field field : fields) {
                    field.setAccessible(true);
                    String name = field.getName();
                    Object value = null;
                    try {
                        value = field.get(petInfoByAPI);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    if (itemObject.has(name)) {
                        if (name.equals("desertionNo")) {
                            String oldValue = value.toString();
                            if (!oldValue.equals(itemObject.optString(name))) {
                                compareDataList.add(name);
                                break;
                            }
                        } else {
                            if (!value.equals(itemObject.optString(name))) {
                                compareDataList.add(name);
                                break;
                            }
                        }
                    }
                }
                if (!compareDataList.isEmpty()) {
                    String compareDataKey = String.join(", ", compareDataList);
                    log.info(compareDataKey + "------------update 동작");
                    petInfoByAPI.update(itemObject, state);
                    publicPetInfoRepository.saveAndFlush(petInfoByAPI);
                }
            }
        }
    }
}
