package pl.archala.ideal.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.reactive.server.WebTestClient;
import pl.archala.ideal.application.rest.dto.in.SaveCommentRequest;
import pl.archala.ideal.application.rest.dto.in.SaveIdeaRequest;
import pl.archala.ideal.application.rest.dto.in.SaveRealizationRequest;
import pl.archala.ideal.application.rest.dto.out.ApiErrorResponse;
import pl.archala.ideal.application.rest.dto.out.GetCommentResponse;
import pl.archala.ideal.application.rest.dto.out.GetIdeaResponse;
import pl.archala.ideal.application.rest.dto.out.GetRealizationResponse;
import pl.archala.ideal.domain.enums.IdeaCategory;
import pl.archala.ideal.shared.PostgresqlContainer;

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
        SaveIdeaRequest saveIdeaRequest = new SaveIdeaRequest(title, content, IdeaCategory.OTHER);

        //when
        GetIdeaResponse actualPostResponse = webTestClient.post()
                                                          .uri("/api/ideas")
                                                          .bodyValue(saveIdeaRequest)
                                                          .exchange()
                                                          .expectStatus()
                                                          .isCreated()
                                                          .expectBody(GetIdeaResponse.class)
                                                          .returnResult()
                                                          .getResponseBody();

        webTestClient.get()
                     .uri("/api/ideas/details/{id}", id)
                     .exchange()
                     .expectStatus()
                     .isOk()
                     .expectBody(GetIdeaResponse.class)
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
        ApiErrorResponse apiErrorResponse = webTestClient.get()
                                                         .uri("/api/ideas/details/{id}", id)
                                                         .exchange()
                                                         .expectStatus()
                                                         .isNotFound()
                                                         .expectBody(ApiErrorResponse.class)
                                                         .returnResult()
                                                         .getResponseBody();

        //then
        assertEquals(HttpStatus.NOT_FOUND, apiErrorResponse.status());
        assertEquals(1,
                     apiErrorResponse.reasons()
                                     .size());
        assertEquals(expectedReason,
                     apiErrorResponse.reasons()
                                     .getFirst());
        assertNotNull(apiErrorResponse.occurred());

    }

    @Test
    void shouldReturnCategoriesList() {
        //given
        List<IdeaCategory> expectedCategories = List.of(IdeaCategory.values());

        //when
        //then
        webTestClient.get()
                     .uri("/api/ideas/categories")
                     .exchange()
                     .expectStatus()
                     .isOk()
                     .expectBodyList(IdeaCategory.class)
                     .isEqualTo(expectedCategories);

    }

    @Test
    void shouldReturnIdeasPage() {
        //given
        SaveIdeaRequest saveIdeaRequest1 = new SaveIdeaRequest("title-1", "content-1", IdeaCategory.OTHER);
        SaveIdeaRequest saveIdeaRequest2 = new SaveIdeaRequest("title-2", "content-2", IdeaCategory.OTHER);

        //when
        //then
        GetIdeaResponse getIdeaResponse1 = webTestClient.post()
                                                        .uri("/api/ideas")
                                                        .bodyValue(saveIdeaRequest1)
                                                        .exchange()
                                                        .expectStatus()
                                                        .isCreated()
                                                        .expectBody(GetIdeaResponse.class)
                                                        .returnResult()
                                                        .getResponseBody();

        GetIdeaResponse getIdeaResponse2 = webTestClient.post()
                                                        .uri("/api/ideas")
                                                        .bodyValue(saveIdeaRequest2)
                                                        .exchange()
                                                        .expectStatus()
                                                        .isCreated()
                                                        .expectBody(GetIdeaResponse.class)
                                                        .returnResult()
                                                        .getResponseBody();

        webTestClient.get()
                     .uri("/api/ideas/page")
                     .exchange()
                     .expectStatus()
                     .isOk()
                     .expectBodyList(GetIdeaResponse.class)
                     .hasSize(2)
                     .contains(getIdeaResponse1, getIdeaResponse2);

    }

    @Test
    void shouldDeleteCommentsWithIdeaButNotRealizations() {
        //given
        Long ideaId = 1L;
        String expectedIdeaNotFoundMsg = "Idea with id 1 does not exist";
        String expectedCommentNotFoundMsg = "Comment with id 1 does not exist";
        SaveIdeaRequest saveIdeaRequest = new SaveIdeaRequest("idea-title", "idea-content", IdeaCategory.OTHER);
        SaveCommentRequest saveCommentRequest = new SaveCommentRequest("idea-comment-content", ideaId);
        SaveRealizationRequest saveRealizationRequest = new SaveRealizationRequest("realization-content", ideaId);

        //when
        GetIdeaResponse getIdeaResponse = webTestClient.post()
                                                       .uri("/api/ideas")
                                                       .bodyValue(saveIdeaRequest)
                                                       .exchange()
                                                       .expectStatus()
                                                       .isCreated()
                                                       .expectBody(GetIdeaResponse.class)
                                                       .returnResult()
                                                       .getResponseBody();

        webTestClient.post()
                     .uri("/api/ideas/comment")
                     .bodyValue(saveCommentRequest)
                     .exchange()
                     .expectStatus()
                     .isCreated()
                     .expectBody(GetCommentResponse.class);

        GetRealizationResponse getRealizationResponse = webTestClient.post()
                                                                     .uri("/api/ideas/realization")
                                                                     .bodyValue(saveRealizationRequest)
                                                                     .exchange()
                                                                     .expectStatus()
                                                                     .isCreated()
                                                                     .expectBody(GetRealizationResponse.class)
                                                                     .returnResult()
                                                                     .getResponseBody();

        webTestClient.delete()
                     .uri("/api/ideas?id={ideaId}", ideaId)
                     .exchange()
                     .expectBody(GetIdeaResponse.class)
                     .isEqualTo(getIdeaResponse);

        ApiErrorResponse ideaApiErrorResponse = webTestClient.get()
                                                             .uri("/api/ideas/details/{id}", ideaId)
                                                             .exchange()
                                                             .expectStatus()
                                                             .isNotFound()
                                                             .expectBody(ApiErrorResponse.class)
                                                             .returnResult()
                                                             .getResponseBody();

        ApiErrorResponse commentApiErrorResponse = webTestClient.get()
                                                                .uri("/api/comments/details/{id}", ideaId)
                                                                .exchange()
                                                                .expectStatus()
                                                                .isNotFound()
                                                                .expectBody(ApiErrorResponse.class)
                                                                .returnResult()
                                                                .getResponseBody();

        webTestClient.get()
                     .uri("/api/realizations/details/{id}", 1L)
                     .exchange()
                     .expectStatus()
                     .isOk()
                     .expectBody(GetRealizationResponse.class)
                     .isEqualTo(getRealizationResponse);

        //then
        assertNotNull(ideaApiErrorResponse.reasons());
        assertNotNull(ideaApiErrorResponse.occurred());
        assertEquals(1,
                     ideaApiErrorResponse.reasons()
                                         .size());
        assertEquals(expectedIdeaNotFoundMsg,
                     ideaApiErrorResponse.reasons()
                                         .getFirst());
        assertEquals(HttpStatus.NOT_FOUND, ideaApiErrorResponse.status());

        assertNotNull(commentApiErrorResponse.reasons());
        assertNotNull(commentApiErrorResponse.occurred());
        assertEquals(1,
                     commentApiErrorResponse.reasons()
                                            .size());
        assertEquals(expectedCommentNotFoundMsg,
                     commentApiErrorResponse.reasons()
                                            .getFirst());
        assertEquals(HttpStatus.NOT_FOUND, commentApiErrorResponse.status());

    }
}