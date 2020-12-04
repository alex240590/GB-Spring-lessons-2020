package ru.geekbrains;

import org.hibernate.cfg.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    private static Long orderNumber = 1L;

    private static EntityManagerFactory emf = new Configuration()
            .configure("hibernate.cfg.xml")
            .buildSessionFactory();

    public static void main(String[] args) {
        fillDB();

        EntityManager em = emf.createEntityManager();

        List<Product> prod = em.createQuery("from Product", Product.class)
                .getResultList();

        List<Customer> customer = em.createQuery("from Customer", Customer.class)
                .getResultList();

        createOrder(customer.get(0), new ArrayList<Product>(Arrays.asList(prod.get(0), prod.get(2))), 5);

        Order order = em.find(Order.class, orderNumber-1);
        System.out.println(order);

        em.close();
    }

    private static void createOrder(Customer customer, ArrayList<Product> products, int quantity){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Order o = new Order(null, orderNumber, customer);
        em.persist(o);
        em.getTransaction().commit();
        Order order = em.find(Order.class, orderNumber);

        em.getTransaction().begin();
        for (Product p: products){
            OrderLine ol = new OrderLine(null, order, p, quantity, p.getPrice());
            em.persist(ol);
        }
        em.getTransaction().commit();

        em.close();
        orderNumber++;
    }



    private static void fillDB(){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        //create customers
        Customer c1 = new Customer(null, "Alex");
        Customer c2 = new Customer(null, "Dan");
        Customer c3 = new Customer(null, "Max");

        em.persist(c1);
        em.persist(c2);
        em.persist(c3);

        //create products
        Product p1 = new Product(null, "iPhone 8", new BigDecimal(50000));
        Product p2 = new Product(null, "iPhone12 Pro", new BigDecimal(100000));
        Product p3 = new Product(null, "iPad Air", new BigDecimal(60000));
        Product p4 = new Product(null, "iPad Pro", new BigDecimal(80000));
        Product p5 = new Product(null, "Apple Watch 5", new BigDecimal(20000));

        em.persist(p1);
        em.persist(p2);
        em.persist(p3);
        em.persist(p4);
        em.persist(p5);

      //create orders
        Order o1 = new Order(null, 1L, c1);
        Order o2 = new Order(null, 2L, c2);
        Order o3 = new Order(null, 3L, c3);
        Order o4 = new Order(null, 4L, c3);
        em.persist(o1);
        em.persist(o2);
        em.persist(o3);
        em.persist(o4);

        //create order lines
        OrderLine ol1 = new OrderLine(null, o1, p1, 5, p1.getPrice());
        OrderLine ol2 = new OrderLine(null,o1, p2, 5, p2.getPrice());
        OrderLine ol3 = new OrderLine(null,o2, p1, 10, p1.getPrice());
        OrderLine ol4 = new OrderLine(null,o2, p2, 10, p2.getPrice());
        OrderLine ol5 = new OrderLine(null,o3, p3, 20, p3.getPrice());
        OrderLine ol6 = new OrderLine(null,o3, p4, 20, p4.getPrice());
        OrderLine ol7 = new OrderLine(null,o4, p5, 3, p5.getPrice());
        em.persist(ol1);
        em.persist(ol2);
        em.persist(ol3);
        em.persist(ol4);
        em.persist(ol5);
        em.persist(ol6);
        em.persist(ol7);

        em.getTransaction().commit();

        em.close();

    }




}

        // INSERT
//        EntityManager em = emFactory.createEntityManager();
//
//        em.getTransaction().begin();
//        Product product = new Product(null, "Some product 1", "Some description 1", new BigDecimal(14));
//        Product product1 = new Product(null, "iPad", "Super table", new BigDecimal(1400));
//        Product product2 = new Product(null, "iPhone", "Super mobile phone", new BigDecimal(900));
//        em.persist(product);
//        em.persist(product1);
//        em.persist(product2);
//        em.getTransaction().commit();
//
//        em.close();

        // SELECT
//        EntityManager em = emFactory.createEntityManager();
//
//        Product product = em.find(Product.class, 1L);
//        System.out.println(product);
//
//        // HQL, JPQL
//        List<Product> products = em.createQuery("from Product", Product.class)
//                .getResultList();
//        products.forEach(System.out::println);
//
//        List<Product> products1 = em.createQuery("from Product p where p.name = :name ", Product.class)
//                .setParameter("name", "iPad")
//                .getResultList();
//        products1.forEach(System.out::println);

        // SQL
//        em.createNativeQuery("select * from products", Product.class)
//                .getResultList();
//        products.forEach(System.out::println);

//        em.close();

        // UPDATE
//        EntityManager em = emFactory.createEntityManager();
//
//        Product product = em.find(Product.class, 1L);
//        System.out.println(product);
//
//        em.getTransaction().begin();
//        product.setName("Macbook pro");
//        em.getTransaction().commit();
//
//        em.close();

        // DELETE
//        EntityManager em = emFactory.createEntityManager();
//
//        em.getTransaction().begin();
//        Product product = em.find(Product.class, 1L);
//        em.remove(product);
//        em.getTransaction().commit();
//
//        List<Product> products = em.createQuery("from Product", Product.class)
//                .getResultList();
//        products.forEach(System.out::println);
//
//        em.close();

        // INSERT one-to-many
//        EntityManager em = emFactory.createEntityManager();
//
//        em.getTransaction().begin();
//
//        em.persist(new Category(null, "Laptop"));
//        em.persist(new Category(null, "Phone"));
//        em.persist(new Category(null, "Tablet"));
//
//        Category laptop = em.createNamedQuery("findByName", Category.class)
//                .setParameter("name", "Laptop")
//                .getSingleResult();
//        Category phone = em.createNamedQuery("findByName", Category.class)
//                .setParameter("name", "Phone")
//                .getSingleResult();
//        Category tablet = em.createNamedQuery("findByName", Category.class)
//                .setParameter("name", "Tablet")
//                .getSingleResult();
//
//        em.persist(new Product(null, "Macbook pro", "Super laptop", new BigDecimal(3000), laptop));
//        em.persist(new Product(null, "iPad", "Super tablet", new BigDecimal(3000), tablet));
//        em.persist(new Product(null, "iPhone", "Super phone", new BigDecimal(3000), phone));
//
//        em.getTransaction().commit();
//
//        em.close();

        // SELECT one-to-many
//        EntityManager em = emFactory.createEntityManager();
//
//        List<Product> products = em.createQuery("select p from Product p inner join p.category c", Product.class)
//                .getResultList();
//        products.forEach(System.out::println);
//    }
//}
