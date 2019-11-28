/******************************************************************************
* Copyright (C) 2017 ShenZhen Powerdata Information Technology Co.,Ltd
* All Rights Reserved.
* 本软件为深圳博安达开发研制。未经本公司正式书面同意，其他任何个人、团体不得使用、
* 复制、修改或发布本软件.
*****************************************************************************/

package com.szboanda.shortmessagesend.common.utils;


import java.util.*;


/**
 * Miscellaneous collection utility methods.
 * Mainly for internal use within the framework.
 *
 * @author Juergen Hoeller
 * @author Rob Harrop
 * @author Arjen Poutsma
 * @since 1.1.3
 */
public abstract class CollectionUtils {

  /**
   * Return {@code true} if the supplied Collection is {@code null} or empty.
   * Otherwise, return {@code false}.
   * @param collection the Collection to check
   * @return whether the given Collection is empty
   */
  public static boolean isEmpty( Collection<?> collection) {
    return (collection == null || collection.isEmpty());
  }
  
  /**
   * Return {@code true} if the supplied Collection is {@code null} or empty.
   * Otherwise, return {@code false}.
   * @param collection the Collection to check
   * @return whether the given Collection is empty
   */
  public static boolean isNotEmpty( Collection<?> collection) {
      return !isEmpty(collection);
  }

  /**
   * Return {@code true} if the supplied Map is {@code null} or empty.
   * Otherwise, return {@code false}.
   * @param map the Map to check
   * @return whether the given Map is empty
   */
  public static boolean isEmpty( Map<?, ?> map) {
    return (map == null || map.isEmpty());
  }
  
  /**
   * Return {@code true} if the supplied Map is {@code null} or empty.
   * Otherwise, return {@code false}.
   * @param map the Map to check
   * @return whether the given Map is empty
   */
  public static boolean isNotEmpty( Map<?, ?> map) {
      return !isEmpty(map);
  }

  /**
   * Merge the given Properties instance into the given Map,
   * copying all properties (key-value pairs) over.
   * <p>Uses {@code Properties.propertyNames()} to even catch
   * default properties linked into the original Properties instance.
   * @param props the Properties instance to merge (may be {@code null})
   * @param map the target Map to merge the properties into
   */
  @SuppressWarnings("unchecked")
  public static <K, V> void mergePropertiesIntoMap( Properties props, Map<K, V> map) {
    if (props != null) {
      for (Enumeration<?> en = props.propertyNames(); en.hasMoreElements();) {
        String key = (String) en.nextElement();
        Object value = props.get(key);
        if (value == null) {
          // Allow for defaults fallback or potentially overridden accessor...
          value = props.getProperty(key);
        }
        map.put((K) key, (V) value);
      }
    }
  }




  /**
   * Check whether the given Collection contains the given element instance.
   * <p>Enforces the given instance to be present, rather than returning
   * {@code true} for an equal element as well.
   * @param collection the Collection to check
   * @param element the element to look for
   * @return {@code true} if found, {@code false} otherwise
   */
  public static boolean containsInstance( Collection<?> collection, Object element) {
    if (collection != null) {
      for (Object candidate : collection) {
        if (candidate == element) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * Return {@code true} if any element in '{@code candidates}' is
   * contained in '{@code source}'; otherwise returns {@code false}.
   * @param source the source Collection
   * @param candidates the candidates to search for
   * @return whether any of the candidates has been found
   */
  public static boolean containsAny(Collection<?> source, Collection<?> candidates) {
    if (isEmpty(source) || isEmpty(candidates)) {
      return false;
    }
    for (Object candidate : candidates) {
      if (source.contains(candidate)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Return the first element in '{@code candidates}' that is contained in
   * '{@code source}'. If no element in '{@code candidates}' is present in
   * '{@code source}' returns {@code null}. Iteration order is
   * {@link Collection} implementation specific.
   * @param source the source Collection
   * @param candidates the candidates to search for
   * @return the first present object, or {@code null} if not found
   */
  @SuppressWarnings("unchecked")
  
  public static <E> E findFirstMatch(Collection<?> source, Collection<E> candidates) {
    if (isEmpty(source) || isEmpty(candidates)) {
      return null;
    }
    for (Object candidate : candidates) {
      if (source.contains(candidate)) {
        return (E) candidate;
      }
    }
    return null;
  }

  /**
   * Find a single value of the given type in the given Collection.
   * @param collection the Collection to search
   * @param type the type to look for
   * @return a value of the given type found if there is a clear match,
   * or {@code null} if none or more than one such value found
   */
  @SuppressWarnings("unchecked")
  
  public static <T> T findValueOfType(Collection<?> collection,  Class<T> type) {
    if (isEmpty(collection)) {
      return null;
    }
    T value = null;
    for (Object element : collection) {
      if (type == null || type.isInstance(element)) {
        if (value != null) {
          // More than one value found... no clear single value.
          return null;
        }
        value = (T) element;
      }
    }
    return value;
  }


  /**
   * Determine whether the given Collection only contains a single unique object.
   * @param collection the Collection to check
   * @return {@code true} if the collection contains a single reference or
   * multiple references to the same instance, {@code false} otherwise
   */
  public static boolean hasUniqueObject(Collection<?> collection) {
    if (isEmpty(collection)) {
      return false;
    }
    boolean hasCandidate = false;
    Object candidate = null;
    for (Object elem : collection) {
      if (!hasCandidate) {
        hasCandidate = true;
        candidate = elem;
      }
      else if (candidate != elem) {
        return false;
      }
    }
    return true;
  }

  /**
   * Find the common element type of the given Collection, if any.
   * @param collection the Collection to check
   * @return the common element type, or {@code null} if no clear
   * common type has been found (or the collection was empty)
   */
  
  public static Class<?> findCommonElementType(Collection<?> collection) {
    if (isEmpty(collection)) {
      return null;
    }
    Class<?> candidate = null;
    for (Object val : collection) {
      if (val != null) {
        if (candidate == null) {
          candidate = val.getClass();
        }
        else if (candidate != val.getClass()) {
          return null;
        }
      }
    }
    return candidate;
  }

  /**
   * Retrieve the last element of the given Set, using {@link SortedSet#last()}
   * or otherwise iterating over all elements (assuming a linked set).
   * @param set the Set to check (may be {@code null} or empty)
   * @return the last element, or {@code null} if none
   * @since 5.0.3
   * @see SortedSet
   * @see LinkedHashMap#keySet()
   * @see java.util.LinkedHashSet
   */
  
  public static <T> T lastElement( Set<T> set) {
    if (isEmpty(set)) {
      return null;
    }
    if (set instanceof SortedSet) {
      return ((SortedSet<T>) set).last();
    }

    // Full iteration necessary...
    Iterator<T> it = set.iterator();
    T last = null;
    while (it.hasNext()) {
      last = it.next();
    }
    return last;
  }

  /**
   * Retrieve the last element of the given List, accessing the highest index.
   * @param list the List to check (may be {@code null} or empty)
   * @return the last element, or {@code null} if none
   * @since 5.0.3
   */
  
  public static <T> T lastElement( List<T> list) {
    if (isEmpty(list)) {
      return null;
    }
    return list.get(list.size() - 1);
  }




}
