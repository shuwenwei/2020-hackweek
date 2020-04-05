package com.sww.util;


import com.sww.exception.BadRequestException;
import org.springframework.validation.BindingResult;

import java.util.Objects;

/**
 * @author sww
 */
public class BindingResultUtil {
    private BindingResultUtil() {}

    public static void checkBinding(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String message = Objects
                    .requireNonNull(bindingResult.getFieldError())
                    .getDefaultMessage();
            throw new BadRequestException(message);
        }
    }
}
