package api;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

public class Message implements Serializable
{
    @Serial
    private static final long serialVersionUID = 1L;

    private final LocalDateTime timestamp;
    private final String body;

    public Message(String body) {
        this.timestamp = LocalDateTime.now();
        this.body = body;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getBody() {
        return body;
    }
}
