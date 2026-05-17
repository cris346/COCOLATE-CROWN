package com.crown.controller;

import com.crown.dto.CatalogResponse;
import com.crown.service.CatalogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller to serve the product catalog.
 * Returns box sizes, flavors, and fillings from the database.
 */
@RestController
@RequestMapping("/api/catalog")
@RequiredArgsConstructor
public class CatalogController {

    private final CatalogService catalogService;

    /**
     * GET /api/catalog
     * Returns all available box sizes, flavors, and fillings.
     */
    @GetMapping
    public ResponseEntity<CatalogResponse> getCatalog() {
        return ResponseEntity.ok(catalogService.getCatalog());
    }
}
