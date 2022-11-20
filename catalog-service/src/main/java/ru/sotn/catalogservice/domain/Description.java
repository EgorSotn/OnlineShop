package ru.sotn.catalogservice.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "descriptions")
@NoArgsConstructor
@AllArgsConstructor
public class Description {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_description")
    Long idDescription;

    @Column(name = "textile")
    private String textile;

    @Column(name = "about_cloth")
    private String aboutCloth;

    public Description(String textile, String aboutCloth) {
        this.textile = textile;
        this.aboutCloth = aboutCloth;
    }
}
