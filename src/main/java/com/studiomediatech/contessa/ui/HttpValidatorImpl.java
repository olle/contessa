package com.studiomediatech.contessa.ui;

import com.studiomediatech.contessa.app.autoconfigure.ContessaProperties;
import com.studiomediatech.contessa.validation.ValidationService;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;


/**
 * A validation component, common for both the Web and RESTful UIs.
 */
public class HttpValidatorImpl implements HttpValidator {

    private final ValidationService validationService;
    private final ContessaProperties props;

    public HttpValidatorImpl(ValidationService validationService, ContessaProperties props) {

        this.validationService = validationService;
        this.props = props;
    }

    @Override
    public void validateForUpload(String filename, byte[] payload) {

        validationService.validFilename(filename);
        validationService.validPayload(payload);
    }


    @Override
    public void validateRequestedName(String name) {

        validationService.validName(name);
    }


    @Override
    public void validateRequestedIdentifier(String identifier) {

        validationService.validIdentifier(identifier);
    }


    @Override
    public void validateCookies(HttpServletRequest request) {

        List<String> cookies = Optional.ofNullable(request.getCookies())
                .map(Arrays::asList)
                .orElse(Collections.emptyList())
                .stream()
                .map(Cookie::getName)
                .distinct()
                .collect(Collectors.toList());

        if (!props.getRequiredCookies().stream().allMatch(cookies::contains)) {
            throw new MissingCookiesRequestException(cookies);
        }
    }


    @Override
    public void validateRemoteAddress(HttpServletRequest request) {

        List<String> allowed = Optional.ofNullable(props.getAllowedAddresses()).orElse(Collections.emptyList());

        if (allowed.isEmpty()) {
            return;
        }

        if (!allowed.contains(request.getRemoteAddr())) {
            throw new AddressNotAllowedRequestException(request.getRemoteAddr());
        }
    }
}
