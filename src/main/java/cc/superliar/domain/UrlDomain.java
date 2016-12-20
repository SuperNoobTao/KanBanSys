package cc.superliar.domain;

import cc.superliar.component.Transformer;
import cc.superliar.constant.CommonsConstant;
import cc.superliar.enums.ErrorType;
import cc.superliar.enums.ValidFlag;
import cc.superliar.exception.CommonsException;
import cc.superliar.param.DeviceParam;
import cc.superliar.param.UrlParam;
import cc.superliar.po.*;
import cc.superliar.repo.UrlReposity;
import cc.superliar.util.ErrorMsgHelper;
import cc.superliar.vo.UrlVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by shentao on 2016/12/20.
 */
@Service
@Transactional(readOnly = true)
public class UrlDomain extends BaseDomain<Url,Long> {

    // ------------------------
    // PUBLIC METHODS
    // ------------------------

    /**
     * Create new {@link Url}.
     *
     * @param currentUser current user
     * @param param       {@link ResourceParam}
     * @return {@link ResourceVO}
     * @throws CommonsException {@link ErrorType#SYS0111} resource already existing, name taken.
     */
    @Transactional
    public UrlVO create(UrlParam param, User currentUser) throws Exception {
        contentExists(param.getContent());
        return super.createByPO(UrlVO.class, urlParam2PO(param, new Url(), currentUser), currentUser);
    }


    /**
     * Update {@link Url}.
     *
     * @param currentUser current user
     * @param param       {@link ResourceParam}
     * @return {@link ResourceVO}
     * @throws CommonsException {@link ErrorType#SYS0122} Cannot find any resource by id param.
     */
    @Transactional
    public UrlVO update(UrlParam param, User currentUser) throws Exception {
        Url url = findById(param.getId());
        if (StringUtils.isNotBlank(param.getContent())&&param.getContent().equals(url.getContent())) {
            contentExists(param.getContent());
        }
        return super.updateByPO(UrlVO.class, urlParam2PO(param, url, currentUser), currentUser);
    }


    public Url findByContent(String content) throws Exception {
        return urlReposity.findByContent(content).orElse(null);
    }


    public Url findById(Long id) {
        return urlReposity.findById(id).orElse(null);
    }

    // --------------------------
    // PRIVATE FIELDS AND METHODS
    // --------------------------

    @Autowired
    private DeviceDomain deviceDomain;

    @Autowired
    private UrlReposity urlReposity;

    @Autowired
    private Transformer transformer;


    private Url urlParam2PO(UrlParam param, Url url, User currentUser) throws Exception {
        transformer.param2PO(getClassT(), param, url, currentUser);
        if (!StringUtils.isBlank(param.getDeviceIds())) {
            List<Device> devices = deviceDomain.getAllByIds(transformer.idsStr2ListStr(param.getDeviceIds()));
            url.setDevices(transformer.list2Set(devices));
        }
        return url;
    }

    private void contentExists(String content) throws Exception {
        if (urlReposity.findByContent(content).isPresent()) {
            // Throw group already existing exception, name taken.
            throw new CommonsException(ErrorType.SYS0111, ErrorMsgHelper.getReturnMsg(ErrorType.SYS0111, getClassT().getSimpleName(), CommonsConstant.NAME));
        }
    }

}
