package com.forbusypeople.budget.repositories.entities;

import com.forbusypeople.budget.enums.RoomsType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "rooms")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class RoomsEntity extends BaseBudgetEntity {

    @Enumerated(EnumType.STRING)
    private RoomsType type;
    private BigDecimal cost;
    @Transient
    private Boolean rent;
    @Transient
    private String currency;

}
