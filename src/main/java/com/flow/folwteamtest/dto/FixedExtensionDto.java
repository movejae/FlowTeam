package com.flow.folwteamtest.dto;

import com.flow.folwteamtest.entity.FixedExtension;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FixedExtensionDto {
    private Long id;
    private String name;
    private boolean blocked;

    public static FixedExtensionDto from(FixedExtension entity) {
        return FixedExtensionDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .blocked(entity.isBlocked())
                .build();
    }
}
