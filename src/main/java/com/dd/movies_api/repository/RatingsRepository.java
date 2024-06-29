package com.dd.movies_api.repository;

import com.dd.movies_api.model.RatingsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingsRepository extends JpaRepository<RatingsEntity, Long>
{
  RatingsEntity findByTitleAndApiKey(String title, String apiKey);
}
