package pl.archala.ideal.controller;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import pl.archala.ideal.PostgresqlContainer;
import pl.archala.ideal.dto.idea.AddIdeaDTO;
import pl.archala.ideal.dto.idea.GetIdeaDTO;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class IdeasControllerTest extends PostgresqlContainer {

    @Autowired
    private WebTestClient webTestClient;

    @SneakyThrows
    @Test
    void shouldReturnAddedIdeaAndFindItInNextGetRequest() {
        //given
        String simpleTitle = "simple-title";
        String simpleContent = "simple-content";
        AddIdeaDTO addIdeaDTO = new AddIdeaDTO(simpleTitle, simpleContent);

        //when
        GetIdeaDTO actualGetIdeaDTO = webTestClient.post().uri("/api/idea")
                .bodyValue(addIdeaDTO)
                .accept(MediaType.APPLICATION_JSON).exchange()
                .expectStatus().isCreated()
                .expectBody(GetIdeaDTO.class).returnResult().getResponseBody();

        webTestClient.get().uri("/api/idea/1").exchange()
                .expectStatus().isOk()
                .expectBody(GetIdeaDTO.class);

        //then
        assertEquals(1L, actualGetIdeaDTO.getId());
        assertEquals(simpleTitle, actualGetIdeaDTO.getTitle());
        assertEquals(simpleContent, actualGetIdeaDTO.getContent());
        assertNull(actualGetIdeaDTO.getRealizationsIds());
        assertNull(actualGetIdeaDTO.getCommentsIds());
    }

}