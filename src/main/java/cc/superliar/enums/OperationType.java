package cc.superliar.enums;

/**
 * 日志操作解释
 *
 * @date 10/27/15
 * @since JDK1.8
 */
public enum OperationType {

  LOGIN(0),
  CREATE(1),
  READ(2),
  UPDATE(3),
  DELETE(4);

  int code;

  OperationType(int code) {
    this.code = code;
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }
}
