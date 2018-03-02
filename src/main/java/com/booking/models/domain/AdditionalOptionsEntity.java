package com.booking.models.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "additional_options")
@Data
@EqualsAndHashCode
@ToString
@NoArgsConstructor
public class AdditionalOptionsEntity implements Serializable {
    private static final long serialVersionUID = 950738396567604666L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private Double price;

    public AdditionalOptionsEntity(final String title, final Double price) {
        this.title = title;
        this.price = price;
    }
}
