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
import pl.archala.ideal.dto.errorResponse.ErrorResponse;
import pl.archala.ideal.dto.idea.AddIdeaDTO;
import pl.archala.ideal.dto.idea.GetIdeaDTO;
import pl.archala.ideal.enums.IdeaCategory;

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
        ErrorResponse errorResponse = webTestClient.get().uri("/api/comments/details/{id}", id).exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorResponse.class)
                .returnResult().getResponseBody();

        //then
        assertEquals(1, errorResponse.reasons().size());
        assertEquals(expectedErrorMsg, errorResponse.reasons().getFirst());
        assertEquals(HttpStatus.NOT_FOUND, errorResponse.status());
        assertNotNull(errorResponse.occurred());

    }

    @Test
    void shouldReturnAddedComment() {
        //given
        AddIdeaDTO addIdeaDTO = new AddIdeaDTO("idea-title", "idea-content", IdeaCategory.OTHER);
        AddCommentDTO addCommentDTO = new AddCommentDTO("comment-content", 1L);

        //when
        GetIdeaDTO addedIdea = webTestClient.post().uri("/api/ideas").bodyValue(addIdeaDTO).exchange()
                .expectStatus().isCreated()
                .expectBody(GetIdeaDTO.class)
                .returnResult().getResponseBody();

        GetCommentDTO addedComment = webTestClient.post().uri("/api/ideas/comment").bodyValue(addCommentDTO).exchange()
                .expectStatus().isCreated()
                .expectBody(GetCommentDTO.class)
                .returnResult().getResponseBody();

        webTestClient.get().uri("/api/comments/details/{id}", addedComment.id()).exchange()
                .expectStatus().isOk()
                .expectBody(GetCommentDTO.class)
                .isEqualTo(addedComment);

        //then
        assertEquals(1L, addedIdea.id());
        assertEquals(addIdeaDTO.title(), addedIdea.title());
        assertEquals(addIdeaDTO.content(), addedIdea.content());
        assertEquals(addIdeaDTO.category(), addedIdea.category());
        assertNotNull(addedIdea.created());

        assertEquals(1L, addedComment.id());
        assertEquals(addCommentDTO.content(), addedComment.content());
        assertNotNull(addedComment.created());

    }

}