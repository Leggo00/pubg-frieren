package com.pubgfrieren.dto;

import lombok.*;

import java.time.LocalDateTime;

public class MapImageDto {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {
        private String title;
        private String description;
        private Long categoryId;
    }

    @Getter
    @Builder
    public static class Response {
        private Long id;
        private String title;
        private String description;
        private String mapSize;
        private String mapName;
        private String fileName;
        private String originalFileName;
        private String filePath;
        private Long fileSize;
        private String contentType;
        private Integer imageWidth;
        private Integer imageHeight;
        private String status;
        private Integer version;
        private String isLatest;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }
}