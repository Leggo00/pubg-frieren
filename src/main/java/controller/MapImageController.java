package com.pubgfrieren.controller;

import com.pubgfrieren.dto.MapImageDto;
import com.pubgfrieren.service.MapImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/images")
@RequiredArgsConstructor
public class MapImageController {

    private final MapImageService mapImageService;

    // 전체 조회
    @GetMapping
    public ResponseEntity<List<MapImageDto.Response>> getAllImages() {
        return ResponseEntity.ok(mapImageService.getAllImages());
    }

    // 단건 조회
    @GetMapping("/{id}")
    public ResponseEntity<MapImageDto.Response> getImage(@PathVariable Long id) {
        return ResponseEntity.ok(mapImageService.getImage(id));
    }

    // 카테고리별 조회
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<MapImageDto.Response>> getImagesByCategory(@PathVariable Long categoryId) {
        return ResponseEntity.ok(mapImageService.getImagesByCategory(categoryId));
    }

    // 등록 (이미지 파일 업로드)
    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<MapImageDto.Response> createImage(
            @RequestParam("title") String title,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam("categoryId") Long categoryId,
            @RequestPart("file") MultipartFile file) throws IOException {

        MapImageDto.Request request = new MapImageDto.Request();
        request.setTitle(title);
        request.setDescription(description);
        request.setCategoryId(categoryId);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(mapImageService.createImage(request, file));
    }

    // 수정
    @PutMapping("/{id}")
    public ResponseEntity<MapImageDto.Response> updateImage(
            @PathVariable Long id,
            @RequestPart("data") MapImageDto.Request request,
            @RequestPart(value = "file", required = false) MultipartFile file) throws IOException {
        return ResponseEntity.ok(mapImageService.updateImage(id, request, file));
    }

    // 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteImage(@PathVariable Long id) {
        mapImageService.deleteImage(id);
        return ResponseEntity.noContent().build();
    }
}