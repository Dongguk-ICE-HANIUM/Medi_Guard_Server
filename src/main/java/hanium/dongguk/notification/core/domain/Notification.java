package hanium.dongguk.notification.core.domain;

import hanium.dongguk.global.base.BaseTimeEntity;
import hanium.dongguk.user.core.domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "notifi_type")
@Table(name = "notification")
@Getter
@NoArgsConstructor
public abstract class Notification extends BaseTimeEntity {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name="uuid2", strategy = "uuid2")
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "description", nullable = false)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Notification(
            final String description,
            final User user) {
        this.description = description;
        this.user = user;
    }
}
