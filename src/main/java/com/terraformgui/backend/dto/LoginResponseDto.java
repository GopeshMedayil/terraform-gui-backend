package com.terraformgui.backend.dto;

public record LoginResponseDto(
        String accessToken,
        String refreshToken
) {}
