package io.github.aliabbosashurov.open.api;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.util.List;

/// Configuration properties for Open API security.
/// Contains the list of open endpoints loaded from YAML or properties.
///
/// @author Aliabbos Ashurov
@ConfigurationProperties(prefix = "app.security.api")
public record OpenApiProperties(
        /// Whether Open API support is enabled in the application. Default is true.
        @DefaultValue("true") boolean enabled,

        /// List of endpoints that are accessible without authentication. Default is empty list.
        @NonNull List<OpenEndpoint> openEndpoints
) {

    /**
     * Represents a single open (public) API endpoint configuration.
     */
    public record OpenEndpoint(
            /// Path pattern of the endpoint. Supports wildcards.
            @NonNull String path,

            /// HTTP methods allowed for this endpoint. Default is "ALL".
            @DefaultValue("ALL") @NonNull List<String> methods,

            /// Optional description of the endpoint for documentation purposes.
            @Nullable String description,

            /// Whether this endpoint is enabled. Default is true.
            @DefaultValue("true") boolean enabled
    ) {}
}
