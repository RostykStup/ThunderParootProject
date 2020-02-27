package rostyk.stupnytskiy.zepka.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "cart")
    private User user;


    @OneToMany(mappedBy = "cart", cascade = CascadeType.DETACH)
    private Set<PublicationForPurchase> publicationsForPurchase;
}
