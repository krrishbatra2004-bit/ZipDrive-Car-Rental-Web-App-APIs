package com.Zipdrive.API.Model;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String make;
    private String model;
    private int yearOfMake;
    private int kmsDriven;
    private String rcNumber;
    private String rcImageFront;
    private String rcImageBack;
    private String insuranceImagePath;
    @ElementCollection
    private List<String> carImagePaths;
    @Column(name = "fasttag_image_path")
    private String fasttagImagePath;
}
