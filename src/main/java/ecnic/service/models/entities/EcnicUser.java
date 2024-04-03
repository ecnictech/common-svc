package ecnic.service.models.entities;import ecnic.service.constants.EcnicUserRole;import ecnic.service.models.BaseEntity;import jakarta.persistence.*;import jakarta.validation.constraints.Email;import lombok.Builder;import lombok.Data;import lombok.EqualsAndHashCode;import java.util.Set;@Data@Builder@Entity@Table(name = "ECNIC_USER")@EqualsAndHashCode(callSuper = false)public class EcnicUser extends BaseEntity {    @Id    @GeneratedValue(strategy = GenerationType.AUTO)    private Integer id;    @Column(unique = true)    private String username;    private String password;    private String saltPassword;    private String ipv4;    @Email    private String firstName;    private String lastName;    private String email;    private String address;    @ElementCollection(fetch = FetchType.EAGER)    @Enumerated(EnumType.STRING)    private Set<EcnicUserRole> roles;}