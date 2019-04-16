package com.hfut.bs.post.service;

import com.hfut.bs.common.page.TailPage;
import com.hfut.bs.common.utils.BeanUtil;
import com.hfut.bs.post.dao.PostMapper;
import com.hfut.bs.post.domain.Post;
import com.hfut.bs.post.model.PostInfoModel;
import com.hfut.bs.post.redis.JedisUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("postService")
public class PostServiceImpl implements IPostService {

    @Autowired
    private PostMapper postMapper;

    @Autowired
    JedisUtils jedisUtils;


    @Override
    public boolean createSelectivity(PostInfoModel postInfoModel) {
        postMapper.insertSelective(toPost(postInfoModel));
        return true;
    }

    @Override
    public PostInfoModel getById(Integer id) {
        Post post  =  postMapper.selectByPrimaryKey(id);
        return null;
    }

    @Override
    public boolean updateSelectivity(PostInfoModel postInfoModel) {
        return false;
    }

    @Override
    public List<PostInfoModel> getHotPosts() {
        return null;
    }

    @Override
    public TailPage<PostInfoModel> getPostsByClassified(String classifyId, TailPage entityQuery) {
        return null;
    }

    private Post toPost(PostInfoModel postInfoModel){
        Post post = new Post();
        BeanUtils.copyProperties(postInfoModel,post);
        return post;
    }

    private PostInfoModel toPostInfoModel(Post post){
        PostInfoModel postInfoModel = new PostInfoModel();
        BeanUtils.copyProperties(post,postInfoModel);
        return postInfoModel;
    }
}
