package com.projetospringboot.projeto.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.projetospringboot.projeto.entity.Category;
import com.projetospringboot.projeto.repository.CategoryRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    //  Injeção por construtor 
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    // ===================== BUSCAR TODAS =====================
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    // ===================== BUSCAR POR ID =====================
    public Category findById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada. ID: " + id));
    }

    // ===================== INSERIR =====================
    public Category insert(Category obj) {
        return categoryRepository.save(obj);
    }

    // ===================== DELETAR =====================
    public void delete(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new EntityNotFoundException("Categoria não encontrada. ID: " + id);
        }
        categoryRepository.deleteById(id);
    }

    // ===================== ATUALIZAR =====================
    public Category update(Long id, Category obj) {

        try {
            Category entity = categoryRepository.getReferenceById(id);
            updateData(entity, obj);
            return categoryRepository.save(entity);

        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("Id não encontrado: " + id);
        }
    }

    // ===================== MÉTODO AUXILIAR =====================
    private void updateData(Category entity, Category obj) {
        entity.setName(obj.getName());
    }
}