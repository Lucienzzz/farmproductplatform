package com.zzq.farmproductplatform.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "usercheck")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String user_id;
    @Column(name = "user_username")
    private String username;
    @Column(name = "user_password")
    private String password;
}
