  package cc.superliar.vo;

import cc.superliar.constant.ResultConstant;

import java.io.Serializable;

  /**
   * VO for {@link com.saintdan.framework.controller.WelcomeController}
   *
   * @author <a href="http://github.com/saintdan">Liao Yifan</a>
   * @date 6/25/15
   * @since JDK1.8
   */
  public class WelcomeVO implements Serializable {

    private static final long serialVersionUID = -7734398342573960351L;

    private String code = ResultConstant.OK;

    private String message = "Successful operation";

    private final long id;

    private final String content;

    public long getId() {
      return id;
    }

    public String getContent() {
      return content;
    }

    public WelcomeVO(long id, String content) {
      this.id = id;
      this.content = content;
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
