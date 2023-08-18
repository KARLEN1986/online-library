package com.example.onlinelibrary.web.dto.user;

import com.example.onlinelibrary.web.dto.validation.OnCreate;
import com.example.onlinelibrary.web.dto.validation.OnUpdate;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@Schema(description = "User DTO")
public class UserDto {

    @Schema(description = "User id", example = "1")
    @NotNull(message = "Id must be not null.",
            groups = OnUpdate.class)
    private Long id;

    @Schema(description = "User name", example = "Hiram Carr")
    @NotNull(message = "Name must be not null.",
            groups = {OnCreate.class, OnUpdate.class})
    @Length(max = 255, message = "Name length must be smaller than 255 symbols.",
            groups = {OnCreate.class, OnUpdate.class})
    private String name;

    @Schema(description = "User phone", example = "(855) 354-4060")
    @NotNull(message = "User phone must be not null.",
            groups = {OnCreate.class, OnUpdate.class})
    @Length(max = 255, message = "User phone length must be smaller than 255 symbols.",
            groups = {OnCreate.class, OnUpdate.class})
    private String phone;

    @Schema(description = "User email", example = "johndoe@gmail.com")
    @NotNull(message = "Username must be not null.",
            groups = {OnCreate.class, OnUpdate.class})
    @Length(max = 255, message = "Username length must be smaller than 255 symbols.",
            groups = {OnCreate.class, OnUpdate.class})
    private String email;

    @Schema(description = "Address", example = "322-3020 Venetians Rd.")
    @NotNull(message = "Address must be not null.",
            groups = {OnCreate.class, OnUpdate.class})
    @Length(max = 255, message = "User address length must be smaller than 255 symbols.",
            groups = {OnCreate.class, OnUpdate.class})
    private String address;

    @Schema(description = "PostalZip", example = "51728")
    @NotNull(message = "PostalZip must be not null.",
            groups = {OnCreate.class, OnUpdate.class})
    @Length(max = 255, message = "PostalZip length must be smaller than 255 symbols.",
            groups = {OnCreate.class, OnUpdate.class})
    private String postalZip;

    @Schema(description = "Country", example = "United States")
    @NotNull(message = "Country must be not null.",
            groups = {OnCreate.class, OnUpdate.class})
    @Length(max = 255, message = "Country length must be smaller than 255 symbols.",
            groups = {OnCreate.class, OnUpdate.class})
    private String country;

    @Schema(description = "Pan", example = "547696 4338816657")
    @NotNull(message = "Pan must be not null.",
            groups = {OnCreate.class, OnUpdate.class})
    @Length(max = 255, message = "Pan length must be smaller than 255 symbols.",
            groups = {OnCreate.class, OnUpdate.class})
    private String pan;

    @Schema(description = "User crypted password")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull(message = "Password must be not null.",
            groups = {OnCreate.class, OnUpdate.class})
    private String password;

    @Schema(description = "User password confirmation")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull(message = "Password confirmation must be not null.",
            groups = {OnCreate.class})
    private String passwordConfirmation;

    @Schema(description = "Expiration Date", example = "2023-10-01 00:00")
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime expirationDate;

    @Schema(description = "Cvv", example = "143")
    @NotNull(message = "Cvv must be not null.",
            groups = {OnCreate.class, OnUpdate.class})
    @Length(max = 3, message = "Cvv length must be smaller than 3 symbols.", groups = {OnCreate.class, OnUpdate.class})
    private String cvv;

}
