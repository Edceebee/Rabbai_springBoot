package com.blogapp.data.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor

public class Comment {
    @Id
    @GeneratedValue
    private UUID id;


    private String commentatorName;

    @Column(nullable = false, length = 150)
    private String content;

    @CreationTimestamp
    private LocalDate dateCreated;



    public Comment(String content, String commentatorName) {
        this.content = content;
        this.commentatorName = commentatorName;
    }
}

