package org.cleancoders.lookupservice.usecases;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryRequest {
    private String englishName;
    private String arabicName;
}
