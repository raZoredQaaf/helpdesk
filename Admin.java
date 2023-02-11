package com.fdmgroup.helpdeskapi.model;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

import lombok.Data;

@Data
@Entity
@PrimaryKeyJoinColumn(name = "user_id")
public class Admin extends User {

}
