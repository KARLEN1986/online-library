package com.example.onlinelibrary.web.mappers;

import com.example.onlinelibrary.domain.purchase.Purchase;
import com.example.onlinelibrary.web.dto.purchase.PurchaseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Mapper interface for converting Purchase entities to PurchaseDto DTOs and vice versa.
 */
@Mapper(componentModel = "spring")
public interface PurchaseMapper {

    /**
     * Convert a Purchase entity to a PurchaseDto DTO.
     *
     * @param purchase The Purchase entity to be converted.
     * @return The corresponding PurchaseDto DTO.
     */
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "book.id", target = "bookId")
    PurchaseDto toDto(Purchase purchase);

    /**
     * Convert a PurchaseDto DTO to a Purchase entity.
     *
     * @param purchaseDto The PurchaseDto DTO to be converted.
     * @return The corresponding Purchase entity.
     */
    @Mapping(source = "userId", target = "user.id")
    @Mapping(source = "bookId", target = "book.id")
    Purchase toEntity(PurchaseDto purchaseDto);

    /**
     * Convert a list of Purchase entities to a list of PurchaseDto DTOs.
     *
     * @param purchases The list of Purchase entities to be converted.
     * @return The corresponding list of PurchaseDto DTOs.
     */
    List<PurchaseDto> toDto(List<Purchase> purchases);
}