package com.fb2.fb.repository;


import com.fb2.fb.model.Favorites;
import com.fb2.fb.model.Post;
import com.fb2.fb.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoritesRepository  extends JpaRepository<Favorites, Long> {
Favorites getFavoritePostsByPostTitleAndUser(String postTitle, User user);
    List<Favorites> findAllByUser(User authenticatedUser);
}
