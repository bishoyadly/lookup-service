package org.cleancoders.lookupservice.usecases;

public interface CategoryOutputBoundary {
    Object presentBadRequestFailure(String errorMessage);

    Object presentRequestProcessingFailure(String errorMessage);

    Object presentSuccessResponse(CategoryResponse categoryResponse);
}
