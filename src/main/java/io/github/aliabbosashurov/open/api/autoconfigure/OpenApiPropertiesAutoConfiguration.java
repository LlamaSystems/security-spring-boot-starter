package io.github.aliabbosashurov.open.api.autoconfigure;

import io.github.aliabbosashurov.open.api.OpenApiProperties;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/// Auto-configuration for [OpenApiProperties].
///
/// @author Aliabbos Ashurov
/// @since v1.0.0
@AutoConfiguration
@EnableConfigurationProperties(OpenApiProperties.class)
@ConditionalOnProperty(
        prefix = "app.security.api",
        name = "enabled",
        havingValue = "true",
        matchIfMissing = true)
public class OpenApiPropertiesAutoConfiguration {
}
