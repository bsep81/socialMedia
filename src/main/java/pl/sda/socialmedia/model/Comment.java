package pl.sda.socialmedia.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment{

    private Long id;
    private String content;
    private LocalDateTime publishedOn;
    private User author;
    private Message commentedMessage;
}
