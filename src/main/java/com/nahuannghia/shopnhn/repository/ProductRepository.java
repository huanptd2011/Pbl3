package com.nahuannghia.shopnhn.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nahuannghia.shopnhn.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    //Tìm kiếm phải viết đúng
    //@Query("SELECT p FROM Product p WHERE LOWER(p.productName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(p.brand) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    //TÌm kiếm có thể viết không dấu
    @Query(
            value = "SELECT * FROM product p WHERE LOWER(p.productName) COLLATE Vietnamese_CI_AI LIKE LOWER(CONCAT('%', :keyword, '%')) OR "
            + "LOWER(p.brand) COLLATE Vietnamese_CI_AI LIKE LOWER(CONCAT('%', :keyword, '%'))",
            nativeQuery = true
    )
    List<Product> searchByNameOrBrand(String keyword);

    @Query("SELECT p FROM Product p ORDER BY p.createdAt DESC LIMIT 4")
    List<Product> findTop4ByOrderByCreatedDateDesc();

    @Query("SELECT COUNT(DISTINCT p.productId) FROM Product p LEFT JOIN ProductInventory pi ON p.productId = pi.productInventoryId.productId WHERE pi.quantity <= :count")
    long countLowStockProducts(@Param("count") int count);

    @Query("SELECT sum(pi.quantity) FROM Product p LEFT JOIN ProductInventory pi ON p.productId = pi.id.productId")
    long countTotalInventory();

}
