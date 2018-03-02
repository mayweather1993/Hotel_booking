package com.booking.models.domain;

import com.booking.models.Auditable;
import com.booking.repository.listeners.AuditListener;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

@Entity
@Table(name = "hotels")
@Data
@EqualsAndHashCode
@ToString
@EntityListeners(AuditListener.class)
@NoArgsConstructor
@AllArgsConstructor
public class HotelEntity implements Serializable, Auditable {
    private static final long serialVersionUID = -6022440187468223069L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String name;
    private int stars;
    @CreatedDate
    private Instant createdDate;
    @LastModifiedDate
    private Instant lastModifiedDate;
}
