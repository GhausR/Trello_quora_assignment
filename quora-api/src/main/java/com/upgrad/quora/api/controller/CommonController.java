package com.upgrad.quora.api.controller;

import com.upgrad.quora.api.model.UserDetailsResponse;
import com.upgrad.quora.service.business.CommonControllerService;
import com.upgrad.quora.service.entity.UserEntity;
import com.upgrad.quora.service.exception.AuthorizationFailedException;
import com.upgrad.quora.service.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CommonController {

    @Autowired
    CommonControllerService service;

    @GetMapping(path = "userprofile/{userId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<UserDetailsResponse> getUserDetails(@PathVariable(name = "userId") final String uuid,
                                                              @RequestHeader(name = "authorization") final String authToken) throws AuthorizationFailedException, UserNotFoundException {


        String[] stringArray = authToken.split("Bearer ");
        String accessToken = stringArray[1];

        service.checkAuthToken(accessToken);
        UserEntity userEntity = service.getUserDetails(uuid);

        UserDetailsResponse response = new UserDetailsResponse().firstName(userEntity.getFirstName()).
                lastName(userEntity.getLastName()).userName(userEntity.getUserName()).
                emailAddress(userEntity.getEmail()).country(userEntity.getCountry()).
                aboutMe(userEntity.getAboutMe()).dob(userEntity.getDob()).
                contactNumber(userEntity.getContactNumber());

        return new ResponseEntity<UserDetailsResponse>(response, HttpStatus.OK);
    }
}
