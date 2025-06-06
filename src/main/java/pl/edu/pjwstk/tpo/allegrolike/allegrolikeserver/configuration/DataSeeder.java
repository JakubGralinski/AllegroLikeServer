package pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.configuration;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.models.Category;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.models.Order;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.models.OrderItem;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.models.OrderStatus;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.models.Product;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.models.Role;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.models.User;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.repositories.CategoryRepository;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.repositories.OrderRepository;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.repositories.ProductRepository;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.repositories.UserRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
@Profile("dev")
public class DataSeeder implements ApplicationRunner {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final PasswordEncoder passwordEncoder;

    public DataSeeder(UserRepository userRepository,
                      CategoryRepository categoryRepository,
                      ProductRepository productRepository,
                      OrderRepository orderRepository,
                      PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        System.out.println("Data Seeder is running...");

        User testUser;
        if (userRepository.count() == 0) {
            System.out.println("Seeding user...");
            testUser = new User("testuser", "test@example.com", passwordEncoder.encode("password"));
            testUser.setRole(Role.ROLE_USER);
            userRepository.save(testUser);
        } else {
            System.out.println("Users already exist. Getting the first user for seeding products/orders.");
            testUser = userRepository.findAll().get(0);
        }

        final var adminInDbOpt = userRepository.findByUsername("mrDanczak");
        User testAdmin = adminInDbOpt.orElseGet(() -> {
            final var admin = new User("mrDanczak", "testadmin@gmail.com", passwordEncoder.encode("password"));
            admin.setRole(Role.ROLE_ADMIN);
            userRepository.save(admin);
            return admin;
        });

        List<Category> categories;
        if (categoryRepository.count() == 0) {
            System.out.println("Seeding categories...");
            Category electronics = new Category("Electronics", null);
            Category books = new Category("Books", null);
            Category fashion = new Category("Fashion", null);
            Category home = new Category("Home", null);
            categories = categoryRepository.saveAll(List.of(electronics, books, fashion, home));
        } else {
            System.out.println("Categories already exist.");
            categories = categoryRepository.findAll();
        }

        List<Product> products;
        if (productRepository.count() == 0 && !categories.isEmpty()) {
            System.out.println("Seeding products...");
            products = new ArrayList<>();
            products.add(new Product("Laptop Pro", "A powerful laptop.", new BigDecimal("1200.00"), 50, testAdmin, categories.get(0), "url_laptop"));
            products.add(new Product("Smartphone X", "The latest smartphone.", new BigDecimal("800.00"), 150, testAdmin, categories.get(0), "url_phone"));
            products.add(new Product("History of Time", "A popular science book.", new BigDecimal("25.50"), 200, testAdmin, categories.get(1), "url_book"));
            products.add(new Product("Leather Jacket", "A stylish leather jacket.", new BigDecimal("150.00"), 100, testAdmin, categories.get(2), "url_jacket"));
            products.add(new Product("Coffee Maker", "Brews delicious coffee.", new BigDecimal("75.00"), 80, testAdmin, categories.get(3), "url_coffee"));
            products.add(new Product("Wireless Headphones", "Noise-cancelling headphones.", new BigDecimal("250.00"), 120, testAdmin, categories.get(0), "url_headphones"));
            productRepository.saveAll(products);
        } else {
             System.out.println("Products already exist or no categories found to link to.");
             products = productRepository.findAll();
        }

        if (orderRepository.count() == 0 && !products.isEmpty()) {
            System.out.println("Seeding orders...");
            Random random = new Random();
            for (int i = 0; i < 50; i++) {
                Product product1 = products.get(random.nextInt(products.size()));
                Product product2 = products.get(random.nextInt(products.size()));

                BigDecimal total;
                List<OrderItem> currentOrderItems = new ArrayList<>();

                if (product1.getId().equals(product2.getId())) {
                    int quantity = random.nextInt(1, 3) + random.nextInt(1, 3);
                    OrderItem item = new OrderItem(null, product1, quantity);
                    currentOrderItems.add(item);
                    total = product1.getPrice().multiply(new BigDecimal(item.getQuantity()));
                } else {
                    OrderItem item1 = new OrderItem(null, product1, random.nextInt(1, 3));
                    OrderItem item2 = new OrderItem(null, product2, random.nextInt(1, 3));
                    currentOrderItems.add(item1);
                    currentOrderItems.add(item2);
                    total = product1.getPrice().multiply(new BigDecimal(item1.getQuantity()))
                                         .add(product2.getPrice().multiply(new BigDecimal(item2.getQuantity())));
                }

                Order order = new Order(
                        LocalDate.now().minusDays(random.nextInt(365)),
                        testUser,
                        OrderStatus.FULFILLED,
                        total,
                        null
                );

                for(OrderItem item : currentOrderItems) {
                    item.setOrder(order);
                }
                order.setOrderItems(currentOrderItems);

                orderRepository.save(order);
            }
             System.out.println("Order seeding complete.");
        } else {
             System.out.println("Orders already exist or no products found to create orders with. Skipping order seeding.");
        }

        System.out.println("Data Seeder finished.");
    }
}