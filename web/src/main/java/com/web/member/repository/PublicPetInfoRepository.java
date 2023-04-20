package com.web.member.repository;

import com.web.common.entity.PetInfoByAPI;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PublicPetInfoRepository extends JpaRepository<PetInfoByAPI, Long> {
    Optional<PetInfoByAPI> findByDesertionNo(Long desertionNo);

    Optional<PetInfoByAPI> findById(Long Id);
}
