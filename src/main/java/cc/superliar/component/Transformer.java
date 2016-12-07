package cc.superliar.component;


import cc.superliar.constant.CommonsConstant;
import cc.superliar.po.User;
import cc.superliar.util.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Ids string transform to iterable helper.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 10/19/15
 * @since JDK1.8
 */
@Component
public class Transformer {

  // ------------------------
  // PUBLIC METHODS
  // ------------------------

  /**
   * Transform ids string to iterable.
   *
   * @param idsStr ids String
   * @return ids iterable
   */
  public List<Long> idsStr2List(String idsStr) {
    return Arrays.stream(idsStr.split(",")).map(Long::valueOf).collect(Collectors.toList());
  }

  public String IdList2IdsStr(List<Long> list){
    return list.stream().map(String::valueOf).collect(Collectors.joining(","));
  }

  /**
   * Transform object list to hash set.
   *
   * @param objects object iterable
   * @return object hash set
   */
  public <T> Set<T> list2Set(List<T> objects) {
    return new HashSet<>(objects);
  }


  /**
   * Transform object list to hash set.
   *
   * @param objects object iterable
   * @return object hash set
   */
  public <T> List<T> set2List(Set<T> objects) {
    return new ArrayList<>(objects);
  }

  /**
   * Transform PO page to PageVO.
   *
   * @param content       page content
   * @param pageable      page init
   * @param totalElements page total
   * @return PageVO
   */
  @SuppressWarnings("unchecked")
  public Page poPage2VO(List content, Pageable pageable, Long totalElements) {
    return new PageImpl<>(content, pageable, totalElements);
  }

  /**
   * Transform param to PO.
   *
   * @param type        class type
   * @param param       param
   * @param po          PO
   * @param currentUser current user
   * @param <T>         class
   * @return PO
   * @throws Exception
   */
  public <T> T param2PO(Class<T> type, Object param, T po, User currentUser) throws Exception {
    // Init createdBy, lastModifiedBy
    Long createdBy;
    Long lastModifiedBy;
    // Init transformer
    Field idField = type.getDeclaredField(CommonsConstant.ID);
    idField.setAccessible(true);

    Field lastModifiedDateField = type.getDeclaredField(CommonsConstant.LAST_MODIFIED_DATE);
    lastModifiedDateField.setAccessible(true);
    Date now = new Date();
    if (idField.get(po) == null) {
      createdBy = currentUser.getId();
      lastModifiedBy = createdBy;
    } else {
      lastModifiedBy = currentUser.getId();
    }
    // Set param.
    BeanUtils.copyPropertiesIgnoreNull(param, po);
    lastModifiedDateField.set(po, now);
    return po;
  }

  /**
   * Transform PO to VO
   *
   * @param pos PO
   * @return VO
   */
  @SuppressWarnings("unchecked")
  public List pos2VOs(Class<?> type, List pos) throws Exception {
    List voList = new ArrayList();
    for (Object po : pos) {
      Object vo = po2VO(type, po);
      voList.add(vo);
    }
    return voList;
  }

  /**
   * Transform PO to VO.
   *
   * @param po PO
   * @return VO
   */
  public <T> T po2VO(Class<T> clazz, Object po) throws InstantiationException, IllegalAccessException {
    T vo = clazz.newInstance();
    BeanUtils.copyPropertiesIgnoreNull(po, vo);
    return vo;
  }

}
