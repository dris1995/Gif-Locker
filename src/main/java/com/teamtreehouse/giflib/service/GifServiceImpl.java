package com.teamtreehouse.giflib.service;

import com.teamtreehouse.giflib.dao.GifDao;
import com.teamtreehouse.giflib.model.Gif;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class GifServiceImpl implements GifService {

    @Autowired
    private GifDao gifDao;

    @Override
    public List<Gif> findAll() {
        return gifDao.findAll();
    }

    @Override
    public Gif findById(Long id) {
        return gifDao.findById(id);
    }

    @Override
    public void save(Gif gif, MultipartFile file) {
        try {
            gif.setBytes(file.getBytes());
            gifDao.save(gif);
        } catch (IOException e) {
            System.err.println("Unable to get byte array form uploaded file");
        }
    }

    @Override
    public void delete(Gif gif) {
        gifDao.delete(gif);

    }

    @Override
    public void toggleFavorite(Gif gif) {
        gif.setFavorite(!gif.isFavorite());
        gifDao.save(gif);
    }

    @Override
    public List<Gif> getFavoriteGifs() {

        List<Gif> gifs = new ArrayList<>();

        for (Gif gif : gifDao.findAll()){
            if (gif.isFavorite() == true){
                gifs.add(gif);
            }
        }//end loop

        return gifs;//return list of gifs  that have the favorite marked

    }//end getFavoriteGifs()

    @Override
    public List<Gif> searchByName(String search) {
        List<Gif> gifs = new ArrayList<>();

        for (Gif gif : gifDao.findAll()){
            if (gif.getDescription().toLowerCase().contains(search.trim().toLowerCase())){
                gifs.add(gif);
            }
        }//end loop

        return gifs;//return list of gifs  that have the favorite marked

    }//end searchByName()
}// End class
