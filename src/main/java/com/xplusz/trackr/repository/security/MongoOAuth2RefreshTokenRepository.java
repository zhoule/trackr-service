package com.xplusz.trackr.repository.security;

import com.xplusz.trackr.model.security.MongoOAuth2RefreshToken;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoOAuth2RefreshTokenRepository extends MongoRepository<MongoOAuth2RefreshToken, String>, MongoOAuth2RefreshTokenRepositoryBase {
}
