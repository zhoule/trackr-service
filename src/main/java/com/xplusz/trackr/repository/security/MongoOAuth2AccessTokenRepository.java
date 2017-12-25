package com.xplusz.trackr.repository.security;

/**
 * Created by hank on 5/21/17.
 */
import com.xplusz.trackr.model.security.MongoOAuth2AccessToken;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoOAuth2AccessTokenRepository extends MongoRepository<MongoOAuth2AccessToken, String>, MongoOAuth2AccessTokenRepositoryBase {

}
