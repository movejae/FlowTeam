package com.flow.folwteamtest.controller;

import com.flow.folwteamtest.dto.CustomExtensionCreateRequest;
import com.flow.folwteamtest.dto.CustomExtensionDto;
import com.flow.folwteamtest.dto.FixedExtensionDto;
import com.flow.folwteamtest.dto.FixedExtensionUpdateRequest;
import com.flow.folwteamtest.service.ExtensionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/extensions")
@RequiredArgsConstructor
public class ExtensionApiController {

    private final ExtensionService extensionService;

    /**
     * 고정 확장자 전체 조회
     * GET /api/extensions/fixed
     */
    @GetMapping("/fixed")
    public ResponseEntity<List<FixedExtensionDto>> getFixedExtensions() {
        log.info("Request to get all fixed extensions");
        List<FixedExtensionDto> extensions = extensionService.getAllFixedExtensions();
        return ResponseEntity.ok(extensions);
    }

    /**
     * 고정 확장자 차단 설정 변경 (ID 기반)
     * PUT /api/extensions/fixed/{id}
     */
    @PutMapping("/fixed/{id}")
    public ResponseEntity<FixedExtensionDto> updateFixedExtension(
            @PathVariable Long id,
            @Valid @RequestBody FixedExtensionUpdateRequest request) {
        log.info("Request to update fixed extension: ID={}, blocked={}", id, request.getBlocked());
        FixedExtensionDto updated = extensionService.updateFixedExtension(id, request.getBlocked());
        return ResponseEntity.ok(updated);
    }

    /**
     * 고정 확장자 차단 설정 변경 (이름 기반)
     * PATCH /api/extensions/fixed/{name}
     */
    @PatchMapping("/fixed/{name}")
    public ResponseEntity<FixedExtensionDto> updateFixedExtensionByName(
            @PathVariable String name,
            @Valid @RequestBody FixedExtensionUpdateRequest request) {
        log.info("Request to update fixed extension by name: name={}, blocked={}", name, request.getBlocked());
        FixedExtensionDto updated = extensionService.updateFixedExtensionByName(name, request.getBlocked());
        return ResponseEntity.ok(updated);
    }

    /**
     * 커스텀 확장자 전체 조회
     * GET /api/extensions/custom
     */
    @GetMapping("/custom")
    public ResponseEntity<List<CustomExtensionDto>> getCustomExtensions() {
        log.info("Request to get all custom extensions");
        List<CustomExtensionDto> extensions = extensionService.getAllCustomExtensions();
        return ResponseEntity.ok(extensions);
    }

    /**
     * 커스텀 확장자 추가
     * POST /api/extensions/custom
     */
    @PostMapping("/custom")
    public ResponseEntity<CustomExtensionDto> addCustomExtension(
            @Valid @RequestBody CustomExtensionCreateRequest request) {
        log.info("Request to add custom extension: extension={}", request.getExtension());
        CustomExtensionDto created = extensionService.addCustomExtension(request.getExtension());
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * 커스텀 확장자 삭제 (ID 기반)
     * DELETE /api/extensions/custom/{id}
     */
    @DeleteMapping("/custom/id/{id}")
    public ResponseEntity<Void> deleteCustomExtension(@PathVariable Long id) {
        log.info("Request to delete custom extension: ID={}", id);
        extensionService.deleteCustomExtension(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * 커스텀 확장자 삭제 (이름 기반)
     * DELETE /api/extensions/custom/{extension}
     */
    @DeleteMapping("/custom/{extension}")
    public ResponseEntity<Void> deleteCustomExtensionByName(@PathVariable String extension) {
        log.info("Request to delete custom extension by name: extension={}", extension);
        extensionService.deleteCustomExtensionByName(extension);
        return ResponseEntity.noContent().build();
    }

    /**
     * 커스텀 확장자 개수 조회
     * GET /api/extensions/custom/count
     */
    @GetMapping("/custom/count")
    public ResponseEntity<Long> getCustomExtensionCount() {
        log.info("Request to get custom extension count");
        long count = extensionService.getCustomExtensionCount();
        return ResponseEntity.ok(count);
    }
}
