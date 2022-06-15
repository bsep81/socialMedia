package pl.sda.socialmedia.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Friendship {


    private Long id;
    @NotNull
    @Size(min = 2, max = 2)
    private List<User> friends;
    @NotNull
    private String status;
}
