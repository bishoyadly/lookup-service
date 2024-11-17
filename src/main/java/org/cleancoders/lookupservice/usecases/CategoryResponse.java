package org.cleancoders.lookupservice.usecases;

import lombok.Builder;
import lombok.Data;
import org.cleancoders.lookupservice.entities.LookupItem;

import java.util.List;

@Data
@Builder
public class CategoryResponse {
    private String id;
    private String englishName;
    private String arabicName;
    private List<LookupItem> lookupItemList;
}
