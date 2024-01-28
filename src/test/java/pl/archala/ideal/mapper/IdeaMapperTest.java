package pl.archala.ideal.mapper;

import org.junit.jupiter.api.Test;
import pl.archala.ideal.dto.idea.AddIdeaDTO;
import pl.archala.ideal.entity.Idea;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IdeaMapperTest {

    private final IdeaMapper ideaMapper = new IdeaMapper();

    @Test
    void shouldReturnIdeaFromAddIdeaDTO() {
        //given
        AddIdeaDTO addIdeaDTO = new AddIdeaDTO("simple-title", "simple-content");

        Idea expectedIdea = new Idea();
        expectedIdea.setTitle(addIdeaDTO.title());
        expectedIdea.setContent(addIdeaDTO.content());

        //when
        Idea actualIdea = ideaMapper.toEntity(addIdeaDTO);
        expectedIdea.setCreated(actualIdea.getCreated());

        //then
        assertEquals(expectedIdea, actualIdea);
    }

}