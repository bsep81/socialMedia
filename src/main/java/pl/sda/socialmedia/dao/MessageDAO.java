package pl.sda.socialmedia.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageDAO {

    private Long id;
    private String content;
    private LocalDateTime publishedOn;
    private String username;
}
