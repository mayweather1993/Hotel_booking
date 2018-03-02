package com.booking.models.domain;

import com.booking.models.Auditable;
import com.booking.models.RoomCategory;
import com.booking.repository.listeners.AuditListener;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "rooms", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"hotel_id", "category"})
})
@Data
@EqualsAndHashCode
@ToString
@EntityListeners(AuditListener.class)
@AllArgsConstructor
@NoArgsConstructor
public class RoomEntity implements Serializable, Auditable {

    private static final long serialVersionUID = 2041470056719491563L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Double price;
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "hotel_id", nullable = false)
    private HotelEntity hotel;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoomCategory category;
    @CreatedDate
    private Instant createdDate;
    @LastModifiedDate
    private Instant lastModifiedDate;
    @ManyToMany(mappedBy = "rooms")
    private List<BookingEntity> bookings;
    @ManyToMany(cascade = CascadeType.REMOVE)
    @JoinTable(
            name = "rooms_options",
            joinColumns = {@JoinColumn(name = "room_id")},
            inverseJoinColumns = {@JoinColumn(name = "option_id")}
    )
    private List<AdditionalOptionsEntity> additionalOptionEntities = new ArrayList<>();
}
