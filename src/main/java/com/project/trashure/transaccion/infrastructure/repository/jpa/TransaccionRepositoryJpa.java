package com.project.trashure.transaccion.infrastructure.repository.jpa;

import com.project.trashure.transaccion.domain.Transaccion;
import com.project.trashure.transaccion.domain.TransaccionJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransaccionRepositoryJpa extends JpaRepository<TransaccionJpa, Integer> {
    List<TransaccionJpa> findAllByIdComprador(Integer idComprador);

    List<TransaccionJpa> findAllByIdVendedor(Integer idVendedor);

    List<TransaccionJpa> findAll();


}
