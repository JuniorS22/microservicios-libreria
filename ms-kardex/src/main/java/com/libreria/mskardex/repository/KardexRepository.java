package com.libreria.mskardex.repository;

import com.libreria.mskardex.entity.Kardex;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KardexRepository extends JpaRepository<Kardex,Integer> {
}
