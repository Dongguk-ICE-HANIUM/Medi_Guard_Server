package hanium.dongguk.drug.patientdrug.domain;

import hanium.dongguk.global.base.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
@Getter
@Table(name = "taking_type")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TakingType extends BaseTimeEntity {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name="uuid2", strategy = "uuid2")
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private ETakingType type;

    @Column(name = "taking_interval", nullable = false)
    private Short takingInterval;

    @Builder
    private TakingType(
            final ETakingType type,
            final Short takingInterval) {
        this.type = type;
        this.takingInterval = takingInterval;
    }

    public static TakingType create(
            final ETakingType type,
            final Short takingInterval) {
        return TakingType.builder()
                .type(type)
                .takingInterval(takingInterval)
                .build();
    }
}
