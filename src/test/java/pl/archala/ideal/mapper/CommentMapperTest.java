package pl.archala.ideal.mapper;

import org.junit.jupiter.api.Test;
import pl.archala.ideal.dto.comment.AddCommentCommentDTO;
import pl.archala.ideal.dto.comment.AddIdeaCommentDTO;
import pl.archala.ideal.dto.comment.AddRealizationCommentDTO;
import pl.archala.ideal.entity.Comment;

import static org.junit.jupiter.api.Assertions.*;

class CommentMapperTest {

    private final CommentMapper commentMapper = new CommentMapper();

    @Test
    void shouldReturnCommentFromAddIdeaCommentDTO() {
        //given
        AddIdeaCommentDTO addIdeaCommentDTO = new AddIdeaCommentDTO("simple-content", 1L);

        Comment expectedComment = new Comment();
        expectedComment.setContent(addIdeaCommentDTO.content());

        //when
        Comment actualComment = commentMapper.toEntity(addIdeaCommentDTO);
        expectedComment.setCreated(actualComment.getCreated());

        //then
        assertEquals(expectedComment, actualComment);
    }

    @Test
    void shouldReturnCommentFromAddRealizationCommentDTO() {
        //given
        AddRealizationCommentDTO addRealizationCommentDTO = new AddRealizationCommentDTO("simple-content", 1L);

        Comment expectedComment = new Comment();
        expectedComment.setContent(addRealizationCommentDTO.content());

        //when
        Comment actualComment = commentMapper.toEntity(addRealizationCommentDTO);
        expectedComment.setCreated(actualComment.getCreated());

        //then
        assertEquals(expectedComment, actualComment);
    }

    @Test
    void shouldReturnCommentFromAddCommentCommentDTO() {
        //given
        AddCommentCommentDTO addCommentCommentDTO = new AddCommentCommentDTO("simple-content", 1L);

        Comment expectedComment = new Comment();
        expectedComment.setContent(addCommentCommentDTO.content());

        //when
        Comment actualComment = commentMapper.toEntity(addCommentCommentDTO);
        expectedComment.setCreated(actualComment.getCreated());

        //then
        assertEquals(expectedComment, actualComment);
    }
}