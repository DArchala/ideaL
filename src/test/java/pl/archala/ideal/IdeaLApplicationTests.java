package pl.archala.ideal;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import pl.archala.ideal.repository.CommentsRepository;
import pl.archala.ideal.repository.IdeasRepository;
import pl.archala.ideal.repository.RealizationsRepository;
import pl.archala.ideal.service.interfaces.CommentsService;
import pl.archala.ideal.service.interfaces.IdeasService;
import pl.archala.ideal.service.interfaces.RealizationsService;

@SpringBootTest
@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
class IdeaLApplicationTests {

    @MockBean
    private CommentsRepository CommentsRepository;

    @MockBean
    private IdeasRepository ideasRepository;

    @MockBean
    private RealizationsRepository realizationsRepository;

    @MockBean
    private CommentsService commentsService;

    @MockBean
    private RealizationsService realizationsService;

    @MockBean
    private IdeasService ideasService;

    @Test
    void contextLoads() {
    }

}
