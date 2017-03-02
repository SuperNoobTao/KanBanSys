package cc.superliar.controller;

import cc.superliar.component.ResultHelper;
import cc.superliar.constant.ResourceURL;
import cc.superliar.constant.VersionConstant;
import cc.superliar.po.User;
import cc.superliar.vo.ResultVO;
import cc.superliar.vo.WelcomeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 */
@RestController
@RequestMapping(ResourceURL.RESOURCES + VersionConstant.V1)
public class WelcomeController {

  // ------------------------
  // PUBLIC METHODS
  // ------------------------

  /**
   * 欢迎页面
   *
   * @param user user
   * @return user's name
   */
  @RequestMapping(ResourceURL.WELCOME)
  public ResponseEntity welcome(@AuthenticationPrincipal User user) {
    return new ResponseEntity<>(new WelcomeVO(user.getId(), String.format(template, user.getName())), HttpStatus.OK);

  }

  // ------------------------
  // PRIVATE FIELDS
  // ------------------------

  private static final String template = "Hello, %s!";

  @Autowired
  private ResultHelper resultHelper;

}
