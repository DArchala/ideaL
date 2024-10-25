package pl.archala.ideal.mapper;

import org.junit.jupiter.api.Test;
import pl.archala.ideal.dto.realization.AddRealizationDTO;
import pl.archala.ideal.entity.Realization;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RealizationMapperTest {

    private final RealizationMapper realizationMapper = new RealizationMapper();

    @Test
    void shouldReturnCommentFromAddRealizationDTO() {
        //given
        AddRealizationDTO addRealizationDTO = new AddRealizationDTO("simple-content", 1L);

        Realization expectedRealization = new Realization();
        expectedRealization.setContent(addRealizationDTO.content());

        //when
        Realization actualRealization = realizationMapper.toEntity(addRealizationDTO);
        expectedRealization.setCreated(actualRealization.getCreated());

        //then
        assertEquals(expectedRealization, actualRealization);

    }
}