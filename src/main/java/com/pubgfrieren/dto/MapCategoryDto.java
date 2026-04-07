package com.pubgfrieren.dto;

import lombok.*;

import java.time.LocalDateTime;

public class MapCategoryDto {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {
        private String mapSize;
        private String mapName;
        private String inUse;
        private Integer sortOrder;
    }

    @Getter
    @Builder
    public static class Response {
        private Long id;
        private String mapSize;
        private String mapName;
        private String inUse;
        private Integer sortOrder;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }
}