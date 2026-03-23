package com.projetospringboot.projeto.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.projetospringboot.projeto.entity.User;
import com.projetospringboot.projeto.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserService {

    private final UserRepository userRepository;

    //  Injeção por construtor (padrão profissional)
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // ===================== BUSCAR TODOS =====================
    public List<User> findAll() {
        return userRepository.findAll();
    }

    // ===================== BUSCAR POR ID =====================
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado. ID: " + id));
    }

    // ===================== INSERIR =====================
    public User insert(User obj) {
        return userRepository.save(obj);
    }

    // ===================== DELETAR =====================
    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException("Usuário não encontrado. ID: " + id);
        }
        userRepository.deleteById(id);
    }

    // ===================== ATUALIZAR =====================
    public User update(Long id, User obj) {

        try {
            User entity = userRepository.getReferenceById(id);
            updateData(entity, obj);
            return userRepository.save(entity);

        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("Id não encontrado: " + id);
        }
    }

    // ===================== MÉTODO AUXILIAR =====================
    private void updateData(User entity, User obj) {
        entity.setName(obj.getName());
        entity.setEmail(obj.getEmail());
        entity.setPhone(obj.getPhone());

        //  senha só atualiza se vier preenchida
        if (obj.getPassword() != null && !obj.getPassword().isBlank()) {
            entity.setPassword(obj.getPassword());
        }
    }
}