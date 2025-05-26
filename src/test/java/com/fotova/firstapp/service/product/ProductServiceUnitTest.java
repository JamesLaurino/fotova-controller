package com.fotova.firstapp.service.product;

import com.drools.dto.category.CategoryDto;
import com.drools.dto.product.ProductDtoDrl;
import com.drools.service.BusinessProductDroolsService;
import com.fotova.dto.ProductDtoAmq;
import com.fotova.dto.file.FileResponseDto;
import com.fotova.dto.image.ImageDto;
import com.fotova.dto.product.ProductDtoBack;
import com.fotova.entity.CategoryEntity;
import com.fotova.entity.ProductEntity;
import com.fotova.repository.product.ProductRepositoryImpl;
import com.fotova.service.RabbitMQProducer;
import com.fotova.service.file.FileService;
import com.fotova.service.product.ProductMapper;
import com.fotova.service.product.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ProductServiceUnitTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepositoryImpl productRepository;

    @Mock
    private BusinessProductDroolsService businessProductDroolsService;

    @Mock
    private ProductMapper productMapper;

    @Mock
    private RabbitMQProducer rabbitMQProducer;

    @Mock
    private FileService fileService;

    @Test
    @DisplayName("Save product")
    public void saveProduct() {
        // GIVEN
        ProductDtoBack productDtoBack = new ProductDtoBack();
        productDtoBack.setId(1);
        productDtoBack.setName("Product 1");
        productDtoBack.setUrl("url 1");
        productDtoBack.setPrice(15.0);
        productDtoBack.setQuantity(4);

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

        // WHEN
        BDDMockito.given(productMapper.mapToProductEntity(productDtoBack)).willReturn(productEntity);
        BDDMockito.given(productRepository.saveWithCategory(productEntity,categoryEntity.getId())).willReturn(productEntity);
        BDDMockito.given(productMapper.mapToProductDtoBack(productEntity)).willReturn(productDtoBack);
        ProductDtoBack result = productService.saveProduct(productDtoBack,categoryEntity.getId());

        // THEN
        verify(productRepository, times(1)).saveWithCategory(productEntity,categoryEntity.getId());
        verify(productMapper, times(1)).mapToProductDtoBack(productEntity);
        verify(productMapper, times(1)).mapToProductEntity(productDtoBack);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(productDtoBack.getId());
        assertThat(result.getName()).isEqualTo(productDtoBack.getName());
        assertThat(result.getUrl()).isEqualTo(productDtoBack.getUrl());
        assertThat(result.getPrice()).isEqualTo(productDtoBack.getPrice());
        assertThat(result.getQuantity()).isEqualTo(productDtoBack.getQuantity());
    }

    @Test
    @DisplayName("getAllProducts")
    public void getAllProducts() {
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
        List<FileResponseDto> filesContent = List.of(new FileResponseDto(), new FileResponseDto());
        List<ProductDtoBack> productDtoBackList = List.of(new ProductDtoBack(), new ProductDtoBack());

        // WHEN
        BDDMockito.given(productRepository.findAll()).willReturn(productEntityList);
        BDDMockito.given(fileService.getAllFilesContent()).willReturn(filesContent);
        BDDMockito.given(productMapper.mapToProductDtoBackList(productEntityList)).willReturn(productDtoBackList);
        BDDMockito.given(productMapper.setFileUrlToProductDtoBackList(filesContent,productDtoBackList)).willReturn(productDtoBackList);
        List<ProductDtoBack> productDtoBackRes = productService.getAllProducts();

        // THEN
        verify(productRepository, times(1)).findAll();
        verify(fileService, times(1)).getAllFilesContent();
        verify(productMapper, times(1)).mapToProductDtoBackList(productEntityList);
        verify(productMapper, times(1)).setFileUrlToProductDtoBackList(filesContent,productDtoBackList);

        assertThat(productDtoBackRes).isNotNull();
        assertThat(productDtoBackRes).hasSize(2);
    }

    @Test
    @DisplayName("getProductById")
    public void getProductById() {
        // GIVEN
        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(1);
        productEntity.setName("Test Product");
        productEntity.setPrice(100.0);
        productEntity.setQuantity(5);
        productEntity.setUrl("url_test");

        ProductDtoBack productDtoBack = new ProductDtoBack();
        productDtoBack.setId(1);
        productDtoBack.setName("Test Product");
        productDtoBack.setPrice(100.0);
        productDtoBack.setQuantity(5);
        productDtoBack.setUrl("url_test");

        List<FileResponseDto> filesContent = List.of(
                new FileResponseDto("file1_url", "file1_name"),
                new FileResponseDto("file2_url", "file2_name")
        );

        ImageDto imageDto1 = new ImageDto();
        imageDto1.setPath("image1_path");
        ImageDto imageDto2 = new ImageDto();
        imageDto2.setPath("image2_path");
        List<ImageDto> imageDtoList = List.of(imageDto1, imageDto2);

        // GIVEN
        when(productRepository.findById(1)).thenReturn(productEntity);
        when(fileService.getAllFilesContent()).thenReturn(filesContent);
        when(productRepository.getProductImages(1)).thenReturn(imageDtoList);
        when(productMapper.mapToProductDtoBack(productEntity)).thenReturn(productDtoBack);
        when(productMapper.setFileUrlToProductDto(imageDtoList, productDtoBack, filesContent)).thenReturn(productDtoBack);

        // WHEN
        ProductDtoBack result = productService.getProductById(1);

        // THEN
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1);
        verify(productRepository).findById(1);
        verify(fileService).getAllFilesContent();
        verify(productRepository).getProductImages(1);
        verify(productMapper).mapToProductDtoBack(productEntity);
        verify(productMapper).setFileUrlToProductDto(imageDtoList, productDtoBack, filesContent);

    }

    @Test
    @DisplayName("deleteProductById")
    public void deleteProductById() {
        // WHEN
        BDDMockito.willDoNothing().given(productRepository).deleteById(1);
        productService.deleteProductById(1);

        // THEN
        verify(productRepository, times(1)).deleteById(1);
    }

    @Test
    @DisplayName("updateProduct")
    public void updateProduct() {
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

        ProductDtoBack productDtoBack = new ProductDtoBack();
        productDtoBack.setId(1);
        productDtoBack.setName("Product 1");
        productDtoBack.setUrl("url 1");
        productDtoBack.setPrice(15.0);
        productDtoBack.setQuantity(4);

        // WHEN
        BDDMockito.given(productMapper.mapToProductEntity(productDtoBack)).willReturn(productEntity);
        BDDMockito.given(productRepository.save(productEntity)).willReturn(productEntity);
        productService.updateProduct(productDtoBack);

        // THEN
        verify(productRepository, times(1)).save(productEntity);
        verify(productMapper, times(1)).mapToProductEntity(productDtoBack);
    }

    @Test
    @DisplayName("testAMQPService")
    public void testAMQPService() {
        // GIVEN
        ProductDtoDrl productDtoDrl = new ProductDtoDrl();
        productDtoDrl.setId(1);
        productDtoDrl.setName("name 1");
        productDtoDrl.setPrice(10.0);
        productDtoDrl.setQuantity(100);
        productDtoDrl.setCategory(new CategoryDto());

        ProductDtoAmq product = new ProductDtoAmq();
        product.setId(1);
        product.setName("name 1");
        product.setPrice(10.0);
        product.setQuantity(100);


        // THEN
        BDDMockito.willDoNothing().given(rabbitMQProducer).sendMessage(productDtoDrl.toString());
        productService.testAMQPService(productDtoDrl);

        // THEN
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(rabbitMQProducer, times(1)).sendMessage(captor.capture());
    }
}
