package com.fotova.firstapp.repositoryImpl.image;

import com.fotova.entity.ImageEntity;
import com.fotova.repository.image.ImageRepositoryImpl;
import com.fotova.repository.image.ImageRepositoryJpa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ImageRepositoryImplTest {
    @Mock
    private ImageRepositoryJpa imageRepositoryJpa;

    @InjectMocks
    private ImageRepositoryImpl imageRepositoryImpl;


    private ImageEntity imageEntityOne;
    private ImageEntity imageEntityTwo;

    @BeforeEach
    public void init() {
        imageRepositoryJpa.deleteAll();
        imageEntityOne = new ImageEntity();
        imageEntityOne.setId(1);
        imageEntityOne.setPath("path/to/image1.jpg");

        imageEntityTwo = new ImageEntity();
        imageEntityTwo.setId(2);
        imageEntityTwo.setPath("path/to/image2.jpg");
    }

    @Test
    @DisplayName("Find all images")
    @Order(1)
    public void givenImageRepository_whenFindAll_thenReturnList() {
        // GIVEN
        BDDMockito.given(imageRepositoryJpa.findAll()).willReturn(List.of(imageEntityOne, imageEntityTwo));

        // WHEN
        List<ImageEntity> imageEntityList = imageRepositoryImpl.findAll();

        // THEN
        assertThat(imageEntityList).isNotEmpty();
        assertThat(imageEntityList).hasSize(2);
        assertThat(imageEntityList).containsExactly(imageEntityOne, imageEntityTwo);
    }

    @Test
    @DisplayName("Find image by id")
    @Order(2)
    public void givenImageRepository_whenFindById_thenReturnImage() {
        // GIVEN
        BDDMockito.given(imageRepositoryJpa.findById(1)).willReturn(Optional.of(imageEntityOne));

        // WHEN
        ImageEntity imageEntity = imageRepositoryImpl.findById(1);

        // THEN
        assertThat(imageEntity).isNotNull();
        assertThat(imageEntity.getId()).isEqualTo(1);
        assertThat(imageEntity.getPath()).isEqualTo("path/to/image1.jpg");
    }

    @Test
    @DisplayName("Save image")
    @Order(3)
    public void givenImageEntity_whenSave_thenReturnSavedEntity() {
        // GIVEN
        BDDMockito.given(imageRepositoryJpa.save(imageEntityOne)).willReturn(imageEntityOne);

        // WHEN
        ImageEntity savedEntity = imageRepositoryImpl.save(imageEntityOne);

        // THEN
        assertThat(savedEntity).isNotNull();
        assertThat(savedEntity.getId()).isEqualTo(1);
        verify(imageRepositoryJpa, times(1)).save(imageEntityOne);
    }

    @Test
    @DisplayName("Update image")
    @Order(4)
    public void givenImageEntity_whenUpdate_thenReturnUpdatedEntity() {
        // GIVEN
        ImageEntity updatedImage = new ImageEntity();
        updatedImage.setId(1);
        updatedImage.setPath("new/path/to/image.jpg");

        BDDMockito.given(imageRepositoryJpa.save(imageEntityOne)).willReturn(updatedImage);

        // WHEN
        ImageEntity result = imageRepositoryImpl.update(imageEntityOne);

        // THEN
        assertThat(result).isNotNull();
        assertThat(result.getPath()).isEqualTo("new/path/to/image.jpg");
        verify(imageRepositoryJpa, times(1)).save(imageEntityOne);
    }

    @Test
    @DisplayName("Delete image by id")
    @Order(5)
    public void givenDeleteImage_whenDeleteById_thenSuccess() {
        // GIVEN
        doNothing().when(imageRepositoryJpa).deleteById(1);

        // WHEN
        imageRepositoryImpl.deleteById(1);

        // THEN
        verify(imageRepositoryJpa, times(1)).deleteById(1);
    }

    @Test
    @DisplayName("Delete all images")
    @Order(6)
    public void givenDeleteAllImages_whenDeleteAll_thenSuccess() {
        // GIVEN
        doNothing().when(imageRepositoryJpa).deleteAll();

        // WHEN
        imageRepositoryImpl.deleteAll();

        // THEN
        verify(imageRepositoryJpa, times(1)).deleteAll();
    }
}
