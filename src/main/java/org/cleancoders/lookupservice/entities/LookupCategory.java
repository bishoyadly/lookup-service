package org.cleancoders.lookupservice.entities;

import lombok.Data;

import java.util.List;

@Data
public class LookupCategory {
    private String id;
    private String englishName;
    private String arabicName;
    private List<LookupItem> lookupItemList;
}
