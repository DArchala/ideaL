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
import pl.archala.ideal.dto.realization.AddRealizationDTO;
import pl.archala.ideal.dto.realization.GetRealizationDTO;
import pl.archala.ideal.error.ErrorResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SuppressWarnings("ConstantConditions")
class RealizationsControllerTest extends PostgresqlContainer {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void shouldReturnNotFoundStatusIfRealizationDoesNotExist() {
        //given
        Long realizationId = 1L;
        String expectedRealizationNotFoundMsg = "Realization with id 1 does not exist";

        //when
        ErrorResponse errorResponse = webTestClient.get().uri("/api/realization/details/{id}", realizationId).exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorResponse.class)
                .returnResult().getResponseBody();

        //then
        assertEquals(1, errorResponse.reasons().size());
        assertEquals(expectedRealizationNotFoundMsg, errorResponse.reasons().getFirst());
        assertEquals(HttpStatus.NOT_FOUND, errorResponse.status());
        assertNotNull(errorResponse.occurred());
    }

    @Test
    void shouldThrowExceptionIfIdeaDoesNotExist() {
        //given
        AddRealizationDTO addRealizationDTO = new AddRealizationDTO("real-content", 1L);
        String expectedIdeaNotFoundMsg = "Idea with id 1 does not exist";

        //when
        ErrorResponse errorResponse = webTestClient.post().uri("/api/realization").bodyValue(addRealizationDTO).exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorResponse.class)
                .returnResult().getResponseBody();

        //then
        assertEquals(1, errorResponse.reasons().size());
        assertEquals(expectedIdeaNotFoundMsg, errorResponse.reasons().getFirst());
        assertNotNull(errorResponse.occurred());

    }

    @Test
    void shouldReturnAddedRealization(){
        //given
        AddIdeaDTO addIdeaDTO = new AddIdeaDTO("idea-title", "idea-content");
        AddRealizationDTO addRealizationDTO = new AddRealizationDTO("realization-content", 1L);

        //when
        GetIdeaDTO getIdeaDTO = webTestClient.post().uri("/api/idea").bodyValue(addIdeaDTO).exchange()
                .expectStatus().isCreated()
                .expectBody(GetIdeaDTO.class)
                .returnResult().getResponseBody();

        GetRealizationDTO getAddedRealizationDTO = webTestClient.post().uri("/api/realization").bodyValue(addRealizationDTO).exchange()
                .expectStatus().isCreated()
                .expectBody(GetRealizationDTO.class)
                .returnResult().getResponseBody();

        GetRealizationDTO getRealizationDTO = webTestClient.get().uri("/api/realization/details/{id}", getAddedRealizationDTO.id()).exchange()
                .expectStatus().isOk()
                .expectBody(GetRealizationDTO.class)
                .isEqualTo(getAddedRealizationDTO)
                .returnResult().getResponseBody();

        //then
        assertEquals(addIdeaDTO.title(), getIdeaDTO.title());
        assertEquals(addIdeaDTO.content(), getIdeaDTO.content());
        assertEquals(addRealizationDTO.ideaId(), getIdeaDTO.id());
        assertEquals(addRealizationDTO.content(), getRealizationDTO.content());

    }

    @Test
    void shouldReturnRealizationsByIdeaId() {
        //given
        AddIdeaDTO addIdeaDTO = new AddIdeaDTO("idea-title", "idea-content");
        AddRealizationDTO addRealizationDTO1 = new AddRealizationDTO("realization-content-1", 1L);
        AddRealizationDTO addRealizationDTO2 = new AddRealizationDTO("realization-content-2", 1L);

        //when
        GetIdeaDTO getIdeaDTO = webTestClient.post().uri("/api/idea").bodyValue(addIdeaDTO).exchange()
                .expectStatus().isCreated()
                .expectBody(GetIdeaDTO.class)
                .returnResult().getResponseBody();

        GetRealizationDTO getRealizationDTO1 = webTestClient.post().uri("/api/realization")
                .bodyValue(addRealizationDTO1).exchange()
                .expectStatus().isCreated()
                .expectBody(GetRealizationDTO.class)
                .returnResult().getResponseBody();

        GetRealizationDTO getRealizationDTO2 = webTestClient.post().uri("/api/realization")
                .bodyValue(addRealizationDTO2).exchange()
                .expectStatus().isCreated()
                .expectBody(GetRealizationDTO.class)
                .returnResult().getResponseBody();

        webTestClient.get().uri("/api/realization/by-idea?ideaId={ideaId}", getIdeaDTO.id()).exchange()
                .expectStatus().isOk()
                .expectBodyList(GetRealizationDTO.class)
                .hasSize(2)
                .contains(getRealizationDTO1, getRealizationDTO2);

        //then
        assertEquals(addIdeaDTO.title(), getIdeaDTO.title());
        assertEquals(addIdeaDTO.content(), getIdeaDTO.content());
        assertEquals(1L, getIdeaDTO.id());
        assertNotNull(getIdeaDTO.created());

        assertEquals(1L, getRealizationDTO1.id());
        assertEquals(addRealizationDTO1.content(), getRealizationDTO1.content());

        assertEquals(2L, getRealizationDTO2.id());
        assertEquals(addRealizationDTO2.content(), getRealizationDTO2.content());

    }
}