package pl.archala.ideal.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;
import pl.archala.ideal.PostgresqlContainer;
import pl.archala.ideal.dto.idea.AddIdeaDTO;
import pl.archala.ideal.dto.idea.GetIdeaDTO;
import pl.archala.ideal.enums.IdeaCategory;
import pl.archala.ideal.error.ErrorResponse;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class IdeasControllerTest extends PostgresqlContainer {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void shouldReturnAddedIdea() {
        //given
        String title = "idea-title";
        String content = "idea-content";
        AddIdeaDTO addIdeaDTO = new AddIdeaDTO(title, content);
        GetIdeaDTO expectedPostResponse = new GetIdeaDTO(1L, title, content, LocalDateTime.now());

        //when
        GetIdeaDTO actualPostResponse = webTestClient.post().uri("/api/idea").bodyValue(addIdeaDTO).exchange()
                .expectStatus().isCreated()
                .expectBody(GetIdeaDTO.class)
                .returnResult().getResponseBody();

        GetIdeaDTO actualGetResponse = webTestClient.get().uri("api/idea/1").exchange()
                .expectStatus().isOk()
                .expectBody(GetIdeaDTO.class)
                .returnResult().getResponseBody();

        //then
        assertEquals(expectedPostResponse.id(), actualPostResponse.id());
        assertEquals(expectedPostResponse.title(), actualPostResponse.title());
        assertEquals(expectedPostResponse.content(), actualPostResponse.content());

        assertEquals(actualPostResponse, actualGetResponse);
    }

    @Test
    void shouldThrowNotFoundExceptionIfEntityWithProvidedIdDoesNotExist() {
        //given
        String notExistingId = "1";
        String expectedReason = "Idea with id %s does not exist".formatted(notExistingId);

        //when
        ErrorResponse actualResponse = webTestClient.get().uri("/api/idea/{id}", notExistingId).exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorResponse.class)
                .returnResult().getResponseBody();

        //then
        assertEquals(HttpStatus.NOT_FOUND, actualResponse.status());
        assertEquals(1, actualResponse.reasons().size());
        assertEquals(expectedReason, actualResponse.reasons().get(0));

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

}