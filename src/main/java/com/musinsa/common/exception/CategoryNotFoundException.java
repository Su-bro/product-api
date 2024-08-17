package com.musinsa.common.exception;

public class CategoryNotFoundException extends RuntimeException {

    public CategoryNotFoundException(String categoryName) {
        super("카테고리를 찾을 수 없습니다. 다시 확인해 주세요.");
    }
}
