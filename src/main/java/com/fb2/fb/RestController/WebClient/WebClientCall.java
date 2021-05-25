//package com.fb2.fb.RestController.WebClient;
//
//import com.fb2.fb.model.Post;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.reactive.function.client.WebClient;
//
//import reactor.core.publisher.Flux;
//import reactor.core.publisher.Mono;
//
//import java.util.Arrays;
//import java.util.List;
//
//import static com.fb2.fb.Constants.WebClientConstants.GET_ALL_BLOG_POSTS;
//
//@Controller
//public class WebClientCall {
//
//    @Autowired
//    private WebClient webClient;
//
//    public WebClientCall(WebClient webClient){
//        this.webClient = webClient;
//    }
//
////    http://localhost:5002/api/v1/post/BlogPosts
//
//
//    @GetMapping(value = "/webclient/BlogPosts")
//    public List<Post> getAllBlogPosts()
//    {
//        return webClient.get()
//                .uri(GET_ALL_BLOG_POSTS)
//                .retrieve()
//                .bodyToFlux(Post.class)
//                .collectList()
//                .block();
//    }
//
//
//
//
////    @GetMapping("/countries")
////    public List<Object> getCountries(){
////        Object[] countries = restTemplate.getForObject(url,Object[].class);
////        return Arrays.asList(countries);
////
////    }
//}
