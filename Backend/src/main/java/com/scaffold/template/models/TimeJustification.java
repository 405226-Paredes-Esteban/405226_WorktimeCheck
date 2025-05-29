package com.scaffold.template.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TimeJustification {
    private Long justificationId;

    private Long timeId;

    private String justificationObservation;

    private String justificationUrl;

    private Long timeState;
}
