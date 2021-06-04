package com.fb2.fb.Controller;


import com.fb2.fb.model.*;
import com.fb2.fb.service.CommentService;
import com.fb2.fb.service.PostLikeService;
import com.fb2.fb.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;


@Controller
public class PostController {

    @Autowired
    private PostService postservice;
    @Autowired
    private CommentService commentservice;

    @Autowired
    PostLikeService likeService;


    @RequestMapping("/facebookHome")
    public ModelAndView viewHomePage(Model model, HttpSession httpSession) {
        ModelAndView mav = new ModelAndView("fbindex");
        User user = (User) httpSession.getAttribute("user");
        mav.addObject("user",user);
        // post object to accept post attribute is available on the facebook home page
        Post post = new Post();
        mav.addObject("post",post);
        // list of posts objects  to display list of all post is also available on the facebook home page

//        List<String> listPosts2 = new ArrayList<>();
        List<Post> listPosts = postservice.listAll();
//        for (Post postt:listPosts) {
//            listPosts2.add("Title : " + postt.getTitle());
//            listPosts2.add("postId : " + postt.getPostId());
//            listPosts2.add("postContent : " + postt.getPostContent());
//            listPosts2.add("Post by " + postt.getUser().getFirstname() + " "+ postt.getUser().getLastname());
//        }

        mav.addObject("listPosts",listPosts);

//        Comment comment = new Comment();
        mav.addObject("newComment", new Comment());
        PostLike postLike = new PostLike();
        mav.addObject("newPostLikes",postLike);
        mav.addObject("newPost", new Post());
        model.addAttribute("newCommentLikes", new CommentLike());
        model.addAttribute("deleteComment", new Comment());

//        model.addAttribute("allComment", commentservice.findAll(postId));

        return mav;
    }

    @RequestMapping(value = "/savePost", method = RequestMethod.POST)
    public String savePost(@ModelAttribute("post") Post post, HttpSession session) {
        System.err.println("post to be saved =" + post);
        User authenticatedUser = (User) session.getAttribute("user");
        //just inserted this to confirm the person posting is login and in session
        if(authenticatedUser == null){return "redirect:/";}


//        model.addAttribute("post",post);
        postservice.addPost(post, authenticatedUser);
        return "redirect:/facebookHome";
    }

    @RequestMapping("/editPost/{postId}")
    public ModelAndView showEditPostPage(@PathVariable(name = "postId") long postId, HttpSession session) {
        User userObj = (User) session.getAttribute("user");
        ModelAndView mav = new ModelAndView("Post_edit2");
        Post post = postservice.get(postId);
        mav.addObject("post", post);
        List<Post> listPosts = postservice.listAll();
        System.err.println("listof posts are " + listPosts);
        mav.addObject("listPosts",listPosts);
        return mav;
    }

    @RequestMapping("/deletePost/{postId}")
    public String deleteProduct(@PathVariable(name = "postId") long postId) {
        postservice.delete(postId);
        return "redirect:/facebookHome";
    }


    @RequestMapping("/savePostLikes/{postId}")
    public String likeIndex(@PathVariable("postId") Long id, HttpSession session, PostLike like, Model model) {
        User userObj = (User) session.getAttribute("user");
        if (userObj == null) return "redirect:/login";
        Post post = postservice.getPostById(id);
        PostLike postLike = likeService.getPostLikeByPostAndUser(post, userObj);
        like.setPost(post);
        like.setUser(userObj);
        if (postLike == null) {
            likeService.addLike(like);
        } else {
            likeService.deleteLike(postLike);
        }

        return "redirect:/facebookHome";
    }


}
