package com.budiluhur.catalog.servlet;

import com.budiluhur.catalog.exception.ProductNotFoundException;
import com.budiluhur.catalog.model.Product;
import com.budiluhur.catalog.repository.ProductRepository;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/products")
public class ProductServlet extends HttpServlet {
    private ProductRepository productRepository;

    @Override
    public void init() {
        productRepository = new ProductRepository();
        productRepository.addProduct(new Product("PRD-01", "Keyboard Mechanical", 450000.0));
        productRepository.addProduct(new Product("PRD-02", "Mouse Wireless Silent", 175000.0));
        productRepository.addProduct(new Product("PRD-03", "Monitor Gaming 24 Inch", 2100000.0));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        // 1. Ambil query parameter id
        String id = request.getParameter("id");

        out.println("<html>");
        out.println("<head><title>Katalog Produk Web</title></head>");
        out.println("<body>");

        if (id != null && !id.trim().isEmpty()) {
            // 2. Pencarian produk berdasarkan parameter id
            try {
                Product p = productRepository.findById(id);
                out.println("<h2>Detail Produk</h2>");
                out.println("<p><strong>ID:</strong> " + p.getId() + "</p>");
                out.println("<p><strong>Nama:</strong> " + p.getName() + "</p>");
                out.println("<p><strong>Harga Satuan:</strong> Rp" + p.getPrice() + "</p>");
                out.println("<br><a href='/products'>&larr; Kembali ke Seluruh Katalog</a>");
            } catch (ProductNotFoundException e) {
                // 3. Tangani jika ID tidak ditemukan: Set HTTP 404 & teks merah
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                out.println("<h2 style='color: red;'>Error 404 - Not Found</h2>");
                out.println("<p style='color: red;'>" + e.getMessage() + "</p>");
                out.println("<br><a href='/products'>&larr; Kembali ke Seluruh Katalog</a>");
            }
        } else {
            // Tampilkan seluruh tabel jika parameter id kosong
            List<Product> products = productRepository.findAll();
            out.println("<h2>=== DAFTAR KATALOG PRODUK (WEB) ===</h2>");
            out.println("<table border='1' cellpadding='8'>");
            out.println("<tr><th>ID Produk</th><th>Nama Produk</th><th>Harga Satuan</th></tr>");
            for (Product p : products) {
                out.println("<tr>");
                out.println("<td><a href='/products?id=" + p.getId() + "'>" + p.getId() + "</a></td>");
                out.println("<td>" + p.getName() + "</td>");
                out.println("<td>Rp" + p.getPrice() + "</td>");
                out.println("</tr>");
            }
            out.println("</table>");
        }

        out.println("</body>");
        out.println("</html>");
    }
}