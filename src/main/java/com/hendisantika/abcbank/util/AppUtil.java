package com.hendisantika.abcbank.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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

    /**
     * <pre>
     * if <i>String</i>:
     *  null	@return false
     *  ""	@return false
     *  " "	@return false
     *  if <i>Collection</i>:
     *  null or empty	@return false
     *  if <i>Map</i>:
     *  null or empty	@return false
     * </pre>
     *
     * @param objects
     * @return
     */
    public static boolean isNotNullNotEmptyObjects(Object... objects) {
        Predicate<Object> predicateStr = (x -> String.class.isAssignableFrom(x.getClass())
                && StringUtils.isEmpty(x.toString().trim()));
        Predicate<Object> predicateColl = (x -> Collection.class.isAssignableFrom(x.getClass())
                && ((Collection<?>) x).isEmpty());
        Predicate<Object> predicateMap = (x -> Map.class.isAssignableFrom(x.getClass()) && ((Map<?, ?>) x).isEmpty());

        return isNotNullObjects(objects) && !Arrays.stream(objects)
                .filter(x -> predicateStr.or(predicateColl).or(predicateMap).test(x)).findAny().isPresent();
    }

    /**
     * <p>
     * Note: outClass object must have default constructor with no arguments
     * </p>
     *
     * @param entityList  list of entities that needs to be mapped
     * @param outCLass    class of result list element
     * @param modelMapper ModelMapper instance
     * @param <D>         type of objects in result list
     * @param <T>         type of entity in <code>entityList</code>
     * @return list of mapped object with <code><D></code> type.
     */
    public static <D, T> List<D> mapAll(final Collection<T> entityList, Class<D> outCLass, ModelMapper modelMapper) {
        return entityList.stream().map(entity -> modelMapper.map(entity, outCLass)).collect(Collectors.toList());
    }

}
