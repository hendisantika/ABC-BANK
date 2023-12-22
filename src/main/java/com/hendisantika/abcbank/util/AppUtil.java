package com.hendisantika.abcbank.util;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Objects;

/**
 * Created by IntelliJ IDEA.
 * Project : ABC-BANK
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 12/22/23
 * Time: 08:48
 * To change this template use File | Settings | File Templates.
 */
@Slf4j
public class AppUtil {
    public static boolean isNotNullObjects(Object... objects) {
        boolean isAnyNull = Arrays.stream(objects).anyMatch(x -> Objects.isNull(x));
        return !isAnyNull;
    }

    public static boolean isNullObject(Object object) {
        return !isNotNullObjects(object);
    }

}
