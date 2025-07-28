package com.ing.wallet.domain.model;

import com.ing.wallet.domain.vo.Tckn;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import java.time.LocalDate;

@Builder
@Getter
public class Customer {
    private Long id;
    @NonNull private String name;
    @NonNull private String surname;
    @NonNull private Tckn tckn;
    @NonNull private LocalDate createdAt;

}
