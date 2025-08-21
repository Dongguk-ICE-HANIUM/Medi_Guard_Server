package hanium.dongguk.user.patient.auth.dto;

public record ApplePublicKey(
        String kty,

        String kid,

        String alg,

        String n,

        String e
) {
}
