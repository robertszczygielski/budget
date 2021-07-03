package com.forbusypeople.budget.repositories.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "property")
@Data
public class PropertyEntity extends BaseBudgetEntity {

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<RoomsEntity> rooms;
    private Boolean single;
    private String city;
    private String postCode;
    private String street;
    private String house;
    private Boolean sold;

}
