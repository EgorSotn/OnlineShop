package ru.sotn.catalogservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "images")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idImage;

    @Column(name = "url_image")
    private String url;

    @ManyToOne
    @JoinColumn(name = "clothe_id")
    @JsonIgnore
    private Clothe clothe;

    public Image(String url, Clothe clothe) {
        this.url = url;
        this.clothe = clothe;
    }

    public Image(String url) {
        this.url = url;
    }
}
