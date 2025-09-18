package com.example.demo.dto.common;

import com.example.easyrestredoclylib.core.DocsDescription;

public enum LoginType {
    @DocsDescription("일반 로그인")
    NORMAL,

    @DocsDescription("소셜 로그인 (Google)")
    GOOGLE,

    @DocsDescription("소셜 로그인 (Facebook)")
    FACEBOOK,

    @DocsDescription("소셜 로그인 (네이버)")
    NAVER,

    @DocsDescription("소셜 로그인 (카카오)")
    KAKAO,

    @DocsDescription("관리자 로그인")
    ADMIN
}