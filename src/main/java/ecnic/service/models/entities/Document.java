package ecnic.service.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ecnic.service.models.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "DOCUMENT")
@EqualsAndHashCode(callSuper = false)
public class Document extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String fileName;
    private String contentType;
    @JsonIgnore
    @Lob
    private byte[] data;
}
