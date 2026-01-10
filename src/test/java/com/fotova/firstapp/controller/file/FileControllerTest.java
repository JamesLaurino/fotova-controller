package com.fotova.firstapp.controller.file;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fotova.dto.file.FileDto;
import com.fotova.dto.file.FileResponseDto;
import com.fotova.service.file.FileService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class FileControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private FileService fileService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Get all files")
    public void getAllFiles() throws Exception {
        // GIVEN
        List<String> fileNames = List.of("file 1", "file 2", "file 3");
        FileDto fileDto = new FileDto();
        fileDto.setFilelist(fileNames);

        // WHEN
        BDDMockito.given(fileService.getAllFiles()).willReturn(fileDto);
        ResultActions resultActions = mockMvc.perform(get("/api/v1/auth/files"));

        // THEN
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.filelist.size()",
                        CoreMatchers.is(fileDto.getFilelist().size())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.success",
                        CoreMatchers.is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseCode",
                        CoreMatchers.is(200)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseMessage",
                        CoreMatchers.is("File retrieved successfully")));
    }

    @Test
    @DisplayName("Get all files content")
    public void getAllFilesContent() throws Exception {
        // GIVEN
        List<FileResponseDto> fileResponseDtoList = List.of(new FileResponseDto(), new FileResponseDto(), new FileResponseDto());

        // WHEN
        BDDMockito.given(fileService.getAllFilesContent()).willReturn(fileResponseDtoList);
        ResultActions resultActions = mockMvc.perform(get("/api/v1/auth/files/content"));

        // THEN
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.size()",
                        CoreMatchers.is(fileResponseDtoList.size())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.success",
                        CoreMatchers.is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseCode",
                        CoreMatchers.is(200)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseMessage", CoreMatchers.is("File retrieved successfully")));
    }
}
