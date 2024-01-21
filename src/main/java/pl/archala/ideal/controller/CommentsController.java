package pl.archala.ideal.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.archala.ideal.service.CommentsService;

@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentsController {

    private final CommentsService commentsService;
}
