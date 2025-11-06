package com.flow.folwteamtest.dto;

import com.flow.folwteamtest.entity.CustomExtension;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class CustomExtensionDto {
    private Long id;
    private String extension;
    private LocalDateTime createdAt;

    public static CustomExtensionDto from(CustomExtension entity) {
        return CustomExtensionDto.builder()
                .id(entity.getId())
                .extension(entity.getExtension())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}
