package com.young.shortenerurl.url.presentation.dto;

import com.young.shortenerurl.url.model.EncodingType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UrlCreateApiRequest(
        @NotBlank(message = "originUrl은 빈값이 들어올 수 없습니다.") String originUrl,
        @NotNull(message = "encodingType은 null이 들어올 수 없습니다.") EncodingType encodingType) {
}
