package com.example.demo.dto.common;

import com.example.easyrestredoclylib.core.DocsDescription;

public enum UserStatus {
    @DocsDescription("활성 사용자")
    ACTIVE,

    @DocsDescription("비활성 사용자")
    INACTIVE,

    @DocsDescription("차단된 사용자")
    BLOCKED,

    @DocsDescription("삭제된 사용자")
    DELETED
}