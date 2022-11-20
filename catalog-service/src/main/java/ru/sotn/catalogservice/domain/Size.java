package ru.sotn.catalogservice.domain;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sizes")
public class Size {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_size")
    private Long idSize;

    @Column(name = "eur_size")
    private String eurSize;

    @Column(name = "ru_size")
    private String ruSize;

    @Column(name = "world_size")
    private String worldSize;



    public Size(String eurSize, String ruSize, String worldSize) {
        this.eurSize = eurSize;
        this.ruSize = ruSize;
        this.worldSize = worldSize;
    }
}
