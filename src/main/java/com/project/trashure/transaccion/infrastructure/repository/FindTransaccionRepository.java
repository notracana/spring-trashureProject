package com.project.trashure.transaccion.infrastructure.repository;

import com.project.trashure.transaccion.domain.Transaccion;
import com.project.trashure.transaccion.domain.TransaccionJpa;
import com.project.trashure.transaccion.infrastructure.repository.jpa.TransaccionRepositoryJpa;
import com.project.trashure.transaccion.infrastructure.repository.port.FindTransaccionPort;
import lombok.AllArgsConstructor;
import org.hibernate.sql.results.jdbc.internal.AbstractResultSetAccess;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class FindTransaccionRepository implements FindTransaccionPort {

    private TransaccionRepositoryJpa transaccionRepositoryJpa;
    @Override
    public List<Transaccion> findAll() {
        return transaccionRepositoryJpa.findAll();
    }

    @Override
    public List<Transaccion> findAllByIdComprador(String idComprador) {
        List<TransaccionJpa> transaccionJpaList = transaccionRepositoryJpa.findAllByIdComprador(idComprador);
        return transaccionJpaList.stream().map(Transaccion::new).collect(Collectors.toList());
    }

    @Override
    public List<Transaccion> findAllByIdVendedor(String idVendedor) {
        List<TransaccionJpa> transaccionJpaList = transaccionRepositoryJpa.findAllByIdComprador(idVendedor);
        return transaccionJpaList.stream().map(Transaccion::new).collect(Collectors.toList());
    }
}
