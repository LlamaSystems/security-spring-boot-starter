package io.github.aliabbosashurov.open.api;

import org.jspecify.annotations.NonNull;
import org.springframework.http.HttpMethod;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

/// Utility class for creating [RequestMatcher] instances for API endpoints.
///
/// @author Aliabbos Ashurov
public final class ApiRequest {

    private ApiRequest() {
    }

    /// Matches no request
    public static final RequestMatcher EMPTY_MATCHER = request -> false;

    /// Returns an array matching all endpoints "/**".
    ///
    /// @return all endpoint patterns
    @NonNull
    public static String[] toAnyEndpoints() {
        return new String[]{ "/**" };
    }

    /// Matches endpoints configured as open (public) dynamically from [OpenApiProperties].
    ///
    /// @param properties Supplier of [OpenApiProperties] for runtime configuration
    /// @return a RequestMatcher that matches all enabled open endpoints
    @NonNull
    public static RequestMatcher toOpenEndpoints(Supplier<OpenApiProperties> properties) {
        List<OpenApiProperties.OpenEndpoint> endpoints = properties.get().openEndpoints();

        if (endpoints.isEmpty()) return EMPTY_MATCHER;

        List<RequestMatcher> matchers = endpoints.stream()
                .filter(OpenApiProperties.OpenEndpoint::enabled)
                .flatMap(endpoint -> {
                    List<String> methods = endpoint.methods();
                    if (methods.isEmpty() || methods.contains("ALL")) {
                        return Stream.<RequestMatcher>of(path(endpoint.path()));
                    } else {
                        return methods.stream()
                                .map(method -> path(endpoint.path(), HttpMethod.valueOf(method)));
                    }
                })
                .toList();

        return matchers.isEmpty() ? EMPTY_MATCHER : new OrRequestMatcher(matchers);
    }

    /// Matches specific paths.
    ///
    /// @param paths the path patterns to match
    /// @return a RequestMatcher for the specified paths
    @NonNull
    public static RequestMatcher to(String... paths) {
        if (paths == null || paths.length == 0) return EMPTY_MATCHER;
        RequestMatcher[] matchers = Stream.of(paths)
                .map(ApiRequest::path)
                .toArray(RequestMatcher[]::new);
        return new OrRequestMatcher(matchers);
    }

    /// Helper for path without method
    private static @NonNull PathPatternRequestMatcher path(String path) {
        return PathPatternRequestMatcher.withDefaults().matcher(path);
    }

    /// Helper for path with method
    private static @NonNull PathPatternRequestMatcher path(String path, HttpMethod method) {
        return PathPatternRequestMatcher.withDefaults().matcher(method, path);
    }
}