package com.pubgfrieren.repository;

import com.pubgfrieren.entity.MapImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MapImageRepository extends JpaRepository<MapImage, Long> {

    List<MapImage> findAllByOrderByCreatedAtDesc();

    List<MapImage> findByCategoryIdOrderByCreatedAtDesc(Long categoryId);

    List<MapImage> findByIsLatestOrderByCreatedAtDesc(String isLatest);
}