package com.softwarefoundation.suporteportalapi.resource;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.util.concurrent.ExecutionException;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class LoginAttempService {

    /**
     *
     */
    private static final int MAXIMUM_NUMBER_OF_ATTEMPS = 5;

    /**
     *
     */
    private static final int ATTEMP_INCREMENT = 1;

    /**
     *
     */
    private LoadingCache<String, Integer> loginAttempCache;

    public LoginAttempService() {
        super();
        loginAttempCache = CacheBuilder.newBuilder().refreshAfterWrite(15, TimeUnit.MINUTES)
                .maximumSize(100).build(new CacheLoader<String, Integer>() {
                    @Override
                    public Integer load(String key) throws Exception {
                        return 0;
                    }
                });
    }

    /**
     * @param username
     */
    public void evictUserFromLoginAttempCache(String username) {
        log.info("Bloqueio login usuário: {}", username);
        loginAttempCache.invalidate(username);
    }

    /**
     *
     */
    public void adaUserToLoginAttempCache(String username) {
        try {
            int attemps = Integer.sum(ATTEMP_INCREMENT, loginAttempCache.get(username));
            loginAttempCache.put(username, attemps);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param username
     * @return
     */
    public Boolean hasExceedMaxAttemps(String username) {
        boolean hasExceedMaxAttemps = false;
        try {
            hasExceedMaxAttemps = loginAttempCache.get(username) >= MAXIMUM_NUMBER_OF_ATTEMPS;
            if (hasExceedMaxAttemps) {
                log.info("Usuario: {}, excedeu a quantidade máxima({}) de tentativas de login.", username, MAXIMUM_NUMBER_OF_ATTEMPS);
            }
            return hasExceedMaxAttemps;
        } catch (ExecutionException e) {
            e.printStackTrace();
            return false;
        }
    }

}
