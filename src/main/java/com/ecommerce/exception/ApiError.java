package com.ecommerce.exception;

import static com.ecommerce.utils.GlobalConstant.DATE_API_FORMAT_PATTERN;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder(setterPrefix = "with")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder({"message", "error_code", "timestamp"})
public record ApiError(
        @Schema(
                        description = "The code used to identify the current failure.",
                        example = "ECOMMERCE_0100")
                @JsonProperty("error_code")
                String errorCode,
        @Schema(
                        description = "Exact date when the error happened.",
                        example = "2024-04-25T09:44:05.544108")
                @JsonFormat(pattern = DATE_API_FORMAT_PATTERN)
                LocalDateTime timestamp,
        @Schema(
                        description = "The message that contains the detail or description of the error.",
                        example = "The Commerce's response body was invalid and could not be deserialized.")
                String message) {}
