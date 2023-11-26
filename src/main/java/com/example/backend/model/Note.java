package com.example.backend.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;
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
    @GeneratedValue(generator = "custom-id", strategy = GenerationType.SEQUENCE)
    @GenericGenerator(name = "custom-id", strategy = "com.example.backend.model.IdGenerator")
    private String id;
    private String title;
    private String content;
    private String userId;

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
