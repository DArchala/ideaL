package pl.archala.ideal.mapper;

import org.junit.jupiter.api.Test;
import pl.archala.ideal.dto.comment.AddCommentDTO;
import pl.archala.ideal.entity.Comment;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CommentMapperTest {

    private final CommentMapper commentMapper = new CommentMapper();

    @Test
    void shouldReturnCommentByAddCommentDTO() {
        //given
        AddCommentDTO addCommentDTO = new AddCommentDTO("simple-content", 1L);

        Comment expectedComment = new Comment();
        expectedComment.setContent(addCommentDTO.content());

        //when
        Comment actualComment = commentMapper.toEntity(addCommentDTO);
        expectedComment.setCreated(actualComment.getCreated());

        //then
        assertEquals(expectedComment, actualComment);
    }

}