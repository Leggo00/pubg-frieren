package com.pubgfrieren.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "mapcategory")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MapCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "map_size", nullable = false, length = 10)
    private String mapSize;

    @Column(name = "map_name", nullable = false, length = 50)
    private String mapName;

    @Column(name = "in_use", columnDefinition = "char(1)")
    @Builder.Default
    private String inUse = "Y";

    @Column(name = "sort_order")
    @Builder.Default
    private Integer sortOrder = 0;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}