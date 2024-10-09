package com.thomasmore.blc.labflow.repository;

import com.thomasmore.blc.labflow.entity.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolRepository extends JpaRepository<Rol, Integer> {
    public Rol findById(Long id);

}
