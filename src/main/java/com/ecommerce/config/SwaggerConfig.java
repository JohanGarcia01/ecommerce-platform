package com.ecommerce.config;

import com.ecommerce.exception.ApiError;
import io.swagger.v3.core.converter.AnnotatedType;
import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.core.converter.ResolvedSchema;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.responses.ApiResponse;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenApi() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("E-Commerce API")
                                .version("1.0")
                                .description("API documentation for the E-Commerce platform."));
    }

    @Bean
    public OpenApiCustomizer openApiCustomizer() {
        ResolvedSchema errorSchema =
                ModelConverters.getInstance().resolveAsResolvedSchema(new AnnotatedType(ApiError.class));
        Content content =
                new Content().addMediaType("application/json", new MediaType().schema(errorSchema.schema));

        return openApi ->
                openApi
                        .getPaths()
                        .values()
                        .forEach(
                                pathItem ->
                                        pathItem
                                                .readOperations()
                                                .forEach(
                                                        operation ->
                                                                operation
                                                                        .getResponses()
                                                                        .addApiResponse(
                                                                                "400",
                                                                                new ApiResponse()
                                                                                        .description(
                                                                                                "Bad Request: The request could not be processed due to invalid syntax.")
                                                                                        .content(content))
                                                                        .addApiResponse(
                                                                                "401",
                                                                                new ApiResponse()
                                                                                        .description(
                                                                                                "Unauthorized: Authentication is required to access this resource.")
                                                                                        .content(content))
                                                                        .addApiResponse(
                                                                                "403",
                                                                                new ApiResponse()
                                                                                        .description(
                                                                                                "Forbidden: The user does not have the necessary permissions.")
                                                                                        .content(content))
                                                                        .addApiResponse(
                                                                                "404",
                                                                                new ApiResponse()
                                                                                        .description(
                                                                                                "Not Found: The requested resource could not be found.")
                                                                                        .content(content))
                                                                        .addApiResponse(
                                                                                "500",
                                                                                new ApiResponse()
                                                                                        .description(
                                                                                                "Internal Server Error: An error occurred on the server.")
                                                                                        .content(content))));
    }
}
