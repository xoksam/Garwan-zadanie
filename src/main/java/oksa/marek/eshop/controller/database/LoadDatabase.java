package oksa.marek.eshop.controller.database;

import oksa.marek.eshop.controller.repositories.*;
import oksa.marek.eshop.controller.services.OrderService;
import oksa.marek.eshop.controller.services.ProductService;
import oksa.marek.eshop.model.enums.EAnimalCategory;
import oksa.marek.eshop.model.enums.EUserRole;
import oksa.marek.eshop.model.entities.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class LoadDatabase {


    @Bean
    public CommandLineRunner initUsers(IUserRepository userRepository) {
        return args -> {
            System.out.println("Preloading: " + userRepository.save(new User("FeroTestovac", "feroTestovac@gmail.com")));
            System.out.println("Preloading: " + userRepository.save(new User("mrkvac", "mrkvac@centrum.sk")));
            System.out.println("Preloading: " + userRepository.save(new User("admin", "admin@eshop.sk", EUserRole.ROLE_ADMIN)));
            System.out.println("Preloading: " + userRepository.save(new User("testUser", "tstUsr@gmail.com")));
        };
    }

    @Bean
    public CommandLineRunner initAnimalCategories(IAnimalCategoryRepository animalCategoryRepository) {
        return args -> Arrays.stream(EAnimalCategory.class.getEnumConstants()).map(a ->
                "Preloading: " + animalCategoryRepository.save(new AnimalCategory(a))).forEach(System.out::println);
    }

    @Bean
    public CommandLineRunner initImages(IGalleryRepository imageRepository) {
        List<String> tickPreventionSprayUrls = Arrays.asList("https://images-na.ssl-images-amazon.com/images/I/51PhNZ1-tVL._SL1287_.jpg",
                "https://www.herbalstrategi.com/wp-content/uploads/2016/07/Yespray-WithBox.png");

        List<String> lightUpLeashUrls = Arrays.asList("https://images-na.ssl-images-amazon.com/images/I/71PuoxrYxPL._SX425_.jpg",
                "https://images-na.ssl-images-amazon.com/images/I/71S6mUBj36L._SY355_.jpg",
                "https://cdn.shopify.com/s/files/1/1729/2401/products/product-image-203586122_large.jpg?v=1488855498",
                "https://doglab.com/wp-content/uploads/Walking-dog-in-park-at-night-with-Illumiseen-rechargable-LED-dog-leash.jpg");
        List<String> catLeashUrls = Arrays.asList("https://images.baxterboo.com/global/images/products/large/adventure-kitty-cat-harness-with-leashrc-pet-black-2309.jpg",
                "https://images-na.ssl-images-amazon.com/images/I/71NqQRjvTYL._SX425_.jpg",
                "https://mlstaticquic-a.akamaihd.net/kzhareen-arnes-para-gato-con-correa-para-gatito-cachorro-D_NQ_NP_655161-MLU29692233827_032019-F.jpg");

        return args -> {
            System.out.println("Preloading: " + imageRepository.save(new Gallery(tickPreventionSprayUrls)));
            System.out.println("Preloading: " + imageRepository.save(new Gallery(lightUpLeashUrls)));
            System.out.println("Preloading: " + imageRepository.save(new Gallery(catLeashUrls)));
        };
    }

    @Bean
    public CommandLineRunner initProducts(IProductRepository productRepository, IGalleryRepository galleryRepository,
                                          IAnimalCategoryRepository animalCategoryRepository) {
        return args -> {
            Gallery tickSprayGallery = galleryRepository.findAll().get(0);
            System.out.println("Gallery: " + tickSprayGallery);
            List<AnimalCategory> tickSprayCategories = animalCategoryRepository.findAll();

            Product tickPreventionSpray = new Product(tickSprayCategories, 99.0D,
                    "Spray that will help your pet get rid of ticks",
                    "Tick prevention spray for any pets ", tickSprayGallery);
            System.out.println("Preloading: " + productRepository.save(tickPreventionSpray));

            Gallery catLeashGallery = galleryRepository.findAll().get(2);
            List<AnimalCategory> catLeashCategories = animalCategoryRepository.findAll().subList(1, 2);
            Product catLeash = new Product(catLeashCategories, 50.2,
                    "Leash for cats", "Cat leash", catLeashGallery);

            System.out.println("Preloading: " + productRepository.save(catLeash));

            Gallery dogLeashGallery = galleryRepository.findAll().get(1);
            List<AnimalCategory> dogLeashCategories = animalCategoryRepository.findAll().subList(0, 1);
            Product dogLeash = new Product(dogLeashCategories, 149.99,
                    "A light up leash for your dog", "Light up dog leash", dogLeashGallery);

            System.out.println("Preloading: " + productRepository.save(dogLeash));
        };
    }

    @Bean
    public CommandLineRunner initOrders(ProductService productService,
                                        IOrderedProductRepository orderedProductRepository, IUserRepository userRepository,
                                        OrderService orderService) {
        return args -> {

            Product product = productService.findById(11L);
            Product product2 = productService.findById(12L);

            User user = userRepository.findByUserName("mrkvac");
            User user2 = userRepository.findByUserName("testUser");

            OrderedProduct orderedProduct = orderedProductRepository.save(new OrderedProduct(product, 3));
            OrderedProduct orderedProduct2 = orderedProductRepository.save(new OrderedProduct(product2, 2));

            OrderedProduct orderedProduct3 = orderedProductRepository.save(new OrderedProduct(product, 5));


            List<OrderedProduct> orderedProducts = Arrays.asList(orderedProductRepository.findById(orderedProduct.getId()).get());
            List<OrderedProduct> orderedProducts2 = Arrays.asList(orderedProductRepository.findById(orderedProduct.getId()).get(),
                    orderedProductRepository.findById(orderedProduct2.getId()).get());

            Order o1 = new Order(orderedProducts, user);
            Order o2 = new Order(orderedProducts2, user2);
            Order o3 = new Order(Arrays.asList(orderedProductRepository.findById(orderedProduct3.getId()).get()), user);

            System.out.println("Order: " + o1 + "\nOrder2: " + o2 + "\nOrder3: " + o3 + "\n\n");

            System.out.println("Preloading: " + orderService.save(o1));
            System.out.println("Preloading: " + orderService.save(o2));
            System.out.println("Preloading: " + orderService.save(o3));
        };
    }

}
