package pl.archala.ideal.controller;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import pl.archala.ideal.PostgresqlContainer;
import pl.archala.ideal.dto.idea.AddIdeaDTO;
import pl.archala.ideal.dto.idea.GetIdeaDTO;
import pl.archala.ideal.enums.IdeaCategory;
import pl.archala.ideal.error.ErrorResponse;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class IdeasControllerTest extends PostgresqlContainer {

    @Autowired
    private WebTestClient webTestClient;

    @SneakyThrows
    @Test
    void shouldReturnAddedIdeaAndFindItUsingGetRequest() {
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
                .expectBody(GetIdeaDTO.class)
                .isEqualTo(actualGetIdeaDTO);

        webTestClient.get().uri("/api/idea/page").exchange()
                .expectStatus().isOk()
                .expectBodyList(GetIdeaDTO.class)
                .hasSize(1)
                .contains(actualGetIdeaDTO);

        webTestClient.get().uri("/api/idea/random").exchange()
                .expectStatus().isOk()
                .expectBody(GetIdeaDTO.class)
                .isEqualTo(actualGetIdeaDTO);

        //then
        assertEquals(1L, actualGetIdeaDTO.id());
        assertEquals(simpleTitle, actualGetIdeaDTO.title());
        assertEquals(simpleContent, actualGetIdeaDTO.content());
        assertNull(actualGetIdeaDTO.realizationsIds());
        assertNull(actualGetIdeaDTO.commentsIds());
    }

    @Test
    void shouldReturnCategoriesList() {
        //given
        List<IdeaCategory> expectedCategories = List.of(IdeaCategory.values());

        //when
        List<IdeaCategory> actualCategories = webTestClient.get().uri("/api/idea/categories").exchange()
                .expectStatus().isOk()
                .expectBodyList(IdeaCategory.class).hasSize(expectedCategories.size())
                .returnResult().getResponseBody();

        //then
        assertEquals(expectedCategories, actualCategories);

    }

    @Test
    void shouldThrowNotFoundExceptionIfEntityWithProvidedIdDoesNotExist() {
        //given
        String notExistingId = "5000";
        String expectedReason = "Idea with id %s does not exist".formatted(notExistingId);

        //when
        ErrorResponse actualResponse = webTestClient.get().uri("/api/idea/5000").exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorResponse.class)
                .returnResult().getResponseBody();

        //then
        assertEquals(HttpStatus.NOT_FOUND, actualResponse.status());
        assertEquals(1, actualResponse.reasons().size());
        assertEquals(expectedReason, actualResponse.reasons().get(0));

    }

}