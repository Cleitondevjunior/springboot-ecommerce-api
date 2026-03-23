package com.projetospringboot.projeto.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projetospringboot.projeto.entity.Category;

//  Interface responsável por acesso a dados da entidade Category
// JpaRepository já fornece CRUD completo automaticamente
public interface CategoryRepository extends JpaRepository<Category, Long> {

    // ===================== CONSULTAS PERSONALIZADAS =====================

    //  Buscar categoria por nome exato
    Optional<Category> findByName(String name);

    //  Buscar categorias contendo parte do nome
    List<Category> findByNameContainingIgnoreCase(String name);

    //  Verificar se já existe categoria com esse nome
    boolean existsByName(String name);
}