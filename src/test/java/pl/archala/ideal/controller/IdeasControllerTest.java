package pl.archala.ideal.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.reactive.server.WebTestClient;
import pl.archala.ideal.PostgresqlContainer;
import pl.archala.ideal.dto.idea.AddIdeaDTO;
import pl.archala.ideal.dto.idea.GetIdeaDTO;
import pl.archala.ideal.enums.IdeaCategory;
import pl.archala.ideal.error.ErrorResponse;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class IdeasControllerTest extends PostgresqlContainer {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void shouldReturnAddedIdea() {
        //given
        String title = "idea-title";
        String content = "idea-content";
        AddIdeaDTO addIdeaDTO = new AddIdeaDTO(title, content);

        //when
        GetIdeaDTO actualPostResponse = webTestClient.post().uri("/api/idea").bodyValue(addIdeaDTO).exchange()
                .expectStatus().isCreated()
                .expectBody(GetIdeaDTO.class)
                .returnResult().getResponseBody();

        GetIdeaDTO actualGetResponse = webTestClient.get().uri("api/idea/details/1").exchange()
                .expectStatus().isOk()
                .expectBody(GetIdeaDTO.class)
                .returnResult().getResponseBody();

        //then
        assertEquals(1L, actualPostResponse.id());
        assertEquals(title, actualPostResponse.title());
        assertEquals(content, actualPostResponse.content());

        assertEquals(actualPostResponse, actualGetResponse);

    }

    @Test
    void shouldThrowNotFoundExceptionIfEntityWithProvidedIdDoesNotExist() {
        //given
        String notExistingId = "1";
        String expectedReason = "Idea with id %s does not exist".formatted(notExistingId);

        //when
        ErrorResponse actualResponse = webTestClient.get().uri("/api/idea/details/{id}", notExistingId).exchange()
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
        //then
        webTestClient.get().uri("/api/idea/categories").exchange()
                .expectStatus().isOk()
                .expectBodyList(IdeaCategory.class)
                .isEqualTo(expectedCategories);

    }

    @Test
    void shouldReturnIdeasPage() {
        //given
        AddIdeaDTO addIdeaDTO1 = new AddIdeaDTO("title-1", "content-1");
        AddIdeaDTO addIdeaDTO2 = new AddIdeaDTO("title-2", "content-2");

        //when
        //then
        GetIdeaDTO getIdeaDTO1 = webTestClient.post().uri("api/idea").bodyValue(addIdeaDTO1).exchange()
                .expectStatus().isCreated()
                .expectBody(GetIdeaDTO.class)
                .returnResult().getResponseBody();

        GetIdeaDTO getIdeaDTO2 = webTestClient.post().uri("api/idea").bodyValue(addIdeaDTO2).exchange()
                .expectStatus().isCreated()
                .expectBody(GetIdeaDTO.class)
                .returnResult().getResponseBody();

        webTestClient.get().uri("api/idea/page").exchange()
                .expectStatus().isOk()
                .expectBodyList(GetIdeaDTO.class).hasSize(2)
                .contains(getIdeaDTO1, getIdeaDTO2);

    }

}