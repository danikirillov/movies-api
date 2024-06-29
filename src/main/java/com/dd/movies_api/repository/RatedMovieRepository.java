package com.dd.movies_api.repository;

import com.dd.movies_api.model.RatedMovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RatedMovieRepository extends JpaRepository<RatedMovieEntity, Integer>
{
  RatedMovieEntity findByTitle(String title);
}
