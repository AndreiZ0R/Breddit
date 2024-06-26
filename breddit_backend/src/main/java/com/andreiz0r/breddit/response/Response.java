package com.andreiz0r.breddit.response;

import com.andreiz0r.breddit.utils.AppUtils;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Getter
@EqualsAndHashCode(callSuper = false)
@ToString
public class Response extends ResponseEntity<Object> implements Serializable {
    private final Map<String, Object> body;
    private final String message;
    private final HttpStatus status;
    private final HttpHeaders headers;

    public Response(final Builder builder) {
        super(builder.body, builder.headers, builder.status);

        this.message = builder.message;
        this.status = builder.status;
        this.headers = builder.headers;
        this.body = builder.body;
    }

    public static class Builder {
        private String message;
        private HttpStatus status;
        private final Map<String, Object> body = new HashMap<>();
        private final HttpHeaders headers = new HttpHeaders();

        public Builder withMessage(final String message) {
            this.message = message;
            return this;
        }

        public Builder withStatus(final HttpStatus status) {
            this.status = status;
            return this;
        }

        public Builder withField(final String fieldName, final Object field) {
            body.put(fieldName, field);
            return this;
        }

        public Builder withMultipleFields(final Map<String, Object> fields) {
            body.putAll(fields);
            return this;
        }

        public Builder withBody(final Object body) {
            this.body.put(AppUtils.PAYLOAD, body);
            return this;
        }

        public Builder withHeader(final String headerName, final String headerValue) {
            headers.set(headerName, headerValue);
            return this;
        }

        public Builder withHeaders(final HttpHeaders headers) {
            this.headers.putAll(headers);
            return this;
        }

        public Response build() {
            body.put(AppUtils.MESSAGE, message);
            body.put(AppUtils.STATUS, status);
            headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

            return new Response(this);
        }
    }
}

