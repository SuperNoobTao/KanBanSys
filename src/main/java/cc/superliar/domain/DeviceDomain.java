package cc.superliar.domain;

import cc.superliar.component.CustomPasswordEncoder;
import cc.superliar.component.Transformer;
import cc.superliar.param.DeviceParam;
import cc.superliar.param.UserParam;
import cc.superliar.po.Device;
import cc.superliar.po.Role;
import cc.superliar.po.Url;
import cc.superliar.po.User;
import cc.superliar.repo.DeviceReposity;
import cc.superliar.repo.UserReposity;
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
public class DeviceDomain extends BaseDomain<Device,String> {

    // --------------------------
    // PRIVATE FIELDS AND METHODS
    // --------------------------

    @Autowired private UrlDomain urlDomain;

    @Autowired private DeviceReposity deviceReposity;

    @Autowired private CustomPasswordEncoder passwordEncoder;

    @Autowired private Transformer transformer;

    @Autowired public DeviceDomain(DeviceReposity deviceReposity) {
        this.deviceReposity = deviceReposity;
    }


    /**
     * Transform {@link UserParam} to {@link User}.
     *
     * @param param       {@link UserParam}
     * @param user        {@link User}
     * @param currentUser currentUser
     * @return {@link User}
     */
    private Device deviceParam2PO(DeviceParam param, Device device, User currentUser) throws Exception {
        transformer.param2PO(getClassT(), param, device, currentUser);
        if (!StringUtils.isBlank(param.getUrlIds())) {
            List<Url> urls = urlDomain.getAllByIds(transformer.idsStr2List(param.getUrlIds()));
            device.setUrls(transformer.list2Set(urls));
        }
        return device;
    }

}
