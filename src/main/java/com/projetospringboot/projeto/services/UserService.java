package com.projetospringboot.projeto.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.projetospringboot.projeto.entity.User;
import com.projetospringboot.projeto.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;

/**
 * Classe de serviço responsável pelas regras de negócio da entidade User.
 * 
 * Aqui não lidamos com HTTP, apenas com lógica e persistência.
 */
@Service
public class UserService {

    /**
     * Repositório responsável pelo acesso ao banco de dados.
     */
    private final UserRepository userRepository;

    /**
     * Injeção de dependência via construtor (boa prática).
     * 
     * @param userRepository repositório de usuários
     */
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // ===================== BUSCAR TODOS =====================

    /**
     * Retorna todos os usuários cadastrados.
     * 
     * @return lista de usuários
     */
    public List<User> findAll() {
        return userRepository.findAll();
    }

    // ===================== BUSCAR POR ID =====================

    /**
     * Busca um usuário pelo ID.
     * 
     * @param id identificador do usuário
     * @return usuário encontrado
     * @throws EntityNotFoundException se não encontrar
     */
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> 
                    new EntityNotFoundException("Usuário não encontrado. ID: " + id));
    }

    // ===================== INSERIR =====================

    /**
     * Insere um novo usuário no banco.
     * 
     * @param obj usuário a ser salvo
     * @return usuário salvo
     */
    public User insert(User obj) {
        return userRepository.save(obj);
    }

    // ===================== DELETAR =====================

    /**
     * Deleta um usuário pelo ID.
     * 
     * @param id identificador do usuário
     * @throws EntityNotFoundException se o usuário não existir
     */
    public void delete(Long id) {

        // Verifica se existe antes de deletar (boa prática)
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException("Usuário não encontrado. ID: " + id);
        }

        userRepository.deleteById(id);
    }

    // ===================== ATUALIZAR =====================

    /**
     * Atualiza os dados de um usuário existente.
     * 
     * @param id identificador do usuário
     * @param obj novos dados do usuário
     * @return usuário atualizado
     */
    public User update(Long id, User obj) {

        try {
            // Busca referência da entidade (sem ir direto ao banco inicialmente)
            User entity = userRepository.getReferenceById(id);

            // Atualiza apenas os campos permitidos
            updateData(entity, obj);

            // Salva alterações
            return userRepository.save(entity);

        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("Id não encontrado: " + id);
        }
    }

    // ===================== MÉTODO AUXILIAR =====================

    /**
     * Atualiza os dados da entidade com base no objeto recebido.
     * 
     * Esse método evita sobrescrever campos indevidamente.
     */
    private void updateData(User entity, User obj) {

        // Atualiza campos básicos
        entity.setName(obj.getName());
        entity.setEmail(obj.getEmail());
        entity.setPhone(obj.getPhone());

        /**
         * Regra importante:
         * A senha só será atualizada se vier preenchida.
         * Isso evita apagar a senha existente com valor null.
         */
        if (obj.getPassword() != null && !obj.getPassword().isBlank()) {
            entity.setPassword(obj.getPassword());
        }
    }
}