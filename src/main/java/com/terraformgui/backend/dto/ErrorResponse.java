package com.terraformgui.backend.dto;

import java.time.LocalDateTime;

public record ErrorResponse(String message,
                            int status,
                            LocalDateTime timestamp) {
}
