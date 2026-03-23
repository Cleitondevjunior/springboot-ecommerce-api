package com.projetospringboot.projeto.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.projetospringboot.projeto.entity.Product;
import com.projetospringboot.projeto.exception.ResourceNotFoundException;
import com.projetospringboot.projeto.repository.ProductRepository;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // ===================== BUSCAR TODOS =====================
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    // ===================== BUSCAR POR ID =====================
    public Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));
    }

    // ===================== INSERIR =====================
    public Product insert(Product obj) {
        return productRepository.save(obj);
    }

    // ===================== DELETAR =====================
    public void delete(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException(id);
        }
        productRepository.deleteById(id);
    }

    // ===================== ATUALIZAR =====================
    public Product update(Long id, Product obj) {

        Product entity = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));

        updateData(entity, obj);
        return productRepository.save(entity);
    }

    // ===================== MÉTODO AUXILIAR =====================
    private void updateData(Product entity, Product obj) {
        entity.setName(obj.getName());
        entity.setDescription(obj.getDescription());
        entity.setPrice(obj.getPrice());
        entity.setImgUrl(obj.getImgUrl());
    }
}