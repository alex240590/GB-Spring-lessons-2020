package ru.geekbrains;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long number;

    @ManyToOne
    private Customer customer;

    @OneToMany(mappedBy = "id")
    private List<OrderLine> lines;


    public Order(){}

    public Order(Long id, Long number, Customer customer) {
        this.id = id;
        this.number = number;
        this.customer = customer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }


    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<OrderLine> getLines() {
        return lines;
    }

    public void setLines(List<OrderLine> lines) {
        this.lines = lines;
    }


    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", customer=" + customer.getName() +
                '}';
    }

}
