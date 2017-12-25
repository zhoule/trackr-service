package com.xplusz.trackr.repository.security;


import com.xplusz.trackr.model.security.MongoOAuth2RefreshToken;

public interface MongoOAuth2RefreshTokenRepositoryBase {
    MongoOAuth2RefreshToken findByTokenId(String tokenId);

    boolean deleteByTokenId(String tokenId);
}
