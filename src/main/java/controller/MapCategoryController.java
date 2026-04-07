package com.pubgfrieren.controller;

import com.pubgfrieren.dto.MapCategoryDto;
import com.pubgfrieren.service.MapCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class MapCategoryController {

    private final MapCategoryService mapCategoryService;

    // 전체 조회
    @GetMapping
    public ResponseEntity<List<MapCategoryDto.Response>> getAllCategories() {
        return ResponseEntity.ok(mapCategoryService.getAllCategories());
    }

    // 단건 조회
    @GetMapping("/{id}")
    public ResponseEntity<MapCategoryDto.Response> getCategory(@PathVariable Long id) {
        return ResponseEntity.ok(mapCategoryService.getCategory(id));
    }

    // 등록
    @PostMapping
    public ResponseEntity<MapCategoryDto.Response> createCategory(@RequestBody MapCategoryDto.Request request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(mapCategoryService.createCategory(request));
    }

    // 수정
    @PutMapping("/{id}")
    public ResponseEntity<MapCategoryDto.Response> updateCategory(@PathVariable Long id,
                                                                  @RequestBody MapCategoryDto.Request request) {
        return ResponseEntity.ok(mapCategoryService.updateCategory(id, request));
    }

    // 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        mapCategoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}