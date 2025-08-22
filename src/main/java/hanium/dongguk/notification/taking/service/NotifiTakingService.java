package hanium.dongguk.notification.taking.service;

import hanium.dongguk.drug.patientdrug.domain.PatientDrug;
import hanium.dongguk.drug.patientdrug.service.retriever.PatientDrugRetriever;
import hanium.dongguk.global.exception.CommonException;
import hanium.dongguk.notification.taking.domain.NotifiTaking;
import hanium.dongguk.notification.taking.dto.request.NotifiTakingDto;
import hanium.dongguk.notification.taking.dto.request.NotifiTakingRequestDto;
import hanium.dongguk.notification.taking.exception.NotifiTakingErrorCode;
import hanium.dongguk.user.patient.domain.UserPatient;
import hanium.dongguk.user.patient.service.UserPatientRetriever;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotifiTakingService {
    private final NotifiTakingRetriever notifiTakingRetriever;
    private final NotifiTakingSaver notifiTakingSaver;
    private final PatientDrugRetriever patientDrugRetriever;
    private final UserPatientRetriever userPatientRetriever;

    @Transactional
    public URI create(
            UUID userId,
            UUID patientDrugId,
            NotifiTakingRequestDto requestDto) {
        PatientDrug currentPatientDrug
                = patientDrugRetriever.findByIdAndUserId(patientDrugId, userId);
        UserPatient currentPatientUser = userPatientRetriever.getUserPatient(userId);

        List<NotifiTakingDto> notifiTakingDtoList = requestDto.notifiTakingDtoList();

        // Request 중복 time 값 처리
        Map<LocalTime, NotifiTakingDto> uniqueTimeMap
                = notifiTakingDtoList.stream()
                                     .collect(
                                             Collectors.toMap(
                                                     NotifiTakingDto::time,
                                                     Function.identity(),
                                                    (existing, replacement) -> existing
                                             )
                                     );

        List<LocalTime> timeListToCheck = new ArrayList<>(uniqueTimeMap.keySet());

        // DB에 값 존재하는지 확인
        List<LocalTime> duplicatedTimes
                = notifiTakingRetriever.findAllByPatientDrugId(patientDrugId)
                                       .stream()
                                       .map(NotifiTaking::getTakingTime)
                                       .filter(timeListToCheck::contains)
                                       .toList();

        // 하나라도 겹치면 에러 터짐
        if (!duplicatedTimes.isEmpty()) {
            throw CommonException.type(NotifiTakingErrorCode.DUPLICATED_TIME);
        }

        uniqueTimeMap.values().forEach(temp ->
                notifiTakingSaver.save(
                        NotifiTaking.create(
                                currentPatientUser,
                                temp.time(),
                                temp.isActive(),
                                currentPatientDrug
                        )
                )
        );

        return null;
    }
}
