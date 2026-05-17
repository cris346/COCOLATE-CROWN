package com.crown.dto;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

/**
 * DTO for returning all catalog data in a single call.
 */
@Data
@Builder
public class CatalogResponse {
    private List<BoxSizeDto> boxSizes;
    private List<String> flavors;
    private List<String> fillings;

    @Data
    @Builder
    public static class BoxSizeDto {
        private Long id;
        private String name;
        private Integer capacity;
        private BigDecimal basePrice;
    }
}
