package com.projetospringboot.projeto.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.projetospringboot.projeto.entity.Category;
import com.projetospringboot.projeto.repository.CategoryRepository;

import jakarta.persistence.EntityNotFoundException;

/**
 * Classe de serviço responsável pelas regras de negócio da entidade Category.
 *
 * Aqui centralizamos operações de busca, inserção, atualização e remoção,
 * além de validações relacionadas à existência da categoria.
 */
@Service
public class CategoryService {

    /**
     * Repositório responsável pelo acesso aos dados de Category.
     */
    private final CategoryRepository categoryRepository;

    /**
     * Injeção de dependência por construtor.
     *
     * @param categoryRepository repositório de categorias
     */
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    // ===================== BUSCAR TODAS =====================

    /**
     * Retorna todas as categorias cadastradas.
     *
     * @return lista de categorias
     */
    @Transactional(readOnly = true)
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    // ===================== BUSCAR POR ID =====================

    /**
     * Busca uma categoria pelo ID.
     *
     * @param id identificador da categoria
     * @return categoria encontrada
     * @throws EntityNotFoundException se a categoria não existir
     */
    @Transactional(readOnly = true)
    public Category findById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada. ID: " + id));
    }

    // ===================== INSERIR =====================

    /**
     * Insere uma nova categoria no banco de dados.
     *
     * @param obj categoria a ser salva
     * @return categoria salva
     */
    @Transactional
    public Category insert(Category obj) {
        return categoryRepository.save(obj);
    }

    // ===================== DELETAR =====================

    /**
     * Remove uma categoria pelo ID.
     *
     * @param id identificador da categoria
     * @throws EntityNotFoundException se a categoria não existir
     */
    @Transactional
    public void delete(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new EntityNotFoundException("Categoria não encontrada. ID: " + id);
        }

        categoryRepository.deleteById(id);
    }

    // ===================== ATUALIZAR =====================

    /**
     * Atualiza uma categoria existente.
     *
     * Primeiro busca a categoria salva no banco,
     * depois aplica os novos dados e persiste novamente.
     *
     * @param id identificador da categoria
     * @param obj novos dados da categoria
     * @return categoria atualizada
     * @throws EntityNotFoundException se a categoria não existir
     */
    @Transactional
    public Category update(Long id, Category obj) {

        Category entity = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada. ID: " + id));

        updateData(entity, obj);

        return categoryRepository.save(entity);
    }

    // ===================== MÉTODO AUXILIAR =====================

    /**
     * Atualiza os campos permitidos da entidade Category.
     *
     * @param entity entidade original vinda do banco
     * @param obj objeto com os novos dados
     */
    private void updateData(Category entity, Category obj) {
        entity.setName(obj.getName());
    }
}