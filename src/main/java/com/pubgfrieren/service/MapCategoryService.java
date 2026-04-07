package com.pubgfrieren.service;

import com.pubgfrieren.dto.MapCategoryDto;
import com.pubgfrieren.entity.MapCategory;
import com.pubgfrieren.repository.MapCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MapCategoryService {

    private final MapCategoryRepository mapCategoryRepository;

    // 전체 조회
    public List<MapCategoryDto.Response> getAllCategories() {
        return mapCategoryRepository.findAllByOrderBySortOrderAsc()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // 단건 조회
    public MapCategoryDto.Response getCategory(Long id) {
        MapCategory category = mapCategoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("카테고리를 찾을 수 없습니다. id=" + id));
        return toResponse(category);
    }

    // 등록
    @Transactional
    public MapCategoryDto.Response createCategory(MapCategoryDto.Request request) {
        MapCategory category = MapCategory.builder()
                .mapSize(request.getMapSize())
                .mapName(request.getMapName())
                .inUse(request.getInUse() != null ? request.getInUse() : "Y")
                .sortOrder(request.getSortOrder() != null ? request.getSortOrder() : 0)
                .build();

        MapCategory saved = mapCategoryRepository.save(category);
        return toResponse(saved);
    }

    // 수정
    @Transactional
    public MapCategoryDto.Response updateCategory(Long id, MapCategoryDto.Request request) {
        MapCategory category = mapCategoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("카테고리를 찾을 수 없습니다. id=" + id));

        category.setMapSize(request.getMapSize());
        category.setMapName(request.getMapName());
        if (request.getInUse() != null) category.setInUse(request.getInUse());
        if (request.getSortOrder() != null) category.setSortOrder(request.getSortOrder());

        return toResponse(category);
    }

    // 삭제
    @Transactional
    public void deleteCategory(Long id) {
        MapCategory category = mapCategoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("카테고리를 찾을 수 없습니다. id=" + id));
        mapCategoryRepository.delete(category);
    }

    // Entity -> Response 변환
    private MapCategoryDto.Response toResponse(MapCategory category) {
        return MapCategoryDto.Response.builder()
                .id(category.getId())
                .mapSize(category.getMapSize())
                .mapName(category.getMapName())
                .inUse(category.getInUse())
                .sortOrder(category.getSortOrder())
                .createdAt(category.getCreatedAt())
                .updatedAt(category.getUpdatedAt())
                .build();
    }
}