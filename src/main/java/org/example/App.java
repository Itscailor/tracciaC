package org.example;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
public class App
{
    public static ArrayList<Product> products = new ArrayList<>();
    public static void main( String[] args ) {
        HttpServer server = null;
        try {
            server = HttpServer.create(new InetSocketAddress(8800), 0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        buildProductList();
        assert server != null;
        server.createContext("/", new MyHandler());
        server.setExecutor(null);
        server.start();
    }
    static void buildProductList() {
        products.add(new Product(36212,"samnso  12 RED",1299.0, 10));
        products.add(new Product(36212,"Apple IPhone 12 RED",10900.0, 10));
        products.add(new Product(36212,"Apple IPhone 12 RED",1000.0, 10));
        products.add(new Product(36213,"Huawei Honor 8 BLACK",25.94, 6));
        products.add(new Product(36214,"Huawei Honor 8 RED",26.94, 1));
        products.add(new Product(36215,"Apple IPhone 13 RED",1226.94, 10));
    }

    public static ArrayList<Product> getProducts() {
        return products;
    }
}