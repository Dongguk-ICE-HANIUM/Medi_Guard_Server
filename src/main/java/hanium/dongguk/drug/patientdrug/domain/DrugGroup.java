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
@Table(name = "drug_group")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DrugGroup extends BaseTimeEntity {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name="uuid2", strategy = "uuid2")
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "isActive", nullable = false)
    private Boolean isActive;

    @Builder
    private DrugGroup(
            final String name,
            final Boolean isActive) {
        this.name = name;
        this.isActive = isActive;
    }

    public static DrugGroup create(
            final String name,
            final Boolean isActive) {
        return DrugGroup.builder()
                .name(name)
                .isActive(isActive)
                .build();
    }
}
