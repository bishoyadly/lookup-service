package org.cleancoders.lookupservice.usecases;

public interface CategoryOutputBoundary {
    CategoryResponse presentBadRequestFailure(String categoryAlreadyExists);

    CategoryResponse presentSuccessResponse(CategoryResponse categoryResponse);
}
