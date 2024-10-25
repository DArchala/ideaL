package pl.archala.ideal.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.reactive.server.WebTestClient;
import pl.archala.ideal.application.rest.dto.in.SaveCommentRequest;
import pl.archala.ideal.application.rest.dto.in.SaveIdeaRequest;
import pl.archala.ideal.application.rest.dto.out.ApiErrorResponse;
import pl.archala.ideal.application.rest.dto.out.GetCommentResponse;
import pl.archala.ideal.application.rest.dto.out.GetIdeaResponse;
import pl.archala.ideal.domain.enums.IdeaCategory;
import pl.archala.ideal.shared.PostgresqlContainer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SuppressWarnings("ConstantConditions")
class CommentsControllerTest extends PostgresqlContainer {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void shouldThrowExceptionIfCommentDoesNotExist() {
        //given
        Long id = 1L;
        String expectedErrorMsg = "Comment with id 1 does not exist";

        //when
        var apiErrorResponse = webTestClient.get()
                                                         .uri("/api/comments/details/{id}", id)
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
        assertEquals(expectedErrorMsg,
                     apiErrorResponse.reasons()
                                     .getFirst());
        assertEquals(HttpStatus.NOT_FOUND, apiErrorResponse.status());
        assertNotNull(apiErrorResponse.occurred());

    }

    @Test
    void shouldReturnAddedComment() {
        //given
        var saveIdeaRequest = new SaveIdeaRequest("idea-title", "idea-content", IdeaCategory.OTHER);
        var saveCommentRequest = new SaveCommentRequest("comment-content", 1L);

        //when
        var addedIdea = webTestClient.post()
                                                 .uri("/api/ideas")
                                                 .bodyValue(saveIdeaRequest)
                                                 .exchange()
                                                 .expectStatus()
                                                 .isCreated()
                                                 .expectBody(GetIdeaResponse.class)
                                                 .returnResult()
                                                 .getResponseBody();

        var addedComment = webTestClient.post()
                                                       .uri("/api/ideas/comment")
                                                       .bodyValue(saveCommentRequest)
                                                       .exchange()
                                                       .expectStatus()
                                                       .isCreated()
                                                       .expectBody(GetCommentResponse.class)
                                                       .returnResult()
                                                       .getResponseBody();

        webTestClient.get()
                     .uri("/api/comments/details/{id}", addedComment.id())
                     .exchange()
                     .expectStatus()
                     .isOk()
                     .expectBody(GetCommentResponse.class)
                     .isEqualTo(addedComment);

        //then
        assertEquals(1L, addedIdea.id());
        assertEquals(saveIdeaRequest.title(), addedIdea.title());
        assertEquals(saveIdeaRequest.content(), addedIdea.content());
        assertEquals(saveIdeaRequest.category(), addedIdea.category());
        assertNotNull(addedIdea.createdAt());

        assertEquals(1L, addedComment.id());
        assertEquals(saveCommentRequest.content(), addedComment.content());
        assertNotNull(addedComment.createdAt());

    }

}