package com.flow.folwteamtest.dto;

import com.flow.folwteamtest.common.ExtensionConstants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CustomExtensionCreateRequest {

    @NotBlank(message = "확장자는 필수입니다.")
    @Size(max = ExtensionConstants.EXTENSION_MAX_LENGTH, message = "확장자는 최대 " + ExtensionConstants.EXTENSION_MAX_LENGTH + "자까지 입력 가능합니다.")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "확장자는 영문자와 숫자만 입력 가능합니다.")
    private String extension;
}
