package com.ing.wallet.domain.repository;

import java.util.List;
public record CustomPage<T>(
        List<T> content,
        int pageNumber,
        int pageSize,
        long totalElements
) {}
