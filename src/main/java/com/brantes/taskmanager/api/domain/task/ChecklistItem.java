package com.brantes.taskmanager.api.domain.task;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;


@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChecklistItem {

    private String title;
    private boolean completed;
}
