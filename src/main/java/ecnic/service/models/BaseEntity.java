package ecnic.service.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.ZonedDateTime;

@MappedSuperclass
@EntityListeners(value = {AuditingEntityListener.class})
@Data
@EqualsAndHashCode(callSuper = false)
public class BaseEntity {

    @CreatedBy
    @Column(name = "CREATED_BY" , nullable = false, updatable = false)
    protected String createdBy;

    @CreatedDate
    @Column(name = "CREATED_DTM", nullable = false, updatable = false)
    protected ZonedDateTime createdDtm;

    @LastModifiedBy
    @Column(name = "MODIFIED_BY" , nullable = false)
    protected String modifiedBy;

    @LastModifiedDate
    @Column(name = "MODIFIED_DTM", nullable = false)
    protected ZonedDateTime modifiedDtm;

    @PrePersist
    protected void onCreate() {
        createdDtm = ZonedDateTime.now();
        modifiedDtm = ZonedDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        modifiedDtm = ZonedDateTime.now();
    }

}
