package com.flow.folwteamtest.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FixedExtensionUpdateRequest {
    @NotNull(message = "차단 여부는 필수입니다.")
    private Boolean blocked;
}
