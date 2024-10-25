package pl.archala.ideal.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.reactive.server.WebTestClient;
import pl.archala.ideal.shared.PostgresqlContainer;
import pl.archala.ideal.dto.comment.SaveCommentRequest;
import pl.archala.ideal.dto.comment.GetCommentResponse;
import pl.archala.ideal.dto.error.ApiErrorResponse;
import pl.archala.ideal.dto.idea.SaveIdeaRequest;
import pl.archala.ideal.dto.idea.GetIdeaResponse;
import pl.archala.ideal.dto.realization.SaveRealizationRequest;
import pl.archala.ideal.dto.realization.GetRealizationResponse;
import pl.archala.ideal.enums.IdeaCategory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SuppressWarnings("ConstantConditions")
class RealizationControllerTest extends PostgresqlContainer {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void shouldReturnNotFoundStatusIfRealizationDoesNotExist() {
        //given
        Long realizationId = 1L;
        String expectedRealizationNotFoundMsg = "Realization with id 1 does not exist";

        //when
        ApiErrorResponse apiErrorResponse = webTestClient.get()
                                                         .uri("/api/realizations/details/{id}", realizationId)
                                                         .exchange()
                                                         .expectStatus()
                                                         .isNotFound()
                                                         .expectBody(ApiErrorResponse.class)
                                                         .returnResult()
                                                         .getResponseBody();

        //then
        assertEquals(1,
                     apiErrorResponse.reasons()
                                     .size());
        assertEquals(expectedRealizationNotFoundMsg,
                     apiErrorResponse.reasons()
                                     .getFirst());
        assertEquals(HttpStatus.NOT_FOUND, apiErrorResponse.status());
        assertNotNull(apiErrorResponse.occurred());
    }

    @Test
    void shouldThrowExceptionIfIdeaWeRealizeDoesNotExist() {
        //given
        SaveRealizationRequest saveRealizationRequest = new SaveRealizationRequest("real-content", 1L);
        String expectedIdeaNotFoundMsg = "Idea with id 1 does not exist";

        //when
        ApiErrorResponse apiErrorResponse = webTestClient.post()
                                                         .uri("/api/ideas/realization")
                                                         .bodyValue(saveRealizationRequest)
                                                         .exchange()
                                                         .expectStatus()
                                                         .isNotFound()
                                                         .expectBody(ApiErrorResponse.class)
                                                         .returnResult()
                                                         .getResponseBody();

        //then
        assertEquals(1,
                     apiErrorResponse.reasons()
                                     .size());
        assertEquals(expectedIdeaNotFoundMsg,
                     apiErrorResponse.reasons()
                                     .getFirst());
        assertNotNull(apiErrorResponse.occurred());

    }

    @Test
    void shouldReturnAddedRealization() {
        //given
        SaveIdeaRequest saveIdeaRequest = new SaveIdeaRequest("idea-title", "idea-content", IdeaCategory.OTHER);
        SaveRealizationRequest saveRealizationRequest = new SaveRealizationRequest("realization-content", 1L);

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

        GetRealizationResponse getAddedRealizationDTO = webTestClient.post()
                                                                     .uri("/api/ideas/realization")
                                                                     .bodyValue(saveRealizationRequest)
                                                                     .exchange()
                                                                     .expectStatus()
                                                                     .isCreated()
                                                                     .expectBody(GetRealizationResponse.class)
                                                                     .returnResult()
                                                                     .getResponseBody();

        GetRealizationResponse getRealizationResponse = webTestClient.get()
                                                                     .uri("/api/realizations/details/{id}", getAddedRealizationDTO.id())
                                                                     .exchange()
                                                                     .expectStatus()
                                                                     .isOk()
                                                                     .expectBody(GetRealizationResponse.class)
                                                                     .isEqualTo(getAddedRealizationDTO)
                                                                     .returnResult()
                                                                     .getResponseBody();

        //then
        assertEquals(saveIdeaRequest.title(), getIdeaResponse.title());
        assertEquals(saveIdeaRequest.content(), getIdeaResponse.content());
        assertEquals(saveRealizationRequest.ideaId(), getIdeaResponse.id());
        assertEquals(saveRealizationRequest.content(), getRealizationResponse.content());

    }

