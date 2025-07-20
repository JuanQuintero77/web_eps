package com.eps.epsapp.service.impl;

import com.eps.epsapp.entity.PlanSalud;
import com.eps.epsapp.repository.PlanSaludRepository;
import com.eps.epsapp.service.PlanSaludService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlanSaludServiceImpl implements PlanSaludService {

    @Autowired
    private PlanSaludRepository planSaludRepository;

    @Override
    public List<PlanSalud> listarTodos() {
        return planSaludRepository.findAll();
    }

    @Override
    public Optional<PlanSalud> obtenerPorId(Integer id) {
        return planSaludRepository.findById(id);
    }

    @Override
    public PlanSalud guardar(PlanSalud planSalud) {
        return planSaludRepository.save(planSalud);
    }

    @Override
    public PlanSalud actualizar(Integer id, PlanSalud planSaludActualizado) {
        Optional<PlanSalud> optionalPlan = planSaludRepository.findById(id);
        if (optionalPlan.isPresent()) {
            PlanSalud planExistente = optionalPlan.get();
            planExistente.setNombrePlan(planSaludActualizado.getNombrePlan());
            planExistente.setCobertura(planSaludActualizado.getCobertura());
            planExistente.setCostoMensual(planSaludActualizado.getCostoMensual()); // ← FALTABA
            return planSaludRepository.save(planExistente);
        }
        return null;
    }

    @Override
    public void eliminar(Integer id) {
        planSaludRepository.deleteById(id);
    }
}
