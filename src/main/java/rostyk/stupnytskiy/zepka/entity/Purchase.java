package rostyk.stupnytskiy.zepka.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean finished;

    private Boolean sent;

    private Boolean cancelled;

    private LocalDateTime creationDate;

    @OneToMany
    private Set<PublicationForPurchase> publicationsForPurchase;

    private Double price;

    @ManyToOne
    private Address address;

    @OneToOne(cascade = CascadeType.ALL)
    private Order order;

    @ManyToOne
    private User user;

}
