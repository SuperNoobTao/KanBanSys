package cc.superliar.domain;

import cc.superliar.component.Transformer;
import cc.superliar.constant.CommonsConstant;
import cc.superliar.enums.ErrorType;
import cc.superliar.enums.ValidFlag;
import cc.superliar.exception.CommonsException;
import cc.superliar.param.ResourceParam;
import cc.superliar.po.Resource;
import cc.superliar.po.Role;
import cc.superliar.po.User;
import cc.superliar.repo.ResourceReposity;
import cc.superliar.util.ErrorMsgHelper;
import cc.superliar.vo.ResourceVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Domain of {@link Resource}
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 10/17/15
 * @since JDK1.8
 */
@Service
@Transactional(readOnly = true) public class ResourceDomain extends BaseDomain<Resource, Long> {

  // ------------------------
  // PUBLIC METHODS
  // ------------------------

  /**
   * Create new {@link Resource}.
   *
   * @param currentUser current user
   * @param param       {@link ResourceParam}
   * @return {@link ResourceVO}
   * @throws CommonsException {@link ErrorType#SYS0111} resource already existing, name taken.
   */
  @Transactional
  public ResourceVO create(ResourceParam param, User currentUser) throws Exception {
    nameExists(param.getName());
    return super.createByPO(ResourceVO.class, resourceParam2PO(param, new Resource(), currentUser), currentUser);
  }

  /**
   * Show {@link ResourceVO} by name of resource.
   *
   * @param param {@link ResourceParam}
   * @return {@link ResourceVO}
   * @throws CommonsException {@link ErrorType#SYS0122} Cannot find any resource by name param.
   */
  public ResourceVO getResourceByName(ResourceParam param) throws Exception {
    return transformer.po2VO(ResourceVO.class, findByName(param.getName()));
  }

  /**
   * Show {@link ResourceVO} by path of resource.
   *
   * @param param {@link ResourceParam}
   * @return {@link ResourceVO}
   * @throws CommonsException {@link ErrorType#SYS0122} Cannot find any resource by path param.
   */
  public ResourceVO getResourceByPath(ResourceParam param) throws Exception {
    return transformer.po2VO(ResourceVO.class, findByPath(param.getPath()));
  }

  public Resource findByName(String name) throws Exception {
    return resourceRepository.findByNameAndValidFlag(name, ValidFlag.VALID).orElse(null);
  }

  /**
   * Update {@link Resource}.
   *
   * @param currentUser current user
   * @param param       {@link ResourceParam}
   * @return {@link ResourceVO}
   * @throws CommonsException {@link ErrorType#SYS0122} Cannot find any resource by id param.
   */
  @Transactional
  public ResourceVO update(ResourceParam param, User currentUser) throws Exception {
    Resource resource = findById(param.getId());
    if (!param.getName().equals(resource.getName())) {
      nameExists(param.getName());
    }
    return super.updateByPO(ResourceVO.class, resourceParam2PO(param, resource, currentUser), currentUser);
  }

  public Resource findById(Long id) {
    return resourceRepository.findById(id).orElse(null);
  }

  // --------------------------
  // PRIVATE FIELDS AND METHODS
  // --------------------------

  @Autowired
  private RoleDomain roleDomain;

  @Autowired
  private ResourceReposity resourceRepository;

  @Autowired
  private Transformer transformer;

  private static final String PATH = "path";

  /**
   * Transform {@link ResourceParam} to {@link Resource}.
   *
   * @param param       {@link ResourceParam}
   * @param resource    {@link Resource
   * @param currentUser currentUser
   * @return {@link Resource
   */
  private Resource resourceParam2PO(ResourceParam param, Resource resource, User currentUser) throws Exception {
    transformer.param2PO(getClassT(), param, resource, currentUser);
    if (!StringUtils.isBlank(param.getRoleIds())) {
      List<Role> roles = roleDomain.getAllByIds(transformer.idsStr2List(param.getRoleIds()));
      resource.setRoles(transformer.list2Set(roles));
    }
    return resource;
  }

  private Resource findByPath(String path) throws Exception {
    return resourceRepository.findByNameAndValidFlag(path, ValidFlag.VALID).orElse(null);
  }

  private void nameExists(String name) throws Exception {
    if (resourceRepository.findByNameAndValidFlag(name, ValidFlag.VALID).isPresent()) {
      // Throw group already existing exception, name taken.
      throw new CommonsException(ErrorType.SYS0111, ErrorMsgHelper.getReturnMsg(ErrorType.SYS0111, getClassT().getSimpleName(), CommonsConstant.NAME));
    }
  }

}
