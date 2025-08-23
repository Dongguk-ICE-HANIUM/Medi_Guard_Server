package hanium.dongguk.notification.taking.service;

import hanium.dongguk.drug.patientdrug.domain.PatientDrug;
import hanium.dongguk.drug.patientdrug.service.retriever.PatientDrugRetriever;
import hanium.dongguk.global.exception.CommonException;
import hanium.dongguk.notification.taking.domain.NotifiTaking;
import hanium.dongguk.notification.taking.dto.request.NotifiTakingDto;
import hanium.dongguk.notification.taking.dto.request.NotifiTakingRequestDto;
import hanium.dongguk.notification.taking.dto.response.NotifiTakingResponseDto;
import hanium.dongguk.notification.taking.dto.response.RetrieveNotifiTakingResponseDto;
import hanium.dongguk.notification.taking.exception.NotifiTakingErrorCode;
import hanium.dongguk.user.patient.domain.UserPatient;
import hanium.dongguk.user.patient.service.UserPatientRetriever;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.time.LocalTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotifiTakingService {
    private final NotifiTakingRetriever notifiTakingRetriever;
    private final NotifiTakingSaver notifiTakingSaver;
    private final NotifiTakingRemover notifiTakingRemover;
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

    @Transactional
    public Void patch(
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
        // Id 검색을 위한 Map
        Map<UUID, NotifiTakingDto> idMap = notifiTakingDtoList.stream()
                .collect(Collectors.toMap(NotifiTakingDto::id, Function.identity()));

        // 수정할 UUID
        List<UUID> requestNotifiTakingIdList = uniqueTimeMap.values()
                                                     .stream()
                                                     .map(NotifiTakingDto::id)
                                                     .toList();

        // 수정할 NotifiTaking
        List<NotifiTaking> DbNotifiTakingList = notifiTakingRetriever
                .findAllByIdInAndPatientDrugId(requestNotifiTakingIdList, patientDrugId);

        // request 검증
        if (DbNotifiTakingList.size() != requestNotifiTakingIdList.size()) {
            throw CommonException.type(NotifiTakingErrorCode.NOT_FOUND);
        }

        List<NotifiTaking> others = notifiTakingRetriever.findAllByPatientDrugId(patientDrugId);
        Set<LocalTime> newTimes = uniqueTimeMap.keySet();
        boolean hasConflict = others.stream()
                                    .filter(temp -> !requestNotifiTakingIdList.contains(temp.getId()))
                                    .map(NotifiTaking::getTakingTime)
                                    .anyMatch(newTimes::contains);

        if (hasConflict) {
            throw CommonException.type(NotifiTakingErrorCode.DUPLICATED_TIME);
        }

        DbNotifiTakingList.forEach(notifiTaking -> {
                    NotifiTakingDto dto = idMap.get(notifiTaking.getId());
                    if (dto != null) {
                        notifiTaking.update(dto.time(), dto.isActive());
                    }
                }
            );

        return null;
    }

    public void delete(UUID userId, UUID notifiTakingId) {
        NotifiTaking targetNotifiTaking
                = notifiTakingRetriever.findByIdAndUserPatientId(notifiTakingId, userId);

        notifiTakingRemover.deleteById(targetNotifiTaking.getId());
    }

    public RetrieveNotifiTakingResponseDto getListNotifiTaking(UUID userId, UUID patientDrugId) {
        PatientDrug targetPatient = patientDrugRetriever.findByIdAndUserId(patientDrugId, userId);

        List<NotifiTaking> notifiTakingList
                = notifiTakingRetriever.findAllByPatientDrugId(targetPatient.getId());

        List<NotifiTakingResponseDto> notifiTakingResponseDtoList
                = notifiTakingList.stream()
                    .map(NotifiTakingResponseDto::from)
                    .toList();


        return RetrieveNotifiTakingResponseDto.of(
            notifiTakingResponseDtoList
        );
    }
}
