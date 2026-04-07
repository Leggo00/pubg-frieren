package com.pubgfrieren.repository;

import com.pubgfrieren.entity.MapCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MapCategoryRepository extends JpaRepository<MapCategory, Long> {

    List<MapCategory> findAllByOrderBySortOrderAsc();

    List<MapCategory> findByInUseOrderBySortOrderAsc(String inUse);

    List<MapCategory> findByMapSizeOrderBySortOrderAsc(String mapSize);
}