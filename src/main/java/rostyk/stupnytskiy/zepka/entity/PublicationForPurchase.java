package rostyk.stupnytskiy.zepka.entity;

import lombok.*;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
public class PublicationForPurchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Publication publication;

    private Integer count;

    @ManyToOne(cascade = CascadeType.DETACH)
    private Cart cart;
}
