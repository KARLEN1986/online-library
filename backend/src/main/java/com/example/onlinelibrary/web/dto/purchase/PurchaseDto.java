package com.example.onlinelibrary.web.dto.purchase;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class PurchaseDto {

    private Long id;
    private Long userId;
    private Long bookId;

    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime purchaseDate;

    private int rating;
}
