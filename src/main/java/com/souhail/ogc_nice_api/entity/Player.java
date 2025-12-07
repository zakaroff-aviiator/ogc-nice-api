package c.s.ogc_nice_api.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "player")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String position;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;
}
