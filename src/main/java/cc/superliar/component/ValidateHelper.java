package cc.superliar.component;


//import com.saintdan.framework.domain.ClientDomain;

import java.lang.reflect.Field;

import cc.superliar.annotation.NotNullField;
import cc.superliar.constant.ControllerConstant;
import cc.superliar.enums.ErrorType;
import cc.superliar.enums.OperationType;
import cc.superliar.param.BaseParam;
import cc.superliar.po.User;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

/**
 * Validate helper.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 11/4/15
 * @since JDK1.8
 */
@Component public class ValidateHelper {

  // ------------------------
  // PUBLIC METHODS
  // ------------------------

  /**
   * Validate current user, param and sign.
   *
   * @param param         param bean
   * @param result        bind result
   * @param currentUser   current user
   * @param logger        log
   * @param operationType {@link OperationType}
   * @return 422
   * @throws Exception
   */
  public ResponseEntity validate(BaseParam param, BindingResult result, User currentUser, Logger logger, OperationType operationType) throws Exception {
    if (result.hasErrors()) {
      return resultHelper.infoResp(ErrorType.SYS0002, result.toString(), HttpStatus.UNPROCESSABLE_ENTITY);
    }
    return validate(param, currentUser, logger, operationType);
  }

  /**
   * Validate current user and sign.
   *
   * @param param         param bean
   * @param currentUser   currentUser
   * @param logger        log
   * @param operationType {@link OperationType}
   * @return 422
   * @throws Exception
   */
  public ResponseEntity validate(BaseParam param, User currentUser, Logger logger, OperationType operationType) throws Exception {
    //check currentUser
    if (currentUser == null || currentUser.getId() == null) {
      return resultHelper.infoResp(logger, ErrorType.SYS0003, ErrorType.SYS0003.description(), HttpStatus.UNPROCESSABLE_ENTITY);
    }
    return validate(param, operationType);
  }

  /**
   * Bean properties null validation.
   *
   * @param param         Param bean
   * @param operationType {@link OperationType}
   * @return 422
   * @throws Exception
   */
  public ResponseEntity validate(BaseParam param, OperationType operationType) throws Exception {
    Field[] fields = param.getClass().getDeclaredFields();
    for (Field field : fields) {
      if (field == null || !field.isAnnotationPresent(NotNullField.class)) {
        continue; // Ignore field without ParamField annotation.
      }
      field.setAccessible(true);
      if (ArrayUtils.contains(field.getAnnotation(NotNullField.class).value(), operationType) && field.get(param) == null) {
        return resultHelper.infoResp(ErrorType.SYS0002, String.format(ControllerConstant.PARAM_BLANK, field.getName()), HttpStatus.UNPROCESSABLE_ENTITY);
      }
    }
    return new ResponseEntity(HttpStatus.OK);
  }
//
//  /**
//   * Validate sign.
//   *
//   * @param param  param
//   * @param logger log
//   * @return 422
//   * @throws Exception
//   */
//
//  public ResponseEntity validateWithRSASignCheck(BaseParam param, Logger logger) throws Exception {
//    // Get current clientId
//    String clientId = SpringSecurityUtils.getCurrentClientId();
//    // Sign verification.
//    if (!signHelper.signCheck(getPublicKeyByClientId(clientId), param)) {
//      // Return rsa signature failed information and log the exception.
//      return resultHelper.infoResp(logger, ErrorType.SYS0004, ErrorType.SYS0004.description(), HttpStatus.UNPROCESSABLE_ENTITY);
//    } else
//      return null;
//  }

  // --------------------------
  // PRIVATE FIELDS AND METHODS
  // --------------------------

  @Autowired private ResultHelper resultHelper;





}
