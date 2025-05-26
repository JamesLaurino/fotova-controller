package com.fotova.firstapp.service.product;

import com.fotova.dto.file.FileResponseDto;
import com.fotova.dto.product.CategoryInnerProductDto;
import com.fotova.dto.product.ProductDtoBack;
import com.fotova.entity.CategoryEntity;
import com.fotova.entity.ProductEntity;
import com.fotova.service.product.ProductMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class ProductMapperServiceUnitTest {

    private ProductMapper productMapper = new ProductMapper();

    @Test
    @DisplayName("mapToProductDtoBackList")
    public void mapToProductDtoBackList() {

        // GIVEN
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setId(1);
        categoryEntity.setName("category 1");

        ProductEntity productEntity = new ProductEntity();
        productEntity.setName("product 1");
        productEntity.setPrice(100.0);
        productEntity.setQuantity(10);
        productEntity.setId(1);
        productEntity.setUrl("url 1");
        productEntity.setCategory(categoryEntity);

        ProductEntity productEntityTwo = new ProductEntity();
        productEntityTwo.setId(2);
        productEntityTwo.setName("product 2");
        productEntityTwo.setPrice(10.0);
        productEntityTwo.setQuantity(100);
        productEntityTwo.setUrl("url 2");

        List<ProductEntity> productEntityList = List.of(productEntity, productEntityTwo);

        // WHEN
        List<ProductDtoBack> productDtoBack = productMapper.mapToProductDtoBackList(productEntityList);

        // THEN
        assertThat(productDtoBack).hasSize(2);
        assertThat(productDtoBack).isNotNull();

        assertThat(productDtoBack.get(0).getId()).isEqualTo(productEntity.getId());
        assertThat(productDtoBack.get(0).getName()).isEqualTo(productEntity.getName());
        assertThat(productDtoBack.get(0).getPrice()).isEqualTo(productEntity.getPrice());
        assertThat(productDtoBack.get(0).getQuantity()).isEqualTo(productEntity.getQuantity());
        assertThat(productDtoBack.get(0).getUrl()).isEqualTo(productEntity.getUrl());
        assertThat(productDtoBack.get(0).getCategoryInnerProductDto()).isNotNull();
        assertThat(productDtoBack.get(0).getCategoryInnerProductDto().getName()).isEqualTo(categoryEntity.getName());

        assertThat(productDtoBack.get(1).getId()).isEqualTo(productEntityTwo.getId());
        assertThat(productDtoBack.get(1).getName()).isEqualTo(productEntityTwo.getName());
        assertThat(productDtoBack.get(1).getPrice()).isEqualTo(productEntityTwo.getPrice());
        assertThat(productDtoBack.get(1).getQuantity()).isEqualTo(productEntityTwo.getQuantity());
        assertThat(productDtoBack.get(1).getUrl()).isEqualTo(productEntityTwo.getUrl());
        assertThat(productDtoBack.get(1).getCategoryInnerProductDto()).isNull();

    }

    @Test
    @DisplayName("setFileUrlToProductDtoBackList")
    public void setFileUrlToProductDtoBackList() {

        // GIVEN
        FileResponseDto fileResponseDto = new FileResponseDto();
        fileResponseDto.setFile("file 1");
        fileResponseDto.setFileName("file name 1");

        FileResponseDto fileResponseDtoTwo = new FileResponseDto();
        fileResponseDtoTwo.setFile("file 2");
        fileResponseDtoTwo.setFileName("file name 2");

        ProductDtoBack productDtoBack = new ProductDtoBack();
        productDtoBack.setId(1);
        productDtoBack.setName("product 1");
        productDtoBack.setPrice(100.0);
        productDtoBack.setQuantity(10);
        productDtoBack.setUrl("file 1");

        ProductDtoBack productDtoBackTwo = new ProductDtoBack();
        productDtoBackTwo.setId(2);
        productDtoBackTwo.setName("product 2");
        productDtoBackTwo.setPrice(10.0);
        productDtoBackTwo.setQuantity(100);
        productDtoBackTwo.setUrl("file 2");

        List<FileResponseDto> fileResponseDtoList = List.of(fileResponseDto, fileResponseDtoTwo);
        List<ProductDtoBack> productDtoBackListToMap = List.of(productDtoBack, productDtoBackTwo);

        // WHEN
        List<ProductDtoBack> productDtoBackList = productMapper
                .setFileUrlToProductDtoBackList(fileResponseDtoList,productDtoBackListToMap);

        // THEN
        assertThat(productDtoBackList).hasSize(2);
        assertThat(productDtoBackList).isNotNull();

        assertThat(productDtoBackList.get(0).getId()).isEqualTo(productDtoBack.getId());
        assertThat(productDtoBackList.get(0).getUrl()).isEqualTo(fileResponseDto.getFile());

        assertThat(productDtoBackList.get(1).getId()).isEqualTo(productDtoBackTwo.getId());
        assertThat(productDtoBackList.get(1).getUrl()).isEqualTo(fileResponseDtoTwo.getFile());

    }

    @Test
    @DisplayName("mapToProductEntity")
    public void mapToProductEntity() {

        // GIVEN
        CategoryInnerProductDto categoryEntity = new CategoryInnerProductDto();
        categoryEntity.setId(1);
        categoryEntity.setName("category 1");

        ProductDtoBack productDtoBack = new ProductDtoBack();
        productDtoBack.setId(1);
        productDtoBack.setName("product 1");
        productDtoBack.setPrice(100.0);
        productDtoBack.setQuantity(10);
        productDtoBack.setUrl("file 1");
        productDtoBack.setCategoryInnerProductDto(categoryEntity);

        ProductDtoBack productDtoBackTwo = new ProductDtoBack();
        productDtoBackTwo.setId(2);
        productDtoBackTwo.setName("product 2");
        productDtoBackTwo.setPrice(10.0);
        productDtoBackTwo.setQuantity(100);
        productDtoBackTwo.setUrl("file 2");

        // WHEN
        ProductEntity productEntityOne = productMapper.mapToProductEntity(productDtoBack);
        ProductEntity productEntityTwo = productMapper.mapToProductEntity(productDtoBackTwo);

        // THEN
        assertThat(productEntityOne).isNotNull();
        assertThat(productEntityTwo).isNotNull();

        assertThat(productEntityOne.getId()).isEqualTo(productDtoBack.getId());
        assertThat(productEntityTwo.getId()).isEqualTo(productDtoBackTwo.getId());

        assertThat(productEntityOne.getName()).isEqualTo(productDtoBack.getName());
        assertThat(productEntityTwo.getName()).isEqualTo(productDtoBackTwo.getName());

        assertThat(productEntityOne.getPrice()).isEqualTo(productDtoBack.getPrice());
        assertThat(productEntityTwo.getPrice()).isEqualTo(productDtoBackTwo.getPrice());

        assertThat(productEntityOne.getUrl()).isEqualTo(productDtoBack.getUrl());
        assertThat(productEntityTwo.getUrl()).isEqualTo(productDtoBackTwo.getUrl());

        assertThat(productEntityOne.getCategory().getId()).isEqualTo(categoryEntity.getId());
        assertThat(productEntityOne.getCategory().getName()).isEqualTo(categoryEntity.getName());

        assertThat(productEntityTwo.getCategory()).isNull();
    }

}
