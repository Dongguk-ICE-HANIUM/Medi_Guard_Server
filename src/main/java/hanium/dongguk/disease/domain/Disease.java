package hanium.dongguk.disease.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
@Getter
@Table(name = "diseases")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Disease {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "english_name", nullable = false)
    private String englishName;

    @Column(name = "disease_code", nullable = false)
    private String code;
}
