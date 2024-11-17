package org.cleancoders.lookupservice.usecases;

import org.cleancoders.lookupservice.entities.LookupCategory;

public interface CategoryDataAccessGateway {
    boolean existsByEnglishName(String englishName);

    String save(LookupCategory lookupCategory);
}
