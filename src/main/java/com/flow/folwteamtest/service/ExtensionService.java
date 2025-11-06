package com.flow.folwteamtest.service;

import com.flow.folwteamtest.common.ExtensionConstants;
import com.flow.folwteamtest.dto.CustomExtensionDto;
import com.flow.folwteamtest.dto.FixedExtensionDto;
import com.flow.folwteamtest.entity.CustomExtension;
import com.flow.folwteamtest.entity.FixedExtension;
import com.flow.folwteamtest.exception.DuplicateExtensionException;
import com.flow.folwteamtest.exception.ExtensionLimitExceededException;
import com.flow.folwteamtest.exception.ExtensionNotFoundException;
import com.flow.folwteamtest.repository.CustomExtensionRepository;
import com.flow.folwteamtest.repository.FixedExtensionRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ExtensionService {

    private final FixedExtensionRepository fixedExtensionRepository;
    private final CustomExtensionRepository customExtensionRepository;

    /**
     * 애플리케이션 시작 시 고정 확장자 초기화
     */
    @PostConstruct
    @Transactional
    public void initializeFixedExtensions() {
        for (String name : ExtensionConstants.FIXED_EXTENSION_NAMES) {
            if (fixedExtensionRepository.findByName(name).isEmpty()) {
                FixedExtension extension = FixedExtension.builder()
                        .name(name)
                        .blocked(false)
                        .build();
                fixedExtensionRepository.save(extension);
                log.info("Initialized fixed extension: {}", name);
            }
        }
    }

    /**
     * 고정 확장자 전체 조회
     */
    public List<FixedExtensionDto> getAllFixedExtensions() {
        return fixedExtensionRepository.findAll().stream()
                .map(FixedExtensionDto::from)
                .collect(Collectors.toList());
    }

    /**
     * 고정 확장자 차단 여부 업데이트 (ID 기반)
     */
    @Transactional
    public FixedExtensionDto updateFixedExtension(Long id, boolean blocked) {
        FixedExtension extension = fixedExtensionRepository.findById(id)
                .orElseThrow(() -> new ExtensionNotFoundException("고정 확장자를 찾을 수 없습니다. ID: " + id));

        extension.updateBlocked(blocked);
        log.info("Updated fixed extension: {} - blocked: {}", extension.getName(), blocked);

        return FixedExtensionDto.from(extension);
    }

    /**
     * 고정 확장자 차단 여부 업데이트 (이름 기반)
     */
    @Transactional
    public FixedExtensionDto updateFixedExtensionByName(String name, boolean blocked) {
        FixedExtension extension = fixedExtensionRepository.findByName(name)
                .orElseThrow(() -> new ExtensionNotFoundException("고정 확장자를 찾을 수 없습니다: " + name));

        extension.updateBlocked(blocked);
        log.info("Updated fixed extension by name: {} - blocked: {}", name, blocked);

        return FixedExtensionDto.from(extension);
    }

    /**
     * 커스텀 확장자 전체 조회
     */
    public List<CustomExtensionDto> getAllCustomExtensions() {
        return customExtensionRepository.findAll().stream()
                .map(CustomExtensionDto::from)
                .collect(Collectors.toList());
    }

    /**
     * 커스텀 확장자 추가
     */
    @Transactional
    public CustomExtensionDto addCustomExtension(String extensionName) {
        // 입력값 정제 (소문자 변환, 공백 제거, '.' 제거)
        String cleanedExtension = extensionName.toLowerCase().trim().replace(".", "");

        // 중복 체크
        if (customExtensionRepository.existsByExtension(cleanedExtension)) {
            throw new DuplicateExtensionException("이미 등록된 확장자입니다: " + cleanedExtension);
        }

        // 최대 개수 체크
        long count = customExtensionRepository.count();
        if (count >= ExtensionConstants.MAX_CUSTOM_EXTENSIONS) {
            throw new ExtensionLimitExceededException(ExtensionConstants.ERROR_EXTENSION_LIMIT_EXCEEDED);
        }

        CustomExtension extension = CustomExtension.builder()
                .extension(cleanedExtension)
                .build();

        CustomExtension saved = customExtensionRepository.save(extension);
        log.info("Added custom extension: {} (current count: {})", cleanedExtension, count + 1);

        return CustomExtensionDto.from(saved);
    }

    /**
     * 커스텀 확장자 삭제 (ID 기반)
     */
    @Transactional
    public void deleteCustomExtension(Long id) {
        if (!customExtensionRepository.existsById(id)) {
            throw new ExtensionNotFoundException("커스텀 확장자를 찾을 수 없습니다. ID: " + id);
        }

        customExtensionRepository.deleteById(id);
        log.info("Deleted custom extension: ID {}", id);
    }

    /**
     * 커스텀 확장자 삭제 (이름 기반)
     */
    @Transactional
    public void deleteCustomExtensionByName(String extensionName) {
        String cleanedExtension = extensionName.toLowerCase().trim();
        CustomExtension extension = customExtensionRepository.findByExtension(cleanedExtension)
                .orElseThrow(() -> new ExtensionNotFoundException("커스텀 확장자를 찾을 수 없습니다: " + cleanedExtension));

        customExtensionRepository.delete(extension);
        log.info("Deleted custom extension by name: {}", cleanedExtension);
    }

    /**
     * 커스텀 확장자 개수 조회
     */
    public long getCustomExtensionCount() {
        return customExtensionRepository.count();
    }
}
