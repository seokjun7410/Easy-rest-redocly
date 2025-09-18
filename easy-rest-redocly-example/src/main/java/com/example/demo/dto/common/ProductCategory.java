package com.example.demo.dto.common;

import com.example.easyrestredoclylib.core.DocsDescription;

public enum ProductCategory {
    @DocsDescription("전자제품")
    ELECTRONICS,

    @DocsDescription("의류")
    CLOTHING,

    @DocsDescription("도서")
    BOOKS,

    @DocsDescription("홈앤가든")
    HOME_GARDEN,

    @DocsDescription("스포츠")
    SPORTS,

    @DocsDescription("기타")
    OTHER
}