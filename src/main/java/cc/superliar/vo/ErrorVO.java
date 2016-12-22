package cc.superliar.vo;

import java.io.Serializable;

/**
 * Result VO.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 8/19/15
 * @since JDK1.8
 */
public class ErrorVO implements Serializable {

  private static final long serialVersionUID = -7144407219523712074L;

  private String code;

  private String message;

  public ErrorVO() {}

  public ErrorVO(String code) {
    this.code = code;
  }

  public ErrorVO(String code, String message) {
    this.code = code;
    this.message = message;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

}
