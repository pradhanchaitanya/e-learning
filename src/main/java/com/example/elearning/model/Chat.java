package com.example.elearning.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "tb_chat")
@Data
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private List<String> chatHistory;

    private boolean status;

    private User primaryUser;

    private User secondaryUser;

    private Date lastSeen;
}
