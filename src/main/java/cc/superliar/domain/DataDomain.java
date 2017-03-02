package cc.superliar.domain;

import cc.superliar.po.Device;
import cc.superliar.po.Url;
import cc.superliar.repo.DeviceReposity;
import cc.superliar.repo.NativeSQLReposity;
import cc.superliar.vo.UrlVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by shentao on 2017/3/1.
 */
@Service
@Transactional(readOnly = true)
public class DataDomain {

    @Autowired
    private DeviceReposity deviceReposity;
    @Autowired
    private NativeSQLReposity nativeSQLReposity;

    public Device findById(String id) {
        return deviceReposity.findById(id).orElse(null);
    }

    public List<Url> findAllById(String id) {
        return nativeSQLReposity.listbydevice3(id);
    }

}
