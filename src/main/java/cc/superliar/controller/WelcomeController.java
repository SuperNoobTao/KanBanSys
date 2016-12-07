package cc.superliar.controller;

import cc.superliar.component.ResultHelper;
import cc.superliar.constant.ResourceURL;
import cc.superliar.constant.VersionConstant;
import cc.superliar.po.User;
import cc.superliar.vo.ResultVO;
import cc.superliar.vo.WelcomeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Welcome controller.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 6/25/15
 * @since JDK1.8
 */
@RestController
@RequestMapping(ResourceURL.RESOURCES + VersionConstant.V1)
public class WelcomeController {

  // ------------------------
  // PUBLIC METHODS
  // ------------------------

  /**
   * Welcome somebody.
   *
   * @param user user
   * @return user's name
   */
  @RequestMapping(ResourceURL.WELCOME)
  public ResultVO welcome(@AuthenticationPrincipal User user) {
    return resultHelper.successResp(new WelcomeVO(user.getId(), String.format(template, user.getName())));
  }

  // ------------------------
  // PRIVATE FIELDS
  // ------------------------

  private static final String template = "Hello, %s!";

  @Autowired
  private ResultHelper resultHelper;

}
