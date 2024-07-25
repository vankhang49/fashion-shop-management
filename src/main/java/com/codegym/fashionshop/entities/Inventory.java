package com.codegym.fashionshop.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "inventories")
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inventory_id")
    private Long inventoryId;

    @Column(name = "ticket_code")
    private String ticketCode;

    @ManyToOne
    @JoinColumn(name = "userId")
    private AppUser user;

    @Column(name = "date_create")
    private LocalDate dateCreate;
}
