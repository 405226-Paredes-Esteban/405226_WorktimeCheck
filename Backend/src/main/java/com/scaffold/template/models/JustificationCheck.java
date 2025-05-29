package com.scaffold.template.models;

import com.scaffold.template.entities.TimeJustificationEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class JustificationCheck {
    private Long checkId ;

    private Long justificationId;

    private Boolean checkApproval;

    private String checkReason;

    private Long checkState;
}
