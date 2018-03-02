package com.booking.models.domain;

import com.booking.models.Auditable;
import com.booking.repository.listeners.AuditListener;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@EqualsAndHashCode
@ToString
@EntityListeners(AuditListener.class)
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity implements Serializable, Auditable {
    private static final long serialVersionUID = -6022440187468223069L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    @Email
    private String email;
    @CreatedDate
    private Instant createdDate;
    @LastModifiedDate
    private Instant lastModifiedDate;
    @OneToMany(mappedBy = "user")
    private List<BookingEntity> bookings = new ArrayList<>();
}
