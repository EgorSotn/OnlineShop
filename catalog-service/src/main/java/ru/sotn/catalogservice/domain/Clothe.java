package ru.sotn.catalogservice.domain;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "clothes",uniqueConstraints = @UniqueConstraint(columnNames = {"name_cloth", "color_cloth" }))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Clothe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cloth")
    private Long id;

    @Column(name = "name_cloth")
    private String nameCloth;

    @Column(name = "color_cloth")
    private String colorCloth;

    @Column(name = "quantity_cloth")
    private Long quantity;

    @Column(name = "price_cloth")
    private String price;

    @Fetch(FetchMode.JOIN)
    @ManyToMany(targetEntity = Size.class, cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinTable(name = "clothe_sizes", joinColumns = @JoinColumn(name = "id_cloth"),
            inverseJoinColumns = @JoinColumn(name = "id_size"))
    private Set<Size> sizes = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "clothe")
    private List<Image> images = new ArrayList<>();

    @OneToOne(fetch = FetchType.EAGER)
    private Description description;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    public Clothe(String nameCloth, String colorCloth, Long quantity, String price, Set<Size> sizes, List<Image> images, Description description, Gender gender) {
        this.nameCloth = nameCloth;
        this.colorCloth = colorCloth;
        this.quantity = quantity;
        this.price = price;
        this.sizes = sizes;
        this.images = images;
        this.description = description;
        this.gender = gender;
    }
}
