package org.hillelNew.reviewSpring.controllers;

import lombok.NoArgsConstructor;
import org.hillelNew.reviewSpring.domain.Post;
import org.hillelNew.reviewSpring.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@NoArgsConstructor

@Controller
@RequestMapping("/posts")
public class PostController {

    @Autowired
    PostRepository postRepository;

    @GetMapping
    public String getForm(Model model) {
        addAttributes(model, new Post(), 0);
        return "post-form";
    }

    @PostMapping
    public String submit(Model model, Post post, @PathVariable(required = false) Integer currentPage) {
        if (post.getNickname().isEmpty()) {
            addAttributes(model, post, currentPage);
            model.addAttribute("errorNickname", "Nickname is absent, please enter it");
            return "post-form";
        }
        if (post.getRating() == null) {
            addAttributes(model, post, currentPage);
            model.addAttribute("errorRating", "Please choose one of the rating's options");
            return "post-form";
        }
        if (post.getText().isEmpty()) {
            addAttributes(model, post, currentPage);
            model.addAttribute("errorComment", "Comment is absent, please write it");
            return "post-form";
        }
        post.setCreated(LocalDateTime.now());
        postRepository.save(post);
        return "redirect:/posts";
    }

    @GetMapping("/{pageNumber}")
    public String pageChange(Model model, Post post, @PathVariable Integer pageNumber) {
        addAttributes(model, post, pageNumber);
        return "post-form";
    }

    @GetMapping("/{pageNumber}/{id}")
    public String updateComment(Model model, @PathVariable Integer pageNumber, @PathVariable Long id) {
        addAttributes(model, postRepository.findById(id).get(), pageNumber);
        return "post-form";
    }

    @GetMapping("/{pageNumber}/{id}/delete")
    public String delete(@PathVariable Long id) {
        postRepository.deleteById(id);
        return "redirect:/posts";
    }

    private void addAttributes(Model model, Post post, Integer currentPage) {
        model.addAttribute("post", post);
        model.addAttribute("ratingTypes", Post.Rating.values());
        Pageable sortedByCreated = PageRequest.of(currentPage, 10, Sort.by("created").descending());
        Page<Post> page = postRepository.findAll(sortedByCreated);
        int[] pageNumbers = new int[page.getTotalPages()];
        for (int i = 0; i < page.getTotalPages(); i++) {
            pageNumbers[i] = i;
        }
        model.addAttribute("pageNumbers", pageNumbers);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("posts", page);
    }


}
