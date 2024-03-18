package pl.archala.ideal.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.reactive.server.WebTestClient;
import pl.archala.ideal.PostgresqlContainer;
import pl.archala.ideal.dto.comment.AddIdeaCommentDTO;
import pl.archala.ideal.dto.comment.GetCommentDTO;
import pl.archala.ideal.dto.idea.AddIdeaDTO;
import pl.archala.ideal.dto.idea.GetIdeaDTO;
import pl.archala.ideal.dto.realization.AddRealizationDTO;
import pl.archala.ideal.dto.realization.GetRealizationDTO;
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
        Long id = 1L;
        String title = "idea-title";
        String content = "idea-content";
        AddIdeaDTO addIdeaDTO = new AddIdeaDTO(title, content);

        //when
        GetIdeaDTO actualPostResponse = webTestClient.post().uri("/api/idea").bodyValue(addIdeaDTO).exchange()
                .expectStatus().isCreated()
                .expectBody(GetIdeaDTO.class)
                .returnResult().getResponseBody();

        webTestClient.get().uri("/api/idea/details/{id}", id).exchange()
                .expectStatus().isOk()
                .expectBody(GetIdeaDTO.class)
                .isEqualTo(actualPostResponse);

        //then
        assertEquals(id, actualPostResponse.id());
        assertEquals(title, actualPostResponse.title());
        assertEquals(content, actualPostResponse.content());

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
        GetIdeaDTO getIdeaDTO1 = webTestClient.post().uri("/api/idea").bodyValue(addIdeaDTO1).exchange()
                .expectStatus().isCreated()
                .expectBody(GetIdeaDTO.class)
                .returnResult().getResponseBody();

        GetIdeaDTO getIdeaDTO2 = webTestClient.post().uri("/api/idea").bodyValue(addIdeaDTO2).exchange()
                .expectStatus().isCreated()
                .expectBody(GetIdeaDTO.class)
                .returnResult().getResponseBody();

        webTestClient.get().uri("/api/idea/page").exchange()
                .expectStatus().isOk()
                .expectBodyList(GetIdeaDTO.class).hasSize(2)
                .contains(getIdeaDTO1, getIdeaDTO2);

    }

    @Test
    void shouldDeleteCommentsWithIdeaButNotRealizations() {
        //given
        Long ideaId = 1L;
        String expectedNotFoundMsg = "Idea with id 1 does not exist";
        AddIdeaDTO addIdeaDTO = new AddIdeaDTO("idea-title", "idea-content");
        AddIdeaCommentDTO addIdeaCommentDTO = new AddIdeaCommentDTO("idea-comment-content", ideaId);
        AddRealizationDTO addRealizationDTO = new AddRealizationDTO("realization-content", ideaId);

        //when
        GetIdeaDTO getIdeaDTO = webTestClient.post().uri("/api/idea").bodyValue(addIdeaDTO).exchange()
                .expectStatus().isCreated()
                .expectBody(GetIdeaDTO.class)
                .returnResult().getResponseBody();

        webTestClient.post().uri("/api/comment/idea").bodyValue(addIdeaCommentDTO).exchange()
                .expectStatus().isCreated()
                .expectBody(GetCommentDTO.class);

        webTestClient.post().uri("/api/realization").bodyValue(addRealizationDTO).exchange()
                .expectStatus().isCreated()
                .expectBody(GetRealizationDTO.class);

        webTestClient.delete().uri(uriBuilder -> uriBuilder.path("/api/idea")
                        .queryParam("id", ideaId).build()).exchange()
                .expectBody(GetIdeaDTO.class).isEqualTo(getIdeaDTO);

        ErrorResponse errorResponse = webTestClient.get().uri("/api/idea/details/{id}", ideaId).exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorResponse.class)
                .returnResult().getResponseBody();

        webTestClient.get().uri("/api/comment/details/{id}", ideaId).exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorResponse.class);

        webTestClient.get().uri("/api/realization/details/{id}", 1L).exchange()
                .expectStatus().isOk()
                .expectBody(GetRealizationDTO.class);

        //then
        assertEquals(1, errorResponse.reasons().size());
        assertEquals(expectedNotFoundMsg, errorResponse.reasons().get(0));
        assertEquals(HttpStatus.NOT_FOUND, errorResponse.status());

    }
}