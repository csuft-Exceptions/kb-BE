package com.kb.job.controller;

import com.kb.common.base.BaseResponse;
import com.kb.job.pojo.comment.Comment;
import com.kb.job.pojo.comment.SecondCommentParam;
import com.kb.job.service.api.CommentService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author mawz
 * @version 1.0
 * @date 2022-07-24 - 15:31
 */
@RestController
public class CommentController {

    @Resource
    private CommentService commentService;

    @GetMapping("/first-comment")
    public BaseResponse getFirstComment(Long videoId){
        return commentService.getFirstComment(videoId);
    }
    @GetMapping("/second-comment")
    public BaseResponse getSecondComment(SecondCommentParam secondCommentParam){
        return commentService.getSecondComment(secondCommentParam);
    }
    @PostMapping("/reply")
    public BaseResponse replay(@RequestBody Comment comment){
        return commentService.reply(comment);
    }

    @PutMapping("/comment")
    public BaseResponse update(@RequestBody Comment comment){
        return commentService.update(comment);
    }

    @DeleteMapping("/comment")
    public BaseResponse delete(Long id){
        return commentService.delete(id);
    }


}
