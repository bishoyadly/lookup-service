package org.cleancoders.lookupservice.usecases;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CategoryUseCaseErrorMessages {

    public static final String CATEGORY_ALREADY_EXISTS = "Category With That Name Already Exists";
}
