package com.Zipdrive.API.Model;
import com.Zipdrive.API.enums.IdType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;
@Entity
@Table(name = "govt_ids")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GovernmentId {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(name = "id_type", nullable = false)
    private IdType idType;
    @Column(name = "id_number", nullable = false, unique = true)
    private String idNumber;
    @Column(name = "file_path", nullable = false)
    private String filePath;
    @CreationTimestamp
    @Column(name = "uploaded_at", updatable = false)
    private LocalDateTime uploadedAt;
    @Column(name = "verified", nullable = false)
    private boolean verified = false;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
