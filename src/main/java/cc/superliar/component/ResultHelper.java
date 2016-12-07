package cc.superliar.component;


import cc.superliar.constant.ResultConstant;
import cc.superliar.enums.ErrorType;
import cc.superliar.enums.OperationStatus;
import cc.superliar.util.LogUtils;
import cc.superliar.vo.ErrorVO;
import cc.superliar.vo.ResultVO;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 * Log debug message and return result.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 9/22/15
 * @since JDK1.8
 */
@Component
public class ResultHelper {

  /**
   * Return success result.
   *
   * @param object some vo.
   * @return result vo.
   */
  public ResultVO successResp(Object object) {
    ResultVO vo = new ResultVO(ResultConstant.OK);
    vo.setOperationStatus(OperationStatus.SUCCESS);
    if (vo.getMessage() == null) {
      vo.setMessage(ResultConstant.SUCCESS);
    }
    vo.setData(object);
    return vo;
  }

  /**
   * Return error information.
   *
   * @param errorType error type
   * @return result vo
   */
  public ResultVO infoResp(ErrorType errorType) {
    return infoResp(errorType, errorType.description());
  }

  /**
   * Return error information.
   *
   * @param errorType error type
   * @param msg       error message
   * @return result vo
   */
  public ResultVO infoResp(ErrorType errorType, String msg) {
    return new ResultVO(
            errorType.name(),
            OperationStatus.FAILURE,
            msg
    );
  }

  /**
   * Return error information.
   *
   * @param logger    Log
   * @param errorMsg  error message
   * @return result vo
   */
  public ResultVO infoResp(Logger logger, ErrorType errorType, String errorMsg) {
    LogUtils.trackInfo(logger, errorMsg);
    return new ResultVO(
            errorType.name(),
            OperationStatus.FAILURE,
            errorMsg
    );
  }

  /**
   * Return error information,
   * and log it to error.
   *
   * @param logger    Log
   * @param errorType error type
   * @param e         e
   * @return result vo
   */
  public ResultVO errorResp(Logger logger, Throwable e, ErrorType errorType) {
    return errorResp(logger, e, errorType, errorType.description());
  }

  /**
   * Return error information,
   * and log it to error.
   *
   * @param logger    Log
   * @param errorType error type
   * @param e         e
   * @param msg       error message
   * @return result vo
   */
  public ResultVO errorResp(Logger logger, Throwable e, ErrorType errorType, String msg) {
    LogUtils.traceError(logger, e, errorType.description());
    return new ResultVO(
            errorType.name(),
            OperationStatus.FAILURE,
            msg
    );
  }


  /**
   * Return error information,
   * and log it to error.
   *
   * @param logger    Log
   * @param errorType error type
   * @param e         e
   * @param msg       error message
   * @return response entity with error message.
   */
  @SuppressWarnings("unchecked")
  public ResponseEntity errorResp(Logger logger, Throwable e, ErrorType errorType, String msg, HttpStatus httpStatus) {
    LogUtils.traceError(logger, e, errorType.description());
    return new ResponseEntity(new ErrorVO(errorType.name(), msg), httpStatus);
  }

}
