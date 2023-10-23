package com.example.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Table
@Setter
@Getter
@SuperBuilder
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Note extends Auditable {
    @Id
    @UuidGenerator
    private String id;
    private String title;
    private String content;
    private String userId;


    public Note(String title, String content, String userId) {
        this.title = title;
        this.content = content;
        this.userId = userId;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || this.getId() == null) {
            return false;
        }
        if (!(obj instanceof Note)) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (((Note) obj).getId() == null) {
            return false;
        }

        return this.getId().equals(((Note) obj).getId());
    }

    @Override
    public int hashCode() {
        if (this.getId() == null) return 0;
        return this.getId().hashCode();
    }
}
