package com.ing.wallet.infrastructure.h2;

import com.ing.wallet.infrastructure.h2.entity.CustomerEntity;
import com.ing.wallet.infrastructure.h2.entity.WalletEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaWalletRepository extends JpaRepository<WalletEntity, Long>, JpaSpecificationExecutor<WalletEntity> {

}