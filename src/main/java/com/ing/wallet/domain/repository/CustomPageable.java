package com.ing.wallet.domain.repository;

public record CustomPageable(
        Integer pageNumber,
        Integer pageSize
) {}
