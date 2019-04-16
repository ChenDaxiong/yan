package com.hfut.bs.post.service;

import com.hfut.bs.common.page.TailPage;
import com.hfut.bs.post.dao.CommentMapper;
import com.hfut.bs.post.domain.Comment;
import com.hfut.bs.post.model.CommentInfoModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceImpl implements ICommentService {

    @Autowired
    private CommentMapper commentMapper;

    public boolean publishComment(CommentInfoModel commentInfoModel){
        int resultCount = commentMapper.insertSelective(toComment(commentInfoModel));
        // 将帖子评论数缓存到redis
        if (resultCount > 0){
            return true;
        }
        return false;
    }


    public TailPage<CommentInfoModel> getComments(int entityId,int entityType,TailPage<CommentInfoModel>  page){
           List<Comment> comments = commentMapper.selectComments(entityId,entityType,page);
           List<CommentInfoModel> results = new ArrayList<>();
           for (Comment comment : comments){
               // 主评论下子评论的数量
               int replyCount = commentMapper.selectCommentReplyCount(entityId,entityType,comment.getId());
               CommentInfoModel commentInfoModel = toCommentInfoModel(comment);
               commentInfoModel.setReplyCommentCount(replyCount);
               // 是否为子评论，子评论要带上原评论的信息
               if (comment.getRefId()!=null){
                   Comment parentComment = commentMapper.selectByPrimaryKey(comment.getId());
                   commentInfoModel.setRefContent(parentComment.getContent());
                   commentInfoModel.setRefUserName(parentComment.getUsername());
               }
               // todo 获取点赞数
               results.add(commentInfoModel);
           }
           int commentsCount = commentMapper.selectTotalCommentCount(entityId, entityType);
           // 根据总数量去初始化页码
           page.setItemsTotalCount(commentsCount);
           page.setItems(results);
           return page;
    }


    public TailPage<CommentInfoModel> getReplyComments(int entityId, int entityType,
                                                       int commentId, TailPage<CommentInfoModel> page) {
        List<Comment> comments = commentMapper.selectReplyComments(entityId, entityType, commentId, page);
        List<CommentInfoModel> results = new ArrayList<>();
        int commentCount = commentMapper.selectCommentReplyCount(entityId, entityType, commentId);
        for (Comment comment : comments) {
            CommentInfoModel commentInfoModel = toCommentInfoModel(comment);
            // 获取子评论的回复数
            commentInfoModel.setReplyCommentCount(
                    commentMapper.selectCommentReplyCount(entityId, entityType, comment.getId()));
            results.add(commentInfoModel);
        }
        page.setItems(results);
        return page;
    }

    public int getTotalCommentCount(int entityId,int entityType){
        return commentMapper.selectTotalCommentCount(entityId, entityType);
    }


    private Comment toComment(CommentInfoModel commentInfoModel){
        Comment comment = new Comment();
        BeanUtils.copyProperties(commentInfoModel,comment);
        return comment;
    }

    private CommentInfoModel toCommentInfoModel (Comment comment){
        CommentInfoModel commentInfoModel = new CommentInfoModel();
        BeanUtils.copyProperties(comment,commentInfoModel);
        return commentInfoModel;
    }






}
