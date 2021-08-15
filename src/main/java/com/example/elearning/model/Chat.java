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
    @Column(name = "id")
    private Long id;

    @Column(name = "chat_history")
    @Convert(converter = StringListConverter.class)
    private List<String> chatHistory;

    @Column(name = "status")
    private boolean status;

    @OneToOne
    @JoinColumn(name = "primary_user_id", referencedColumnName = "id")
    private User primaryUser;

    @OneToOne
    @JoinColumn(name = "secondary_user_id", referencedColumnName = "id")
    private User secondaryUser;

    @Column(name = "last_seen")
    private Date lastSeen;
}
