package com.eps.epsapp.repository;

import com.eps.epsapp.entity.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CitaRepository extends JpaRepository<Cita, Integer> {
    List<Cita> findByProfesional_IdProfesional(Integer idProfesional);
}
