package hanium.dongguk.patientdrug.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface PatientDrugRepository extends JpaRepository<PatientDrug, UUID> {

    // 필요 시 아래 메서드 추가 가능
    // List<PatientDrug> findByUserPatientId(UUID patientId);
}