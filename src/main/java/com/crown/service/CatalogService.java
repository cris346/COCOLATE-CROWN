package com.crown.service;

import com.crown.dto.CatalogResponse;
import com.crown.repository.BoxSizeRepository;
import com.crown.repository.ChocolateFlavorRepository;
import com.crown.repository.FillingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

/**
 * Returns catalog data from the database.
 * This ensures the frontend can dynamically load flavors, fillings, and box sizes.
 */
@Service
@RequiredArgsConstructor
public class CatalogService {

    private final BoxSizeRepository boxSizeRepository;
    private final ChocolateFlavorRepository chocolateFlavorRepository;
    private final FillingRepository fillingRepository;

    public CatalogResponse getCatalog() {
        return CatalogResponse.builder()
                .boxSizes(boxSizeRepository.findAll().stream()
                        .map(bs -> CatalogResponse.BoxSizeDto.builder()
                                .id(bs.getId())
                                .name(bs.getName())
                                .capacity(bs.getCapacity())
                                .basePrice(bs.getBasePrice())
                                .build())
                        .collect(Collectors.toList()))
                .flavors(chocolateFlavorRepository.findAll().stream()
                        .map(f -> f.getName())
                        .collect(Collectors.toList()))
                .fillings(fillingRepository.findAll().stream()
                        .map(f -> f.getName())
                        .collect(Collectors.toList()))
                .build();
    }
}
