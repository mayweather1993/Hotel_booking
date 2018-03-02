package com.booking.models.vm;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;
import java.util.List;

@Data
@EqualsAndHashCode
@ToString
@NoArgsConstructor
public class BookingVM implements Serializable {
    private static final long serialVersionUID = 172164620239634775L;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    @NotNull
    private Instant from;
    @NotNull
    private Instant to;
    @NotNull
    private Long user;
    @NotEmpty
    private List<Long> roomIds;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Double price;
    @LastModifiedDate
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Instant lastModifiedDate;
    @CreatedDate
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Instant createdDate;

    public BookingVM(@NotNull Instant from, @NotNull Instant to, @NotNull Long user, @NotEmpty List<Long> roomIds) {
        this.from = from;
        this.to = to;
        this.user = user;
        this.roomIds = roomIds;
    }
}
