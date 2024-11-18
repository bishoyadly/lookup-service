package org.cleancoders.lookupservice.usecases;

import org.cleancoders.lookupservice.entities.LookupCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(value = MockitoExtension.class)
class LookupCategoryUseCaseTests {

    @Mock
    private CategoryOutputBoundary categoryPresenter;
    @Mock
    private CategoryDataAccessGateway categoryDataAccessGateway;

    private CategoryInputBoundary categoryUseCaseInteractor;

    @BeforeEach
    void setUp() {
        LookupCategoryMapper lookupCategoryMapper = new LookupCategoryMapperImpl();
        categoryUseCaseInteractor = new CategoryUseCaseInteractor(categoryPresenter, categoryDataAccessGateway, lookupCategoryMapper);
    }

    @Test
    void createCategory_caseAlreadyExists() {
        CategoryRequest categoryRequest = buildCategoryRequest();
        CategoryResponse expectedResponse = buildEmptyCategoryResponse();
        when(categoryDataAccessGateway.existsByEnglishName(categoryRequest.getEnglishName())).thenReturn(true);
        when(categoryPresenter.presentBadRequestFailure(anyString())).thenReturn(expectedResponse);

        CategoryResponse categoryResponse = (CategoryResponse) categoryUseCaseInteractor.createCategory(categoryRequest);

        verify(categoryDataAccessGateway, times(1))
                .existsByEnglishName(categoryRequest.getEnglishName());
        verify(categoryPresenter, times(1))
                .presentBadRequestFailure(CategoryUseCaseErrorMessages.CATEGORY_ALREADY_EXISTS);
        assertEquals(expectedResponse, categoryResponse);
    }

    @Test
    void createCategory_caseSuccessResponse() {
        CategoryRequest categoryRequest = buildCategoryRequest();
        CategoryResponse expectedResponse = buildCategoryResponse();
        LookupCategory expectedLookupCategory = buildLookupCategory();
        when(categoryDataAccessGateway.existsByEnglishName(categoryRequest.getEnglishName())).thenReturn(false);
        when(categoryPresenter.presentSuccessResponse(any())).thenReturn(expectedResponse);

        CategoryResponse categoryResponse = (CategoryResponse) categoryUseCaseInteractor.createCategory(categoryRequest);

        verify(categoryDataAccessGateway, times(1))
                .existsByEnglishName(categoryRequest.getEnglishName());
        assertLookupCategoryFields(expectedLookupCategory);
        verify(categoryPresenter, times(1)).presentSuccessResponse(any(CategoryResponse.class));
        assertEquals(expectedResponse, categoryResponse);
    }


    //#region Test Utils Methods
    private CategoryRequest buildCategoryRequest() {
        return CategoryRequest.builder()
                .englishName("governorate")
                .arabicName("محافظات")
                .build();
    }


    private CategoryResponse buildEmptyCategoryResponse() {
        return CategoryResponse.builder()
                .englishName("")
                .arabicName("")
                .build();
    }


    private CategoryResponse buildCategoryResponse() {
        return CategoryResponse.builder()
                .id(UUID.randomUUID().toString())
                .englishName("governorate")
                .arabicName("محافظات")
                .build();
    }

    private LookupCategory buildLookupCategory() {
        LookupCategory lookupCategory = new LookupCategory();
        lookupCategory.setId(UUID.randomUUID().toString());
        lookupCategory.setEnglishName("governorate");
        lookupCategory.setArabicName("محافظات");
        return lookupCategory;
    }

    private void assertLookupCategoryFields(LookupCategory expected) {
        ArgumentCaptor<LookupCategory> lookupCategoryArgumentCaptor = ArgumentCaptor.forClass(LookupCategory.class);
        verify(categoryDataAccessGateway, times(1)).save(lookupCategoryArgumentCaptor.capture());
        LookupCategory actual = lookupCategoryArgumentCaptor.getValue();
        assertEquals(expected.getEnglishName(), actual.getEnglishName());
        assertEquals(expected.getArabicName(), actual.getArabicName());
        assertEquals(expected.getLookupItemList(), actual.getLookupItemList());
    }
    //#endregion

}
