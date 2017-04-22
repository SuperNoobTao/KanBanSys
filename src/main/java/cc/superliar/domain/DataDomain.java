package cc.superliar.domain;

import cc.superliar.component.Transformer;
import cc.superliar.constant.ResultConstant;
import cc.superliar.po.Device;
import cc.superliar.po.Url;
import cc.superliar.repo.DeviceReposity;
import cc.superliar.repo.NativeSQLReposity;
import cc.superliar.vo.DataVO;
import cc.superliar.vo.StyleVO;
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
    @Autowired
    private StyleDomain styleDomain;
    @Autowired
    private Transformer transformer;

    public Device findById(String id) {
        return deviceReposity.findById(id).orElse(null);
    }

    public List<Url> findAllById(String id) {
        return nativeSQLReposity.listbydevice3(id);
    }

    public DataVO result(String id) throws Exception {

        Device device = findById(id);
        if (device != null) {
            DataVO dataVO = new DataVO(ResultConstant.OK, "Successful operation");

            dataVO.setDescription(device.getDescription());
            dataVO.setName(device.getName());
            dataVO.setId(device.getId());
            dataVO.setLocation(device.getLocation());
            dataVO.setScreenNum(device.getScreenNum());
            dataVO.setScreenSize(device.getScreenSize());

            StyleVO styleVO = styleDomain.getById(Long.valueOf(device.getStyleid()), StyleVO.class);
            dataVO.setStyleVO(styleVO);

            List<Url> urlList = findAllById(id);
            List<UrlVO> urlVOList = transformer.pos2VOs(UrlVO.class, urlList);
            dataVO.setUrls(urlVOList);
           return  dataVO;
        }
        return null;
    }
}
