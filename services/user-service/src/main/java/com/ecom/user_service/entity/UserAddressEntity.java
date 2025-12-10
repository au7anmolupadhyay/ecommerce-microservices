package com.ecom.user_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "address")
@AllArgsConstructor
public class UserAddressEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private String user_id;
    private String city;
    private String state;
    private String pincode;
    private String country;
    private boolean is_default;
    private Date created_at;
    private Date updated_at;
}
