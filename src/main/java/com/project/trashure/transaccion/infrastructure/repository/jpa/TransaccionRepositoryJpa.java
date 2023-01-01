package com.project.trashure.transaccion.infrastructure.repository.jpa;

import com.project.trashure.transaccion.domain.Transaccion;
import com.project.trashure.transaccion.domain.TransaccionJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransaccionRepositoryJpa extends JpaRepository<TransaccionJpa, String> {
    List<TransaccionJpa> findAllByIdComprador(String idComprador);

    List<TransaccionJpa> findAllByIdVendedor(String idVendedor);

    List<TransaccionJpa> findAll();


}
