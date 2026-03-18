public static final Long NOT_EXISTS_CATEGORY_ID = 0L;

@Test
public void getExistsCategoryIdShouldReturnCategory() {

    Category category = mock(Category.class);
    when(category.getId()).thenReturn(EXISTS_CATEGORY_ID);
    when(categoryRepository.findById(EXISTS_CATEGORY_ID)).thenReturn(Optional.of(category));

    Category categoryResponse = categoryService.get(EXISTS_CATEGORY_ID);

    assertNotNull(categoryResponse);
    assertEquals(EXISTS_CATEGORY_ID, category.getId());
}

@Test
public void getNotExistsCategoryIdShouldReturnNull() {

    when(categoryRepository.findById(NOT_EXISTS_CATEGORY_ID)).thenReturn(Optional.empty());

    Category category = categoryService.get(NOT_EXISTS_CATEGORY_ID);

    assertNull(category);
}
