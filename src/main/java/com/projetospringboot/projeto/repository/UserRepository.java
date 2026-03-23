package com.projetospringboot.projeto.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projetospringboot.projeto.entity.User;

//  Interface responsável por acesso a dados da entidade User
// JpaRepository já fornece CRUD completo automaticamente
public interface UserRepository extends JpaRepository<User, Long> {

    // ===================== MÉTODOS PERSONALIZADOS =====================

    //  Buscar usuário por email (usado em login futuramente)
    Optional<User> findByEmail(String email);

    //  Verifica se já existe email cadastrado
    boolean existsByEmail(String email);
}