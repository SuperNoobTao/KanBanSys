package cc.superliar.domain;



import cc.superliar.component.Transformer;
import cc.superliar.constant.CommonsConstant;
import cc.superliar.enums.ErrorType;
import cc.superliar.enums.ValidFlag;
import cc.superliar.exception.CommonsException;
import cc.superliar.repo.CustomRepository;
import cc.superliar.util.ErrorMsgHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Abstract base service implement.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 7/21/15
 * @since JDK1.8
 */
public abstract class BaseDomain<T, ID extends Serializable> {


  /**
   * Get all <T>.
   *
   * @return <T>s
   * @throws Exception
   */
  @SuppressWarnings("unchecked")
  public <VO> List<VO> getAll(Specification<T> specification, Sort sort, Class<VO> voType) throws Exception {
    List pos = repository.findAll(specification, sort);
    if (pos.isEmpty()) {
      // Throw po cannot find exception.
      throw new CommonsException(ErrorType.SYS0121, ErrorMsgHelper.getReturnMsg(ErrorType.SYS0121, getClassT().getSimpleName(), getClassT().getSimpleName()));
    }
    return transformer.pos2VOs(voType, pos);
  }

  // --------------------------
  // PRIVATE FIELDS AND METHODS
  // --------------------------

  @Autowired
  private CustomRepository<T, ID> repository;

//  @Autowired
//  protected LogHelper logHelper;

  @Autowired
  private Transformer transformer;


  /**
   * Get class of <T>
   *
   * @return class of <T>
   * @throws Exception
   */
  @SuppressWarnings("unchecked")
  protected Class<T> getClassT() throws Exception {
    Type type = getClass().getGenericSuperclass();
    return (Class) ((ParameterizedType) type).getActualTypeArguments()[0];
  }

  /**
   * Set invalid flag
   *
   * @param po po
   * @return po with invalid flag
   * @throws Exception
   */
  private T setInvalid(T po) throws Exception {
    Field validFlagField = po.getClass().getDeclaredField(CommonsConstant.VALID_FLAG);
    validFlagField.setAccessible(true);
    validFlagField.set(po, ValidFlag.INVALID);
    return po;
  }
}
