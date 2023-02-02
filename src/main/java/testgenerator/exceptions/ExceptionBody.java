package testgenerator.exceptions;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({
        "status",
        "message",
        "timestamp",
        "path"
})
@Schema(name = "ExceptionBody")
public class ExceptionBody {

    @JsonProperty(value = "status")
    @Schema(
            name = "status",
            description = "Status code of the exception",
            type = "string",
            example = "401"
    )
    private String status;

    @JsonProperty(value = "message")
    @Schema(
            name = "message",
            description = "Message of the exception",
            type = "string",
            example = "Invalid credentials"
    )
    private String message;

    @JsonProperty(value = "timestamp")
    @Schema(
            name = "timestamp",
            description = "Timestamp of the exception",
            type = "string",
            example = "2023-01-01T00:00:00"
    )
    private LocalDateTime timestamp;

    @JsonProperty(value = "path")
    @Schema(
            name = "path",
            description = "Path of the exception",
            type = "string",
            example = "/users/profile"
    )
    private String path;

}
