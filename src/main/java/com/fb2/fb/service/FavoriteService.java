package com.fb2.fb.service;

import com.fb2.fb.model.Favorites;
import com.fb2.fb.model.Post;
import com.fb2.fb.model.User;
import com.fb2.fb.repository.FavoritesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class FavoriteService {
    @Autowired
    FavoritesRepository favoritesRepository;

    public Favorites getFavoritesByTitleAndUser(String title, User user) {
        return favoritesRepository.getFavoritePostsByPostTitleAndUser(title, user);
    }

    public void addFavourite(Favorites favorites) {
        favoritesRepository.save(favorites);
    }

    public void deleteFavorite(Favorites favorites) {
        favoritesRepository.delete(favorites);
    }


    public List<Favorites> findAllFavoritesByUser(User authenticatedUser) {
        return favoritesRepository.findAllByUser(authenticatedUser);
    }


    public Boolean checkExistence(Long id){
        if(favoritesRepository.existsById(id)){
            return true;
        }
        return false;
    }

    public void deleteFavoriteById(Long favoriteId) {
        favoritesRepository.deleteById(favoriteId);
    }
}