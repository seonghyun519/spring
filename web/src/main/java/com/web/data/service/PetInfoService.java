package com.web.data.service;

import com.web.common.entity.PetInfoByAPI;
import com.web.data.dto.PublicPetResponseDto;
import com.web.data.repository.PublicPetInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PetInfoService {

    private final PublicPetInfoRepository publicPetInfoRepository;
    @Transactional(readOnly = true)
    public List<PublicPetResponseDto> getPublicPet(int page, int size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "happenDt", "desertionNo");
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<PetInfoByAPI> postPage = publicPetInfoRepository.findAll(pageable);
        List<PublicPetResponseDto> dtoList = new ArrayList<>();

        for (PetInfoByAPI petInfoByAPI : postPage) {
            PublicPetResponseDto responseDto = PublicPetResponseDto.of(petInfoByAPI);
            dtoList.add(responseDto);
        }
        return dtoList;
    }
}
