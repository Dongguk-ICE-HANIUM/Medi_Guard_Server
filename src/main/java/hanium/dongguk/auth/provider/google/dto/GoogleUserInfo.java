package hanium.dongguk.auth.provider.google.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record GoogleUserInfo (

    @JsonProperty("sub")
    String providerId,

    String name,
    String email,
    String picture

    ){
}
