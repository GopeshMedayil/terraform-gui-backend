package com.terraformgui.backend.dto.response;

public record LoginResponseDto(
        String accessToken,
        String refreshToken
) {}