    @Test
    void shouldReturnRealizationsByIdeaId() {
        //given
        SaveIdeaRequest saveIdeaRequest = new SaveIdeaRequest("idea-title", "idea-content", IdeaCategory.OTHER);
        SaveRealizationRequest saveRealizationRequest1 = new SaveRealizationRequest("realization-content-1", 1L);
        SaveRealizationRequest saveRealizationRequest2 = new SaveRealizationRequest("realization-content-2", 1L);

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

        GetRealizationResponse getRealizationResponse1 = webTestClient.post()
                                                                      .uri("/api/ideas/realization")
                                                                      .bodyValue(saveRealizationRequest1)
                                                                      .exchange()
                                                                      .expectStatus()
                                                                      .isCreated()
                                                                      .expectBody(GetRealizationResponse.class)
                                                                      .returnResult()
                                                                      .getResponseBody();

        GetRealizationResponse getRealizationResponse2 = webTestClient.post()
                                                                      .uri("/api/ideas/realization")
                                                                      .bodyValue(saveRealizationRequest2)
                                                                      .exchange()
                                                                      .expectStatus()
                                                                      .isCreated()
                                                                      .expectBody(GetRealizationResponse.class)
                                                                      .returnResult()
                                                                      .getResponseBody();

        webTestClient.get()
                     .uri("/api/realizations/by-idea?ideaId={ideaId}", getIdeaResponse.id())
                     .exchange()
                     .expectStatus()
                     .isOk()
                     .expectBodyList(GetRealizationResponse.class)
                     .hasSize(2)
                     .contains(getRealizationResponse1, getRealizationResponse2);

        //then
        assertEquals(saveIdeaRequest.title(), getIdeaResponse.title());
        assertEquals(saveIdeaRequest.content(), getIdeaResponse.content());
        assertEquals(1L, getIdeaResponse.id());
        assertNotNull(getIdeaResponse.createdAt());

        assertEquals(1L, getRealizationResponse1.id());
        assertEquals(saveRealizationRequest1.content(), getRealizationResponse1.content());

        assertEquals(2L, getRealizationResponse2.id());
        assertEquals(saveRealizationRequest2.content(), getRealizationResponse2.content());

    }

    @Test
    void shouldDeleteRealizationWithCommentsButWithoutIdea() {
        //given
        SaveIdeaRequest saveIdeaRequest = new SaveIdeaRequest("idea-title", "idea-content", IdeaCategory.OTHER);
        SaveRealizationRequest saveRealizationRequest = new SaveRealizationRequest("realization-content", 1L);
        SaveCommentRequest saveCommentRequest = new SaveCommentRequest("comment-content", 1L);
        String expectedRealizationNotFoundMsg = "Realization with id 1 does not exist";
        String expectedCommentNotFoundMsg = "Comment with id 1 does not exist";

        //when
        GetIdeaResponse addedIdea = webTestClient.post()
                                                 .uri("/api/ideas")
                                                 .bodyValue(saveIdeaRequest)
                                                 .exchange()
                                                 .expectStatus()
                                                 .isCreated()
                                                 .expectBody(GetIdeaResponse.class)
                                                 .returnResult()
                                                 .getResponseBody();

        GetRealizationResponse addedRealization = webTestClient.post()
                                                               .uri("/api/ideas/realization")
                                                               .bodyValue(saveRealizationRequest)
                                                               .exchange()
                                                               .expectStatus()
                                                               .isCreated()
                                                               .expectBody(GetRealizationResponse.class)
                                                               .returnResult()
                                                               .getResponseBody();

        GetCommentResponse addedComment = webTestClient.post()
                                                       .uri("/api/realizations/comment")
                                                       .bodyValue(saveCommentRequest)
                                                       .exchange()
                                                       .expectStatus()
                                                       .isCreated()
                                                       .expectBody(GetCommentResponse.class)
                                                       .returnResult()
                                                       .getResponseBody();

        webTestClient.delete()
                     .uri("/api/realizations?id=1")
                     .exchange()
                     .expectStatus()
                     .isOk()
                     .expectBody(GetRealizationResponse.class)
                     .isEqualTo(addedRealization);

        webTestClient.get()
                     .uri("/api/ideas/details/{id}", addedIdea.id())
                     .exchange()
                     .expectStatus()
                     .isOk()
                     .expectBody(GetIdeaResponse.class)
                     .isEqualTo(addedIdea);

        ApiErrorResponse errorRealizationResponse = webTestClient.get()
                                                                 .uri("/api/realizations/details/{id}", addedRealization.id())
                                                                 .exchange()
                                                                 .expectStatus()
                                                                 .isNotFound()
                                                                 .expectBody(ApiErrorResponse.class)
                                                                 .returnResult()
                                                                 .getResponseBody();

        ApiErrorResponse errorCommentResponse = webTestClient.get()
                                                             .uri("/api/comments/details/{id}", addedComment.id())
                                                             .exchange()
                                                             .expectStatus()
                                                             .isNotFound()
                                                             .expectBody(ApiErrorResponse.class)
                                                             .returnResult()
                                                             .getResponseBody();

        //then
        assertEquals(1,
                     errorRealizationResponse.reasons()
                                             .size());
        assertEquals(expectedRealizationNotFoundMsg,
                     errorRealizationResponse.reasons()
                                             .getFirst());
        assertEquals(HttpStatus.NOT_FOUND, errorRealizationResponse.status());
        assertNotNull(errorRealizationResponse.occurred());

        assertEquals(1,
                     errorCommentResponse.reasons()
                                         .size());
        assertEquals(expectedCommentNotFoundMsg,
                     errorCommentResponse.reasons()
                                         .getFirst());
        assertEquals(HttpStatus.NOT_FOUND, errorCommentResponse.status());
        assertNotNull(errorCommentResponse.occurred());

    }
}