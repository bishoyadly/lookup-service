package org.cleancoders.lookupservice.usecases;

import lombok.AllArgsConstructor;
import org.cleancoders.lookupservice.entities.LookupCategory;

@AllArgsConstructor
public class CategoryUseCaseInteractor implements CategoryInputBoundary {

    private final CategoryOutputBoundary categoryPresenter;

    private final CategoryDataAccessGateway categoryDataAccessGateway;

    @Override
    public CategoryResponse createCategory(CategoryRequest categoryRequest) {
        if (categoryDataAccessGateway.existsByEnglishName(categoryRequest.getEnglishName())) {
            return categoryPresenter.presentBadRequestFailure(CategoryUseCaseErrorMessages.CATEGORY_ALREADY_EXISTS);
        } else {
            String categoryId = categoryDataAccessGateway.save(new LookupCategory());
            CategoryResponse response = CategoryResponse.builder().id(categoryId).build();
            return categoryPresenter.presentSuccessResponse(response);
        }
    }
}
