package hanium.dongguk.user.doctor.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EDepartment {
    // 내과계
    INTERNAL_MEDICINE("내과"),
    CARDIOLOGY("심장내과"),
    GASTROENTEROLOGY("소화기내과"),
    ENDOCRINOLOGY("내분비내과"),
    NEPHROLOGY("신장내과"),
    HEMATOLOGY("혈액내과"),
    ONCOLOGY("종양내과"),
    RHEUMATOLOGY("류마티스내과"),
    NEUROLOGY("신경과"),
    PULMONOLOGY("호흡기내과"),

    // 외과계
    GENERAL_SURGERY("외과"),
    ORTHOPEDIC_SURGERY("정형외과"),
    NEUROSURGERY("신경외과"),
    PLASTIC_SURGERY("성형외과"),
    THORACIC_SURGERY("흉부외과"),
    UROLOGY("비뇨의학과"),

    // 전문과
    PEDIATRICS("소아청소년과"),
    OBSTETRICS_GYNECOLOGY("산부인과"),
    OPHTHALMOLOGY("안과"),
    OTOLARYNGOLOGY("이비인후과"),
    DERMATOLOGY("피부과"),
    PSYCHIATRY("정신건강의학과"),
    ANESTHESIOLOGY("마취통증의학과"),
    RADIOLOGY("영상의학과"),
    PATHOLOGY("병리과"),
    FAMILY_MEDICINE("가정의학과"),
    EMERGENCY_MEDICINE("응급의학과"),
    REHABILITATION("재활의학과"),
    DENTISTRY("치과"),

    // 기타
    OTHER("기타");

    private final String displayName;
}