package com.projetospringboot.projeto.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.projetospringboot.projeto.entity.Product;
import com.projetospringboot.projeto.exception.ResourceNotFoundException;
import com.projetospringboot.projeto.repository.ProductRepository;

/**
 * Classe de serviço responsável pelas regras de negócio da entidade Product.
 *
 * Aqui centralizamos as operações de:
 * - busca
 * - inserção
 * - atualização
 * - remoção
 *
 * Também concentramos validações e tratamento de existência do recurso.
 */
@Service
public class ProductService {

    /**
     * Repositório responsável pelo acesso aos dados de Product.
     */
    private final ProductRepository productRepository;

    /**
     * Injeção de dependência por construtor.
     *
     * @param productRepository repositório de produtos
     */
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // ===================== BUSCAR TODOS =====================

    /**
     * Retorna todos os produtos cadastrados.
     *
     * @return lista de produtos
     */
    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    // ===================== BUSCAR POR ID =====================

    /**
     * Busca um produto pelo ID.
     *
     * @param id identificador do produto
     * @return produto encontrado
     * @throws ResourceNotFoundException se o produto não existir
     */
    @Transactional(readOnly = true)
    public Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));
    }

    // ===================== INSERIR =====================

    /**
     * Insere um novo produto no banco de dados.
     *
     * @param obj produto a ser salvo
     * @return produto salvo
     */
    @Transactional
    public Product insert(Product obj) {
        return productRepository.save(obj);
    }

    // ===================== DELETAR =====================

    /**
     * Remove um produto pelo ID.
     *
     * @param id identificador do produto
     * @throws ResourceNotFoundException se o produto não existir
     */
    @Transactional
    public void delete(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException(id);
        }

        productRepository.deleteById(id);
    }

    // ===================== ATUALIZAR =====================

    /**
     * Atualiza os dados de um produto existente.
     *
     * Primeiro busca o produto salvo no banco,
     * depois aplica os novos dados e persiste novamente.
     *
     * @param id identificador do produto
     * @param obj novos dados do produto
     * @return produto atualizado
     * @throws ResourceNotFoundException se o produto não existir
     */
    @Transactional
    public Product update(Long id, Product obj) {

        Product entity = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));

        updateData(entity, obj);

        return productRepository.save(entity);
    }

    // ===================== MÉTODO AUXILIAR =====================

    /**
     * Atualiza os campos permitidos da entidade Product.
     *
     * @param entity entidade original vinda do banco
     * @param obj objeto com os novos dados
     */
    private void updateData(Product entity, Product obj) {
        entity.setName(obj.getName());
        entity.setDescription(obj.getDescription());
        entity.setPrice(obj.getPrice());
        entity.setImgUrl(obj.getImgUrl());
    }
}