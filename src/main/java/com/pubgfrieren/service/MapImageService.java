package com.pubgfrieren.service;

import com.pubgfrieren.dto.MapImageDto;
import com.pubgfrieren.entity.MapCategory;
import com.pubgfrieren.entity.MapImage;
import com.pubgfrieren.repository.MapCategoryRepository;
import com.pubgfrieren.repository.MapImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MapImageService {

    private final MapImageRepository mapImageRepository;
    private final MapCategoryRepository mapCategoryRepository;
    private final FileStorageService fileStorageService;

    // 전체 조회
    public List<MapImageDto.Response> getAllImages() {
        return mapImageRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // 단건 조회
    public MapImageDto.Response getImage(Long id) {
        MapImage image = mapImageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("이미지를 찾을 수 없습니다. id=" + id));
        return toResponse(image);
    }

    // 카테고리별 조회
    public List<MapImageDto.Response> getImagesByCategory(Long categoryId) {
        return mapImageRepository.findByCategoryIdOrderByCreatedAtDesc(categoryId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // 등록 (이미지 파일 업로드)
    @Transactional
    public MapImageDto.Response createImage(MapImageDto.Request request, MultipartFile file) throws IOException {
        MapCategory category = mapCategoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("카테고리를 찾을 수 없습니다. id=" + request.getCategoryId()));

        String storedFileName = fileStorageService.storeFile(file);

        MapImage image = MapImage.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .category(category)
                .fileName(storedFileName)
                .originalFileName(file.getOriginalFilename())
                .filePath(storedFileName)
                .fileSize(file.getSize())
                .contentType(file.getContentType())
                .build();

        MapImage saved = mapImageRepository.save(image);
        return toResponse(saved);
    }

    // 수정 (이미지 파일 교체 가능)
    @Transactional
    public MapImageDto.Response updateImage(Long id, MapImageDto.Request request, MultipartFile file) throws IOException {
        MapImage image = mapImageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("이미지를 찾을 수 없습니다. id=" + id));

        image.setTitle(request.getTitle());
        image.setDescription(request.getDescription());

        if (request.getCategoryId() != null) {
            MapCategory category = mapCategoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("카테고리를 찾을 수 없습니다."));
            image.setCategory(category);
        }

        // 새 파일이 있으면 교체
        if (file != null && !file.isEmpty()) {
            fileStorageService.deleteFile(image.getFileName());
            String storedFileName = fileStorageService.storeFile(file);
            image.setFileName(storedFileName);
            image.setOriginalFileName(file.getOriginalFilename());
            image.setFilePath(storedFileName);
            image.setFileSize(file.getSize());
            image.setContentType(file.getContentType());
        }

        return toResponse(image);
    }

    // 삭제
    @Transactional
    public void deleteImage(Long id) {
        MapImage image = mapImageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("이미지를 찾을 수 없습니다. id=" + id));
        fileStorageService.deleteFile(image.getFileName());
        mapImageRepository.delete(image);
    }

    // Entity -> Response 변환
    private MapImageDto.Response toResponse(MapImage image) {
        return MapImageDto.Response.builder()
                .id(image.getId())
                .title(image.getTitle())
                .description(image.getDescription())
                .mapSize(image.getCategory().getMapSize())
                .mapName(image.getCategory().getMapName())
                .fileName(image.getFileName())
                .originalFileName(image.getOriginalFileName())
                .filePath(image.getFilePath())
                .fileSize(image.getFileSize())
                .contentType(image.getContentType())
                .imageWidth(image.getImageWidth())
                .imageHeight(image.getImageHeight())
                .status(image.getStatus())
                .version(image.getVersion())
                .isLatest(image.getIsLatest())
                .createdAt(image.getCreatedAt())
                .updatedAt(image.getUpdatedAt())
                .build();
    }
}