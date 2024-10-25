//package com.yash.MovieRoger.repository;
//
//import com.yash.MovieRoger.model.Movie;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.stereotype.Repository;
//
//import java.util.concurrent.TimeUnit;
//
//@Repository
//@Slf4j
//public class CacheRepository {
////    static final String SUB_STR = "Mov:";
//    public static final Integer MOVIE_CACHE_KEY_EXPIRY = 1;
//
//    @Autowired
//    RedisTemplate<String, Movie> redisTemplate;
//
//    public void set(Movie movie) {
//        String key = String.valueOf(movie.getTitle());
//        try {
//            redisTemplate.opsForValue().set(key, movie, MOVIE_CACHE_KEY_EXPIRY, TimeUnit.DAYS);
//            log.info("Cached movie with key: {}",key);
//        }catch (Exception e) {
//            log.error("Error caching movie with key {}", key, e);
//        }
//    }
//
//    public Movie get(String key) {
//        try{
//            Movie movie = redisTemplate.opsForValue().get(key);
//            if(movie != null) {
//                log.info("Retrieved movie data from key: {}",key);
//            }else {
//                log.info("Movie with key: {} not found", key);
//            }
//            return movie;
//        } catch (Exception e) {
//            log.error("Error retrieving movie with key {}", key, e);
//            return null;
//        }
//    }
//}
