package hanium.dongguk.user.patient.auth.dto;

import java.util.List;

public record ApplePublicKeysResponseDto (

    List<ApplePublicKey> keyList

){
}