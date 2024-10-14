package com.thomasmore.blc.labflow.service;

import com.thomasmore.blc.labflow.entity.Rol;
import com.thomasmore.blc.labflow.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class RolService {
    @Autowired
    private RolRepository rolRepository;

    // create
    public void create(Rol rol) {
        rolRepository.save(rol);
    }

    // read
    public List<Rol> read() {
        return rolRepository.findAll();
    }

    // update
    public ResponseEntity<Rol> update(Long id, Rol rol) {
        Rol updateRol = rolRepository.findById(id);
        if (updateRol != null) {
            updateRol.setNaam(rol.getNaam());
            rolRepository.save(updateRol);
            return new ResponseEntity<>(updateRol, org.springframework.http.HttpStatus.OK);
        }
        return new ResponseEntity<>(org.springframework.http.HttpStatus.NOT_FOUND);
    }

    // delete
    public ResponseEntity<Integer> delete(Long id) {
        Rol deleteRol = rolRepository.findById(id);
        if (deleteRol != null) {
            rolRepository.delete(deleteRol);
            return new ResponseEntity<>(rolRepository.findAll().size(), org.springframework.http.HttpStatus.OK);
        }
        return new ResponseEntity<>(rolRepository.findAll().size(), org.springframework.http.HttpStatus.NOT_FOUND);
    }
}
