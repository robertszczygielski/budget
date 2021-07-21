package com.forbusypeople.budget.repositories.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "property")
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class PropertyEntity extends BaseBudgetEntity {

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "property_room_associations",
            joinColumns = {@JoinColumn(name = "property_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "room_id", referencedColumnName = "id")}
    )
    private List<RoomsEntity> rooms;
    private Boolean single;
    private String city;
    @Column(name = "post_code")
    private String postCode;
    private String street;
    private String house;
    private Boolean sold;

}
