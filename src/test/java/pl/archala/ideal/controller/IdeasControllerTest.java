package pl.archala.ideal.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pl.archala.ideal.PostgresqlContainer;
import pl.archala.ideal.dto.idea.AddIdeaDTO;
import pl.archala.ideal.dto.idea.GetIdeaDTO;
import pl.archala.ideal.utils.serialization.LocalDateTimeAdapterProvider;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class IdeasControllerTest extends PostgresqlContainer {

    @Autowired
    private MockMvc mockMvc;

    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, LocalDateTimeAdapterProvider.getInstance())
            .create();

    @SneakyThrows
    @Test
    void shouldReturnAddedIdeaFromPostgres() {
        //given
        String simpleTitle = "simple-title";
        String simpleContent = "simple-content";
        AddIdeaDTO addIdeaDTO = new AddIdeaDTO(simpleTitle, simpleContent);
        String jsonAddIdeaDTO = gson.toJson(addIdeaDTO);

        //when
        mockMvc.perform(post("/api/idea")
                        .content(jsonAddIdeaDTO)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(201));

        String jsonGetIdeaDTO = mockMvc.perform(get("/api/idea").param("id", "1"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        GetIdeaDTO getIdeaDTO = gson.fromJson(jsonGetIdeaDTO, GetIdeaDTO.class);

        //then
        assertEquals(1L, getIdeaDTO.getId());
        assertEquals(simpleTitle, getIdeaDTO.getTitle());
        assertEquals(simpleContent, getIdeaDTO.getContent());
        assertNull(getIdeaDTO.getRealizationsIds());
        assertNull(getIdeaDTO.getCommentsIds());
    }

}