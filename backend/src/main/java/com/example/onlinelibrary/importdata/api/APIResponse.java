package com.example.onlinelibrary.importdata.api;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Represents a response containing a list of APIBooks.
 */
@Setter
@Getter
public class APIResponse {

    private List<APIBookDto> data;

}