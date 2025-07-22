package com.eps.epsapp.repository;

import com.eps.epsapp.entity.Profesional;
import com.eps.epsapp.entity.Tercero;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfesionalRepository extends JpaRepository<Profesional, Integer> {
    Profesional findByTercero(Tercero tercero); // ✅ importante
}
