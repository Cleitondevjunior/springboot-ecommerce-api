package com.projetospringboot.projeto.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projetospringboot.projeto.entity.User;

/**
 * Interface responsável pelo acesso a dados da entidade User.
 * 
 * Ao estender JpaRepository, já herdamos automaticamente:
 * - save()
 * - findById()
 * - findAll()
 * - deleteById()
 * - existsById()
 * 
 * Ou seja, CRUD completo sem precisar implementar.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    // ===================== MÉTODOS PERSONALIZADOS =====================

    /**
     * Busca um usuário pelo email.
     * 
     * O Spring Data JPA cria automaticamente a query com base no nome do método.
     * 
     * @param email email do usuário
     * @return Optional contendo o usuário (se existir)
     */
    Optional<User> findByEmail(String email);

    /**
     * Verifica se já existe um usuário com o email informado.
     * 
     * Muito usado para validação (ex: evitar email duplicado).
     * 
     * @param email email a ser verificado
     * @return true se existir, false caso contrário
     */
    boolean existsByEmail(String email);
}