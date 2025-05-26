package com.fotova.firstapp.service.image;

import com.fotova.dto.image.ImageDto;
import com.fotova.dto.product.ProductDtoBack;
import com.fotova.entity.ImageEntity;
import com.fotova.entity.ProductEntity;
import com.fotova.repository.image.ImageRepositoryImpl;
import com.fotova.service.image.ImageMapper;
import com.fotova.service.image.ImageService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class ImageServiceUnitTest {
    @InjectMocks
    private ImageService imageService;

    @Mock
    private ImageRepositoryImpl imageRepositoryImpl;

    @Mock
    private ImageMapper imageMapper;

    @Test
    @DisplayName("Get all images")
    public void getAllImages() {
        // GIVEN
        ImageDto imageDtoOne = new ImageDto();
        imageDtoOne.setId(1);
        imageDtoOne.setPath("image1.jpg");
        imageDtoOne.setProductDtoBack(new ProductDtoBack());

        ImageDto imageDtoTwo = new ImageDto();
        imageDtoTwo.setId(2);
        imageDtoTwo.setPath("image2.jpg");
        imageDtoTwo.setProductDtoBack(new ProductDtoBack());

        ImageEntity imageEntityOne = new ImageEntity();
        imageEntityOne.setId(1);
        imageEntityOne.setPath("entity-image1.jpg");
        imageEntityOne.setProduct(new ProductEntity());

        ImageEntity imageEntityTwo = new ImageEntity();
        imageEntityTwo.setId(2);
        imageEntityTwo.setPath("entity-image2.jpg");
        imageEntityTwo.setProduct(new ProductEntity());

        List<ImageDto> imageDtoList = List.of(imageDtoOne, imageDtoTwo);
        List<ImageEntity> imageEntityList = List.of(imageEntityOne, imageEntityTwo);

        // WHEN
        BDDMockito.given(imageRepositoryImpl.findAll()).willReturn(imageEntityList);
        BDDMockito.given(imageMapper.mapToImageDtoList(imageEntityList)).willReturn(imageDtoList);
        List<ImageDto> imageDtoListRes = imageService.getAllImages();

        // THEN
        verify(imageRepositoryImpl, times(1)).findAll();
        verify(imageMapper, times(1)).mapToImageDtoList(imageEntityList);
        assertThat(imageDtoListRes).isNotEmpty();
        assertThat(imageDtoListRes).hasSize(2);
        assertThat(imageDtoListRes.get(0).getPath()).isEqualTo(imageDtoOne.getPath());
        assertThat(imageDtoListRes.get(1).getPath()).isEqualTo(imageDtoTwo.getPath());
    }

    @Test
    @DisplayName("Get an image by its id")
    public void getImageById() {
        // GIVEN
        Integer imageId = 1;

        ImageDto imageDtoOne = new ImageDto();
        imageDtoOne.setId(imageId);
        imageDtoOne.setPath("image1.jpg");
        imageDtoOne.setProductDtoBack(new ProductDtoBack());

        ImageEntity imageEntityOne = new ImageEntity();
        imageEntityOne.setId(imageId);
        imageEntityOne.setPath("entity-image1.jpg");
        imageEntityOne.setProduct(new ProductEntity());

        // WHEN
        BDDMockito.given(imageRepositoryImpl.findById(imageId)).willReturn(imageEntityOne);
        BDDMockito.given(imageMapper.mapToImageDto(imageEntityOne)).willReturn(imageDtoOne);
        ImageDto imageDtoRes = imageService.getImageById(imageId);

        // THEN
        verify(imageRepositoryImpl, times(1)).findById(imageId);
        verify(imageMapper, times(1)).mapToImageDto(imageEntityOne);
        assertThat(imageDtoRes).isNotNull();
        assertThat(imageDtoRes.getId()).isEqualTo(imageId);
        assertThat(imageDtoRes.getPath()).isEqualTo(imageDtoOne.getPath());
    }

    @Test
    @DisplayName("Delete an image by its id")
    public void deleteImageById() {
        // GIVEN
        Integer imageId = 1;

        // WHEN
        BDDMockito.willDoNothing().given(imageRepositoryImpl).deleteById(imageId);
        String result = imageService.deleteImageById(imageId);

        // THEN
        verify(imageRepositoryImpl, times(1)).deleteById(imageId);
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo("Image deleted successfully with id : " + imageId);
    }

    @Test
    @DisplayName("Save an image with a product id")
    public void saveImage() {
        // GIVEN
        Integer productId = 1;

        ImageDto imageDtoInput = new ImageDto();
        imageDtoInput.setId(1);
        imageDtoInput.setPath("path/to/image.jpg");
        imageDtoInput.setProductDtoBack(new ProductDtoBack());

        ImageEntity imageEntityInput = new ImageEntity();
        imageEntityInput.setId(1);
        imageEntityInput.setPath("path/to/image.jpg");

        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(productId);
        imageEntityInput.setProduct(productEntity);

        ImageEntity savedImageEntity = new ImageEntity();
        savedImageEntity.setId(1);
        savedImageEntity.setPath("path/to/image.jpg");
        savedImageEntity.setProduct(productEntity);

        ImageDto imageDtoOutput = new ImageDto();
        imageDtoOutput.setId(1);
        imageDtoOutput.setPath("path/to/image.jpg");
        imageDtoOutput.setProductDtoBack(new ProductDtoBack());

        // WHEN
        BDDMockito.given(imageMapper.mapToImageEntity(imageDtoInput)).willReturn(imageEntityInput);
        BDDMockito.given(imageRepositoryImpl.save(imageEntityInput)).willReturn(savedImageEntity);
        BDDMockito.given(imageMapper.mapToImageDto(savedImageEntity)).willReturn(imageDtoOutput);

        ImageDto result = imageService.saveImage(imageDtoInput, productId);

        // THEN
        verify(imageMapper, times(1)).mapToImageEntity(imageDtoInput);
        verify(imageRepositoryImpl, times(1)).save(imageEntityInput);
        verify(imageMapper, times(1)).mapToImageDto(savedImageEntity);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(imageDtoInput.getId());
        assertThat(result.getPath()).isEqualTo(imageDtoInput.getPath());
    }

    @Test
    @DisplayName("Update an image")
    public void updateImage() {
        // GIVEN
        Integer imageId = 1;
        Integer productId = 10;

        ImageDto imageDtoInput = new ImageDto();
        imageDtoInput.setId(imageId);
        imageDtoInput.setPath("old/path.jpg");

        ImageEntity foundEntity = new ImageEntity();
        foundEntity.setId(imageId);
        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(productId);
        foundEntity.setProduct(productEntity);

        ProductDtoBack productDtoBack = new ProductDtoBack();
        productDtoBack.setId(productId);
        ImageDto imageDtoWithProduct = new ImageDto();
        imageDtoWithProduct.setId(imageId);
        imageDtoWithProduct.setProductDtoBack(productDtoBack);
        imageDtoWithProduct.setPath("old/path.jpg");

        ImageEntity entityToUpdate = new ImageEntity();
        entityToUpdate.setId(imageId);
        entityToUpdate.setPath("old/path.jpg");

        ImageEntity updatedEntity = new ImageEntity();
        updatedEntity.setId(imageId);
        updatedEntity.setPath("old/path.jpg");

        ImageDto updatedDto = new ImageDto();
        updatedDto.setId(imageId);
        updatedDto.setPath("old/path.jpg");
        updatedDto.setProductDtoBack(productDtoBack);

        // WHEN
        BDDMockito.given(imageRepositoryImpl.findById(imageId)).willReturn(foundEntity);
        BDDMockito.given(imageMapper.mapToImageEntity(imageDtoInput)).willReturn(entityToUpdate);
        BDDMockito.given(imageRepositoryImpl.update(entityToUpdate)).willReturn(updatedEntity);
        BDDMockito.given(imageMapper.mapToImageDto(updatedEntity)).willReturn(updatedDto);

        ImageDto result = imageService.updateImage(imageDtoInput);

        // THEN
        verify(imageRepositoryImpl, times(1)).findById(imageId);
        verify(imageMapper, times(1)).mapToImageEntity(imageDtoInput);
        verify(imageRepositoryImpl, times(1)).update(entityToUpdate);
        verify(imageMapper, times(1)).mapToImageDto(updatedEntity);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(imageId);
        assertThat(result.getProductDtoBack()).isNotNull();
        assertThat(result.getProductDtoBack().getId()).isEqualTo(productId);
    }

}
