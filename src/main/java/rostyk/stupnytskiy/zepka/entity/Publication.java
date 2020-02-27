package rostyk.stupnytskiy.zepka.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
public class Publication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @ManyToOne
//    private User user;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "text")
    private String description;

    private String mainImageName;

//    @Size(max = 5)
    @ElementCollection
    private List<String> images = new ArrayList<>();

    private LocalDateTime creationDate;

    private Integer count;

    @OneToMany(mappedBy = "publication")
    private List<PublicationForPurchase> purchases;

//    @OneToMany(mappedBy = "publication")
//    private List<Order> order;

    @ManyToOne(cascade = CascadeType.DETACH)
    private Subcategory subcategory;

    @ManyToOne(cascade = CascadeType.DETACH)
    private Type type;

    @ManyToOne(cascade = CascadeType.DETACH)
    private Delivery delivery;

    @ManyToOne(cascade = CascadeType.DETACH)
    private User user;
}
