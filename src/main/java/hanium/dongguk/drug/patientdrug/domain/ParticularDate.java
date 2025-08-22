package hanium.dongguk.drug.patientdrug.domain;

import hanium.dongguk.global.base.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Table(name = "particular_date")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ParticularDate extends BaseTimeEntity {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name="uuid2", strategy = "uuid2")
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "taking_type_id")
    private TakingType takingType;

    @Builder
    private ParticularDate(
            final LocalDate date,
            final TakingType takingType) {
        this.date = date;
        this.takingType = takingType;
    }

    public static ParticularDate create(
            final LocalDate date,
            final TakingType takingType) {
        return ParticularDate.builder()
                .date(date)
                .takingType(takingType)
                .build();
    }
}
