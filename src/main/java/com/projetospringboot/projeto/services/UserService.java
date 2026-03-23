package com.projetospringboot.projeto.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.projetospringboot.projeto.entity.User;
import com.projetospringboot.projeto.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;

/**
 * Classe de serviço responsável pelas regras de negócio da entidade User.
 *
 * Aqui concentramos a lógica da aplicação antes de acessar o banco
 * ou retornar os dados para a camada de controller.
 */
@Service
public class UserService {

    /**
     * Repositório responsável pelas operações de acesso a dados.
     */
    private final UserRepository userRepository;

    /**
     * Injeção de dependência por construtor.
     * Esse é o padrão recomendado no Spring.
     *
     * @param userRepository repositório da entidade User
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
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepository.findAll();
    }

    // ===================== BUSCAR POR ID =====================

    /**
     * Busca um usuário pelo ID.
     *
     * @param id identificador do usuário
     * @return usuário encontrado
     * @throws EntityNotFoundException se o usuário não existir
     */
    @Transactional(readOnly = true)
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado. ID: " + id));
    }

    // ===================== INSERIR =====================

    /**
     * Insere um novo usuário no banco de dados.
     *
     * Antes de salvar, valida se o email já está em uso.
     *
     * @param obj entidade User a ser salva
     * @return usuário salvo
     * @throws IllegalArgumentException se o email já estiver cadastrado
     */
    @Transactional
    public User insert(User obj) {

        validateEmailForInsert(obj.getEmail());

        return userRepository.save(obj);
    }

    // ===================== DELETAR =====================

    /**
     * Remove um usuário pelo ID.
     *
     * @param id identificador do usuário
     * @throws EntityNotFoundException se o usuário não existir
     */
    @Transactional
    public void delete(Long id) {

        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException("Usuário não encontrado. ID: " + id);
        }

        userRepository.deleteById(id);
    }

    // ===================== ATUALIZAR =====================

    /**
     * Atualiza um usuário existente.
     *
     * Busca o usuário já salvo no banco, aplica as alterações
     * recebidas e persiste novamente.
     *
     * @param id identificador do usuário
     * @param obj objeto com os novos dados
     * @return usuário atualizado
     * @throws EntityNotFoundException se o ID não existir
     * @throws IllegalArgumentException se o novo email já estiver em uso por outro usuário
     */
    @Transactional
    public User update(Long id, User obj) {

        User entity = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado. ID: " + id));

        validateEmailForUpdate(id, obj.getEmail());

        updateData(entity, obj);

        return userRepository.save(entity);
    }

    // ===================== MÉTODO AUXILIAR DE UPDATE =====================

    /**
     * Atualiza os dados permitidos da entidade.
     *
     * Esse método evita espalhar lógica de atualização dentro do método update.
     *
     * @param entity entidade original vinda do banco
     * @param obj objeto com os novos dados
     */
    private void updateData(User entity, User obj) {

        entity.setName(obj.getName());
        entity.setEmail(obj.getEmail());
        entity.setPhone(obj.getPhone());

        /**
         * Regra de negócio:
         * a senha só é alterada se vier preenchida.
         * Isso evita apagar a senha existente com null ou string vazia.
         */
        if (obj.getPassword() != null && !obj.getPassword().isBlank()) {
            entity.setPassword(obj.getPassword());
        }
    }

    // ===================== VALIDAÇÃO DE EMAIL NO INSERT =====================

    /**
     * Valida se o email informado já está cadastrado no momento da inserção.
     *
     * @param email email a ser validado
     * @throws IllegalArgumentException se já existir um usuário com esse email
     */
    private void validateEmailForInsert(String email) {
        if (email != null && userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Já existe um usuário cadastrado com o email: " + email);
        }
    }

    // ===================== VALIDAÇÃO DE EMAIL NO UPDATE =====================

    /**
     * Valida se o email informado já pertence a outro usuário.
     *
     * No update, o mesmo usuário pode manter o próprio email,
     * mas não pode usar o email de outro cadastro.
     *
     * @param id identificador do usuário que está sendo atualizado
     * @param email email a ser validado
     * @throws IllegalArgumentException se o email já estiver em uso por outro usuário
     */
    private void validateEmailForUpdate(Long id, String email) {

        if (email == null || email.isBlank()) {
            return;
        }

        userRepository.findByEmail(email)
                .filter(user -> !user.getId().equals(id))
                .ifPresent(user -> {
                    throw new IllegalArgumentException("Já existe um usuário cadastrado com o email: " + email);
                });
    }
}