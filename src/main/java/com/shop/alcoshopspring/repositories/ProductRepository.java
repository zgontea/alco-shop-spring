package com.shop.alcoshopspring.repositories;

import com.shop.alcoshopspring.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    String filteredQueryProducts = "SELECT * " +
            "FROM products " +
            "LEFT JOIN categories ON products.category_id = categories.id " +
            "WHERE categories.name = ?3 " +
            "AND products.name LIKE %?4% " +
            "AND products.unit_price >= ?5 " +
            "AND products.unit_price <= ?6 " +
            "OFFSET ?1 LIMIT ?2";
    String filteredQueryProductsCount = "SELECT COUNT(products.id) " +
            "FROM products " +
            "LEFT JOIN categories ON products.category_id = categories.id " +
            "WHERE categories.name = ?1 " +
            "AND products.name LIKE %?2% " +
            "AND products.unit_price >= ?3 " +
            "AND products.unit_price <= ?4";
    String filteredQueryProductsCategoryAll = "SELECT * " +
            "FROM products " +
            "LEFT JOIN categories ON products.category_id = categories.id " +
            "WHERE products.name LIKE %?3% " +
            "AND products.unit_price >= ?4 " +
            "AND products.unit_price <= ?5 " +
            "OFFSET ?1 LIMIT ?2";
    String filteredQueryProductsCountCategoryAll = "SELECT COUNT(products.id) " +
            "FROM products " +
            "LEFT JOIN categories ON products.category_id = categories.id " +
            "WHERE products.name LIKE %?1% " +
            "AND products.unit_price >= ?2 " +
            "AND products.unit_price <= ?3";

    @Query(value = filteredQueryProducts, nativeQuery = true)
    List<Product> getFilteredProducts(int offset, int limit, String category, String productName, BigDecimal unitPriceMin, BigDecimal unitPriceMax);

    @Query(value = filteredQueryProductsCategoryAll, nativeQuery = true)
    List<Product> getFilteredProducts(int offset, int limit, String productName, BigDecimal unitPriceMin, BigDecimal unitPriceMax);

    @Query(value = filteredQueryProductsCount, nativeQuery = true)
    long getFilteredProductsCount(String category, String productName, BigDecimal unitPriceMin, BigDecimal unitPriceMax);

    @Query(value = filteredQueryProductsCountCategoryAll, nativeQuery = true)
    long getFilteredProductsCount(String productName, BigDecimal unitPriceMin, BigDecimal unitPriceMax);
}