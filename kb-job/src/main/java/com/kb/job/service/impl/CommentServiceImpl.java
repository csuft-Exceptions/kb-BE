package com.kb.job.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kb.common.base.BaseResponse;
import com.kb.common.utils.AssertUtil;
import com.kb.job.dao.CommentMapper;
import com.kb.job.pojo.comment.Comment;
import com.kb.job.pojo.comment.SecondCommentParam;
import com.kb.job.service.api.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author mawz
 * @version 1.0
 * @date 2022-07-24 - 15:49
 */
@Service
@Slf4j
public class CommentServiceImpl implements CommentService {

    @Resource
    private CommentMapper commentMapper;
    @Override
    public BaseResponse getFirstComment(Long videoId) {
        List<Comment> list=commentMapper.getFirstComment(videoId);
        return BaseResponse.success(list,list.size());
    }

    @Override
    public BaseResponse getSecondComment(SecondCommentParam secondCommentParam) {
        List<Comment> list=commentMapper.getSecondComment(secondCommentParam);
        PageHelper.startPage(secondCommentParam.getPage(),secondCommentParam.getLimit());
        PageInfo<Comment> pageInfo=new PageInfo<>(list);
        return BaseResponse.success(pageInfo.getList(),pageInfo.getList().size());
    }

    @Override
    public BaseResponse reply(Comment comment) {
        Integer count=commentMapper.add(comment);
        AssertUtil.assertNotEquals(1,count,"回复失败,请重试!");
        return BaseResponse.success("回复成功!");
    }

    @Override
    public BaseResponse update(Comment comment) {
        Integer count=commentMapper.update(comment);
        AssertUtil.assertNotEquals(1,count,"更新操作失败,请重试!");
        return BaseResponse.success("更新成功!");
    }

    @Override
    public BaseResponse delete(Long id) {
        Integer count=commentMapper.delete(id);
        AssertUtil.assertNotEquals(1,count,"删除操作失败,请重试!");
        return BaseResponse.success("删除成功!");
    }
}
