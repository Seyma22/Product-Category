package com.example.productcategory.entity;

import lombok.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.stream.Stream;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "product")
class Product {

    public Product(String name) {
        this.name = name;
    }

    @Id
    @GeneratedValue
    private Long id;
    @Column(unique=true)
    private String name;

    @Column(unique=true)
    private String code; //categorycode+3 hane

    @ManyToOne
    private Category category;
    @OneToMany
    private ProductBarcode productBarcode;

    private String status;
    @ManyToOne
    private String brand;
    @ManyToOne
    private Unit unit;

}

@RepositoryRestResource
interface ProductRepository extends JpaRepository<Product, Integer> {}

@Component
class ProductInitializer implements CommandLineRunner {

    private final ProductRepository productRepository;

    ProductInitializer(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Stream.of("Lining", "PUMA", "Bad Boy", "Air Jordan", "Nike", "Adidas", "Reebok")
                .forEach(product -> productRepository.save(new Product(product)));

        productRepository.findAll().forEach(System.out::println);
    }
}
