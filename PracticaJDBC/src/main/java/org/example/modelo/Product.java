package org.example.modelo;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder

public class Product {
    private String id;
    private String name;
    private String author;
    private String editorial;
    private double price;
}
