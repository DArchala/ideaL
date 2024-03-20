package pl.archala.ideal.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.reactive.server.WebTestClient;
import pl.archala.ideal.PostgresqlContainer;
import pl.archala.ideal.dto.comment.AddCommentDTO;
import pl.archala.ideal.dto.comment.GetCommentDTO;
import pl.archala.ideal.dto.idea.AddIdeaDTO;
import pl.archala.ideal.dto.idea.GetIdeaDTO;
import pl.archala.ideal.dto.realization.AddRealizationDTO;
import pl.archala.ideal.dto.realization.GetRealizationDTO;
import pl.archala.ideal.enums.IdeaCategory;
import pl.archala.ideal.dto.errorResponse.ErrorResponse;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SuppressWarnings("ConstantConditions")
class IdeasControllerTest extends PostgresqlContainer {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void shouldReturnAddedIdea() {
        //given
        Long id = 1L;
        String title = "idea-title";
        String content = "idea-content";
        AddIdeaDTO addIdeaDTO = new AddIdeaDTO(title, content, IdeaCategory.OTHER);

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
        Long id = 1L;
        String expectedReason = "Idea with id %s does not exist".formatted(id);

        //when
        ErrorResponse errorResponse = webTestClient.get().uri("/api/idea/details/{id}", id).exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorResponse.class)
                .returnResult().getResponseBody();

        //then
        assertEquals(HttpStatus.NOT_FOUND, errorResponse.status());
        assertEquals(1, errorResponse.reasons().size());
        assertEquals(expectedReason, errorResponse.reasons().getFirst());
        assertNotNull(errorResponse.occurred());

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
        AddIdeaDTO addIdeaDTO1 = new AddIdeaDTO("title-1", "content-1", IdeaCategory.OTHER);
        AddIdeaDTO addIdeaDTO2 = new AddIdeaDTO("title-2", "content-2", IdeaCategory.OTHER);

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
        String expectedIdeaNotFoundMsg = "Idea with id 1 does not exist";
        String expectedCommentNotFoundMsg = "Comment with id 1 does not exist";
        AddIdeaDTO addIdeaDTO = new AddIdeaDTO("idea-title", "idea-content", IdeaCategory.OTHER);
        AddCommentDTO addCommentDTO = new AddCommentDTO("idea-comment-content", ideaId);
        AddRealizationDTO addRealizationDTO = new AddRealizationDTO("realization-content", ideaId);

        //when
        GetIdeaDTO getIdeaDTO = webTestClient.post().uri("/api/idea").bodyValue(addIdeaDTO).exchange()
                .expectStatus().isCreated()
                .expectBody(GetIdeaDTO.class)
                .returnResult().getResponseBody();

        webTestClient.post().uri("/api/idea/add-comment").bodyValue(addCommentDTO).exchange()
                .expectStatus().isCreated()
                .expectBody(GetCommentDTO.class);

        GetRealizationDTO getRealizationDTO = webTestClient.post().uri("/api/realization").bodyValue(addRealizationDTO).exchange()
                .expectStatus().isCreated()
                .expectBody(GetRealizationDTO.class)
                .returnResult().getResponseBody();

        webTestClient.delete().uri("/api/idea?id={ideaId}", ideaId).exchange()
                .expectBody(GetIdeaDTO.class)
                .isEqualTo(getIdeaDTO);

        ErrorResponse ideaErrorResponse = webTestClient.get().uri("/api/idea/details/{id}", ideaId).exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorResponse.class)
                .returnResult().getResponseBody();

        ErrorResponse commentErrorResponse = webTestClient.get().uri("/api/comment/details/{id}", ideaId).exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorResponse.class)
                .returnResult().getResponseBody();

        webTestClient.get().uri("/api/realization/details/{id}", 1L).exchange()
                .expectStatus().isOk()
                .expectBody(GetRealizationDTO.class)
                .isEqualTo(getRealizationDTO);

        //then
        assertNotNull(ideaErrorResponse.reasons());
        assertNotNull(ideaErrorResponse.occurred());
        assertEquals(1, ideaErrorResponse.reasons().size());
        assertEquals(expectedIdeaNotFoundMsg, ideaErrorResponse.reasons().getFirst());
        assertEquals(HttpStatus.NOT_FOUND, ideaErrorResponse.status());

        assertNotNull(commentErrorResponse.reasons());
        assertNotNull(commentErrorResponse.occurred());
        assertEquals(1, commentErrorResponse.reasons().size());
        assertEquals(expectedCommentNotFoundMsg, commentErrorResponse.reasons().getFirst());
        assertEquals(HttpStatus.NOT_FOUND, commentErrorResponse.status());

    }
}