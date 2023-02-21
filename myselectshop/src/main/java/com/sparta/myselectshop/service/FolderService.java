package com.sparta.myselectshop.service;

import com.sparta.myselectshop.entity.Folder;
import com.sparta.myselectshop.entity.Product;
import com.sparta.myselectshop.entity.User;
import com.sparta.myselectshop.repository.FolderRepository;
import com.sparta.myselectshop.repository.ProductRepository;
import com.sparta.myselectshop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FolderService {

    private final ProductRepository productRepository;
    private final FolderRepository folderRepository;
    private final UserRepository userRepository;


    // 로그인한 회원에 폴더들 등록
    @Transactional
    public List<Folder> addFolders(List<String> folderNames, String name) { //Product Service나 다른 부분에서는 User객체를 UserDetailsImple에 들어있던 여기서는 이름을 가져오고 
        // User를 따로 조회 해서 가지고 왔음. 컨트롤러에서는 UserDetails.getUserName();으로 보냄 컨트롤러에서 콘솔 많이 찍어볼 수 있게함 //JPA 영속성 Context,fetch type의 지연로딩,즉시로딩
        //의 부분과 연관되어있음 //프록시 객체 이부분은 확인 필요 //user 객체로 받아와서 실행 해볼것 다음 어떻게 해결할지 생각해보기 //해결 못하더라도 이유가 먼지 이해하면 jpa를 이해했다라고 볼 수 있음

        User user = userRepository.findByUsername(name).orElseThrow(
                () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
        );

        // 입력으로 들어온 폴더 이름을 기준으로, 회원이 이미 생성한 폴더들을 조회합니다.
        List<Folder> existFolderList = folderRepository.findAllByUserAndNameIn(user, folderNames);

        List<Folder> folderList = new ArrayList<>();

        for (String folderName : folderNames) {
            // 이미 생성한 폴더가 아닌 경우만 폴더 생성
            if (!isExistFolderName(folderName, existFolderList)) {
                Folder folder = new Folder(folderName, user);
                folderList.add(folder);
            }
        }

        return folderRepository.saveAll(folderList);
    }

    // 로그인한 회원이 등록된 모든 폴더 조회
    @Transactional(readOnly = true)
    public List<Folder> getFolders(User user) {
        return folderRepository.findAllByUser(user);
    }

    @Transactional(readOnly = true)
    public Page<Product> getProductsInFolder(Long folderId, int page, int size, String sortBy, boolean isAsc, User user) {

        // 페이징 처리
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);


        return productRepository.findAllByUserIdAndFolderList_Id(user.getId(), folderId, pageable);
    }

    private boolean isExistFolderName(String folderName, List<Folder> existFolderList) {
        // 기존 폴더 리스트에서 folder name 이 있는지?
        for (Folder existFolder : existFolderList) {
            if (existFolder.getName().equals(folderName)) {
                return true;
            }
        }

        return false;
    }

}