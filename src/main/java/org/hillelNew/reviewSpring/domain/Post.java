package org.hillelNew.reviewSpring.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(name = "nickname")
    private String nickname;

    @Column(name = "text", columnDefinition = "text")
    private String text;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created")
    private LocalDateTime created;

    @NonNull
    @Enumerated(EnumType.STRING)
    @Column(name = "rating")
    private Rating rating;

    public enum Rating {
        POSITIVE,
        NEGATIVE
    }
}
