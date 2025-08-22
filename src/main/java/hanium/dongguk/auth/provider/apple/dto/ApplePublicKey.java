package hanium.dongguk.auth.provider.apple.dto;

public record ApplePublicKey(
        String kty,

        String kid,

        String alg,

        String n,

        String e
) {
}
