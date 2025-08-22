package hanium.dongguk.drug.drug.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
@Getter
@Table(name = "drugs")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Drug {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name="uuid2", strategy = "uuid2")
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "company_name", nullable = false)
    private String companyName;

    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "effect", columnDefinition = "TEXT", nullable = false)
    private String effect;

    @Column(name = "warning", columnDefinition = "TEXT", nullable = false)
    private String warning;

    @Column(name = "interaction", columnDefinition = "TEXT", nullable = false)
    private String interaction;

    @Column(name = "side_effect", columnDefinition = "TEXT", nullable = false)
    private String sideEffect;

    @Column(name = "deposit_method", columnDefinition = "TEXT", nullable = false)
    private String depositMethod;
}
