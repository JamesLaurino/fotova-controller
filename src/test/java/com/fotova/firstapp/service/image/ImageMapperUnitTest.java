package com.fotova.firstapp.service.image;

import com.fotova.dto.image.ImageDto;
import com.fotova.dto.product.CategoryInnerProductDto;
import com.fotova.dto.product.ProductDtoBack;
import com.fotova.entity.CategoryEntity;
import com.fotova.entity.ImageEntity;
import com.fotova.entity.ProductEntity;
import com.fotova.service.image.ImageMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ImageMapperUnitTest {
    private ImageMapper imageMapper = new ImageMapper();
    @Test
    @DisplayName("mapToImageDto - mapping complet avec produit et catégorie")
    void mapToImageDtoFullTest() {
        // GIVEN
        CategoryEntity category = new CategoryEntity();
        category.setId(1);
        category.setName("CategoryName");

        ProductEntity product = new ProductEntity();
        product.setId(10);
        product.setName("ProductName");
        product.setPrice(99.99);
        product.setQuantity(5);
        product.setUrl("http://product.url/image.jpg");
        product.setCategory(category);

        ImageEntity imageEntity = new ImageEntity();
        imageEntity.setId(100);
        imageEntity.setPath("image/path.jpg");
        imageEntity.setProduct(product);

        // WHEN
        ImageDto dto = imageMapper.mapToImageDto(imageEntity);

        // THEN
        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isEqualTo(100);
        assertThat(dto.getPath()).isEqualTo("image/path.jpg");

        assertThat(dto.getProductDtoBack()).isNotNull();
        ProductDtoBack productDto = dto.getProductDtoBack();
        assertThat(productDto.getId()).isEqualTo(10);
        assertThat(productDto.getName()).isEqualTo("ProductName");
        assertThat(productDto.getPrice()).isEqualTo(99.99);
        assertThat(productDto.getQuantity()).isEqualTo(5);
        assertThat(productDto.getUrl()).isEqualTo("http://product.url/image.jpg");

        assertThat(productDto.getCategoryInnerProductDto()).isNotNull();
        CategoryInnerProductDto catDto = productDto.getCategoryInnerProductDto();
        assertThat(catDto.getId()).isEqualTo(1);
        assertThat(catDto.getName()).isEqualTo("CategoryName");
    }

    @Test
    @DisplayName("mapToImageDto - image sans produit")
    void mapToImageDtoNoProductTest() {
        // GIVEN
        ImageEntity imageEntity = new ImageEntity();
        imageEntity.setId(101);
        imageEntity.setPath("image/no-product.jpg");
        imageEntity.setProduct(null);

        // WHEN
        ImageDto dto = imageMapper.mapToImageDto(imageEntity);

        // THEN
        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isEqualTo(101);
        assertThat(dto.getPath()).isEqualTo("image/no-product.jpg");
        assertThat(dto.getProductDtoBack()).isNull();
    }

    @Test
    @DisplayName("mapToImageDto - produit sans catégorie")
    void mapToImageDtoProductNoCategoryTest() {
        // GIVEN
        ProductEntity product = new ProductEntity();
        product.setId(11);
        product.setName("ProductWithoutCategory");
        product.setPrice(50.0);
        product.setQuantity(2);
        product.setUrl("http://product.url/no-category.jpg");
        product.setCategory(null);

        ImageEntity imageEntity = new ImageEntity();
        imageEntity.setId(102);
        imageEntity.setPath("image/product-no-category.jpg");
        imageEntity.setProduct(product);

        // WHEN
        ImageDto dto = imageMapper.mapToImageDto(imageEntity);

        // THEN
        assertThat(dto).isNotNull();
        assertThat(dto.getProductDtoBack()).isNotNull();
        assertThat(dto.getProductDtoBack().getCategoryInnerProductDto()).isNull();
    }

    @Test
    @DisplayName("mapToImageDtoList - map une liste d'ImageEntity")
    void mapToImageDtoListTest() {
        // GIVEN
        ImageEntity image1 = new ImageEntity();
        image1.setId(1);
        image1.setPath("path1.jpg");

        ImageEntity image2 = new ImageEntity();
        image2.setId(2);
        image2.setPath("path2.jpg");

        List<ImageEntity> entityList = List.of(image1, image2);

        // WHEN
        List<ImageDto> dtoList = imageMapper.mapToImageDtoList(entityList);

        // THEN
        assertThat(dtoList).hasSize(2);
        assertThat(dtoList.get(0).getId()).isEqualTo(1);
        assertThat(dtoList.get(1).getPath()).isEqualTo("path2.jpg");
    }

    @Test
    @DisplayName("mapToImageEntity - mapping simple avec produit")
    void mapToImageEntityWithProductTest() {
        // GIVEN
        ProductDtoBack productDto = new ProductDtoBack();
        productDto.setId(55);

        ImageDto imageDto = new ImageDto();
        imageDto.setId(200);
        imageDto.setPath("dto/path.jpg");
        imageDto.setProductDtoBack(productDto);

        // WHEN
        ImageEntity entity = imageMapper.mapToImageEntity(imageDto);

        // THEN
        assertThat(entity).isNotNull();
        assertThat(entity.getId()).isEqualTo(200);
        assertThat(entity.getPath()).isEqualTo("dto/path.jpg");
        assertThat(entity.getProduct()).isNotNull();
        assertThat(entity.getProduct().getId()).isEqualTo(55);
    }

    @Test
    @DisplayName("mapToImageEntity - mapping sans produit")
    void mapToImageEntityNoProductTest() {
        // GIVEN
        ImageDto imageDto = new ImageDto();
        imageDto.setId(201);
        imageDto.setPath("dto/no-product.jpg");
        imageDto.setProductDtoBack(null);

        // WHEN
        ImageEntity entity = imageMapper.mapToImageEntity(imageDto);

        // THEN
        assertThat(entity).isNotNull();
        assertThat(entity.getId()).isEqualTo(201);
        assertThat(entity.getPath()).isEqualTo("dto/no-product.jpg");
        assertThat(entity.getProduct()).isNull();
    }
}
