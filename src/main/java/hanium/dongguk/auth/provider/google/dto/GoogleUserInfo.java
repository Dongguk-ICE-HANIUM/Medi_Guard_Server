package hanium.dongguk.auth.provider.google.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GoogleUserInfo (

    @JsonProperty("sub")
    String providerId,

    String name,
    String email,
    String picture

    ){
}
