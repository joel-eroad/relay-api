package io.relay.rest;

import java.util.NoSuchElementException;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler({NoSuchElementException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleExceptionNotFound(final Exception ex) {
        logger.debug("Could not find resource", ex);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public CustomError handleIllegalArgumentException(final IllegalArgumentException ex) {
        logger.debug("Invalid Argument Received", ex);
        return new CustomError(HttpStatus.BAD_REQUEST, "Invalid Argument Received");
    }

    @ExceptionHandler({
        HttpMessageNotReadableException.class,
        MethodArgumentTypeMismatchException.class,
        HttpMediaTypeNotSupportedException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public CustomError handleBadRequests(final Exception ex) {
        logger.debug("Bad input from client", ex);
        return new CustomError(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ResponseBody
    public CustomError handleExceptionMethodNotAllowed(final Exception ex) {
        logger.debug("Method not allowed", ex);
        return new CustomError(HttpStatus.METHOD_NOT_ALLOWED, "HTTP Method not allowed");
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public CustomError handleException(HttpServletRequest httpServletRequest, Exception ex) {
        final String queryString = sanitizedQueryString(httpServletRequest.getQueryString(), 250);

        logger.error("Unknown exception [queryString=\"{}\" errorMessage=\"{}\"]", queryString, ex.getMessage(), ex);
        return new CustomError(HttpStatus.INTERNAL_SERVER_ERROR,
            "Oops something went wrong. Please contact us if this keeps occurring.");
    }

    /**
     * Logging should not be vulnerable to injection attacks.
     *
     * @param queryString The query string to sanitize.
     * @param maxLength The max acceptable length
     * @return A string that is no more characters than the max length requested, with new lines and tabs replaced.
     */
    private String sanitizedQueryString(String queryString, int maxLength) {
        if (queryString == null) {
            return "";
        }
        return queryString
            .substring(0, Math.min(queryString.length(), maxLength))
            .replaceAll("[\n]+", "\\\\n")
            .replaceAll("[\r]+", "\\\\r")
            .replaceAll("[\t]+", "\\\\t");
    }
}
