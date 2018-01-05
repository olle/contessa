package com.studiomediatech.contessa.ui.web;

import com.studiomediatech.contessa.logging.Loggable;
import com.studiomediatech.contessa.validation.ValidationService;


final class WebValidatorImpl implements WebValidator {

    private final ValidationService validationService;

    public WebValidatorImpl(ValidationService validationService) {

        this.validationService = validationService;
    }

    @Override
    public void validateContentRequest(String name) {

        validationService.validName(name);
    }
}

interface WebValidator extends Loggable {


    default void validateContentRequest(String name) {

        logger().warn("Not validating name {}", name);
    }
}
