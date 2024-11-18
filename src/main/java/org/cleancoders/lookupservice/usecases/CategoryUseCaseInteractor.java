package org.cleancoders.lookupservice.usecases;

import lombok.AllArgsConstructor;
import org.cleancoders.lookupservice.entities.LookupCategory;

@AllArgsConstructor
public class CategoryUseCaseInteractor implements CategoryInputBoundary {

    private final CategoryOutputBoundary categoryPresenter;

    private final CategoryDataAccessGateway categoryDataAccessGateway;

    private final LookupCategoryMapper lookupCategoryMapper;

    @Override
    public Object createCategory(CategoryRequest categoryRequest) {
        try {
            return processCreateCategoryRequest(categoryRequest);
        } catch (Exception e) {
            return presentRequestProcessingFailureResponse(e.getMessage());
        }
    }

    //#region Request Processing Methods
    Object processCreateCategoryRequest(CategoryRequest categoryRequest) {
        if (categoryExists(categoryRequest.getEnglishName())) {
            return presentBadRequestFailureResponse(CategoryUseCaseErrorMessages.CATEGORY_ALREADY_EXISTS);
        } else {
            LookupCategory lookupCategory = buildLookupEntity(categoryRequest);
            String categoryId = saveCategory(lookupCategory);
            CategoryResponse response = CategoryResponse.builder().id(categoryId).build();
            return presentSuccessResponse(response);
        }
    }
    //#endregion

    //#region Object Mapping Methods
    LookupCategory buildLookupEntity(CategoryRequest categoryRequest) {
        return lookupCategoryMapper.requestToEntity(categoryRequest);
    }
    //#endregion

    //#### region Data Access Methods
    private boolean categoryExists(String englishName) {
        return categoryDataAccessGateway.existsByEnglishName(englishName);
    }

    private String saveCategory(LookupCategory lookupCategory) {
        return categoryDataAccessGateway.save(lookupCategory);
    }
    //#endregion ###########################

    //#### region Presenter Methods
    private Object presentBadRequestFailureResponse(String errorMessage) {
        return categoryPresenter.presentBadRequestFailure(errorMessage);
    }

    private Object presentRequestProcessingFailureResponse(String errorMessage) {
        return categoryPresenter.presentRequestProcessingFailure(errorMessage);
    }

    private Object presentSuccessResponse(CategoryResponse response) {
        return categoryPresenter.presentSuccessResponse(response);
    }
    //#endregion
}
