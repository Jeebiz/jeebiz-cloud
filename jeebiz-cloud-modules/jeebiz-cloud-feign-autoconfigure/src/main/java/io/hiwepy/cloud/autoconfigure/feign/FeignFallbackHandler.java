/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.autoconfigure.feign;

/**
 * @author wandl
 */
public interface FeignFallbackHandler {

    default void handle(Throwable throwable) {

    }

}
