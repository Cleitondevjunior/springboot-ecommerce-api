package com.projetospringboot.projeto.config;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.projetospringboot.projeto.entity.*;
import com.projetospringboot.projeto.entity.enums.OrderStatus;
import com.projetospringboot.projeto.repository.*;

/**
 * Classe responsável por popular o banco de dados em ambiente de teste.
 *
 * Executa automaticamente ao iniciar a aplicação com o profile "test".
 */
@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final OrderItemRepository orderItemRepository;

    public TestConfig(UserRepository userRepository,
                      OrderRepository orderRepository,
                      CategoryRepository categoryRepository,
                      ProductRepository productRepository,
                      OrderItemRepository orderItemRepository) {

        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.orderItemRepository = orderItemRepository;
    }

    @Override
    public void run(String... args) {

        // ===================== USERS =====================
        User u1 = new User(null, "Maria Brown", "maria@gmail.com", "988888888", "123456");
        User u2 = new User(null, "Alex Green", "alex@gmail.com", "977777777", "123456");

        userRepository.saveAll(List.of(u1, u2));

        // ===================== ORDERS =====================
        Order o1 = new Order(null, Instant.parse("2019-06-20T19:53:07Z"), OrderStatus.WAITING_PAYMENT, u1);
        Order o2 = new Order(null, Instant.parse("2019-07-21T03:42:10Z"), OrderStatus.PAID, u2);
        Order o3 = new Order(null, Instant.parse("2019-07-22T15:21:22Z"), OrderStatus.CANCELED, u1);

        orderRepository.saveAll(List.of(o1, o2, o3));

        // ===================== CATEGORIES =====================
        Category cat1 = new Category(null, "Electronics");
        Category cat2 = new Category(null, "Books");
        Category cat3 = new Category(null, "Computers");

        categoryRepository.saveAll(List.of(cat1, cat2, cat3));

        // ===================== PRODUCTS =====================
        Product p1 = new Product(null, "The Lord of the Rings", "Lorem ipsum",
                new BigDecimal("90.50"), "");
        Product p2 = new Product(null, "Smart TV", "Lorem ipsum",
                new BigDecimal("2190.00"), "");
        Product p3 = new Product(null, "Macbook Pro", "Lorem ipsum",
                new BigDecimal("1250.00"), "");
        Product p4 = new Product(null, "PC Gamer", "Lorem ipsum",
                new BigDecimal("1200.00"), "");
        Product p5 = new Product(null, "Rails for Dummies", "Lorem ipsum",
                new BigDecimal("100.99"), "");

        productRepository.saveAll(List.of(p1, p2, p3, p4, p5));

        // ===================== RELACIONAMENTO PRODUCT-CATEGORY =====================
        p1.getCategories().add(cat2);

        p2.getCategories().addAll(List.of(cat1, cat3));

        p3.getCategories().add(cat3);
        p4.getCategories().add(cat3);

        p5.getCategories().add(cat2);

        productRepository.saveAll(List.of(p1, p2, p3, p4, p5));

        // ===================== ORDER ITEMS =====================
        OrderItem oi1 = new OrderItem(o1, p1, 2, p1.getPrice());
        OrderItem oi2 = new OrderItem(o1, p3, 1, p3.getPrice());
        OrderItem oi3 = new OrderItem(o2, p3, 2, p3.getPrice());

        // 🔥 ativa desconto automático (quantidade >= 5)
        OrderItem oi4 = new OrderItem(o3, p5, 6, p5.getPrice());

        orderItemRepository.saveAll(List.of(oi1, oi2, oi3, oi4));

        // ===================== PAYMENT =====================
        Payment pay1 = new Payment(null,
                Instant.parse("2019-06-20T21:53:07Z"),
                o1);

        // relação bidirecional
        o1.setPayment(pay1);

        orderRepository.save(o1);
    }
}