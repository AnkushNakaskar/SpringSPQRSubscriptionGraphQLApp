package com.graphql.subscription.service;

import com.graphql.subscription.bean.Profile;
import com.graphql.subscription.repository.ProfileRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * @author ankushnakaskar
 */
@Service
@Slf4j
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    public List<Profile> profiles() {
        log.info("Fetching all the profiles ....!!!!");
        return StreamSupport.stream(profileRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }
}
