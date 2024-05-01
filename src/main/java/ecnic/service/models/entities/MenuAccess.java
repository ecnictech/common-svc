package ecnic.service.models.entities;

import ecnic.service.models.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "MENU_ACCESS")
@EqualsAndHashCode(callSuper = false)
public class MenuAccess extends BaseEntity {

    @Id
    private Integer id;
    private String name;
}
