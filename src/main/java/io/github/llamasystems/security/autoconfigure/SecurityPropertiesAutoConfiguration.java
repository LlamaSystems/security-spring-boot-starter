package io.github.llamasystems.security.autoconfigure;

import io.github.llamasystems.security.SecurityProperties;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/// Auto-configuration for [SecurityProperties].
///
/// @author Aliabbos Ashurov
/// @since v1.0.0
@AutoConfiguration
@EnableConfigurationProperties(SecurityProperties.class)
@ConditionalOnProperty(
        prefix = "app.security.api",
        name = "enabled",
        havingValue = "true",
        matchIfMissing = true)
public class SecurityPropertiesAutoConfiguration {
}
