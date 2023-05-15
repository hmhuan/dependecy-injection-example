import com.example.di.container.DIContainer;
import com.example.di.service.ProductService;

public class Main {
    public static void main(String[] args) {
        DIContainer diContainer = new DIContainer();

        ProductService productService = diContainer.getInstance(ProductService.class);
        System.out.println(productService.getAllProducts());
    }
}
