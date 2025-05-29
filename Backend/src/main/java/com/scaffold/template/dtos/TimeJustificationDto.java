package com.scaffold.template.dtos;

import io.minio.GetObjectResponse;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class TimeJustificationDto {
    private Long justificationId;

    @NotNull
    private Long timeId;

    @NotNull
    private String justificationObservation;

    private String justificationUrl;

    private GetObjectResponse file;

    private Long timeState;

}
