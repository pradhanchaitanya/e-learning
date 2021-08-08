package com.example.elearning.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tb_resource")
@Data
public class Resource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long resourceId;

    @Column(name = "resource_name")
    private String resourceName;

    @Column(name = "resource_link")
    private String resourceLink;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "resource_category")
    private String resourceCategory;

    @CreationTimestamp
    @Temporal(TemporalType.DATE)
    @Column(name = "created_on")
    private Date createdOn;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "created_by", referencedColumnName = "id")
    private User createdBy;

    @Column(name = "verified")
    private boolean verified;

    @Column(name = "active")
    private boolean active;
}
