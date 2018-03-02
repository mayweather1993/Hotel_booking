package com.booking.models.domain;

import com.booking.models.Auditable;
import com.booking.repository.listeners.AuditListener;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "bookings")
@Data
@EqualsAndHashCode
@ToString
@EntityListeners(AuditListener.class)
public class BookingEntity implements Serializable, Auditable {
    private static final long serialVersionUID = 6817156061760574424L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Instant fromDate;
    @Column(nullable = false)
    private Instant toDate;
    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "rooms_bookings",
            joinColumns = {@JoinColumn(name = "id")},
            inverseJoinColumns = {@JoinColumn(name = "room_id")}
    )
    private List<RoomEntity> rooms;
    @LastModifiedDate
    private Instant lastModifiedDate;
    @CreatedDate
    private Instant createdDate;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;
    private Double price;
}
