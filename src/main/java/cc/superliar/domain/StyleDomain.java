package cc.superliar.domain;

import cc.superliar.component.Transformer;
import cc.superliar.enums.ErrorType;
import cc.superliar.enums.ValidFlag;
import cc.superliar.exception.CommonsException;
import cc.superliar.param.StyleParam;
import cc.superliar.param.UserParam;
import cc.superliar.po.Style;
import cc.superliar.po.User;
import cc.superliar.repo.DeviceReposity;
import cc.superliar.repo.StyleReposity;
import cc.superliar.util.ErrorMsgHelper;
import cc.superliar.vo.StyleVO;
import cc.superliar.vo.UserVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by shentao on 2016/12/21.
 */
@Service
@Transactional(readOnly = true)
public class StyleDomain extends BaseDomain<Style,Long> {

    // ------------------------
    // PUBLIC METHODS
    // ------------------------

    /**
     * Create new {@link Style}.
     *
     * @param currentUser current user
     * @param param       {@link StyleParam}
     * @return {@link StyleVO}
     * @throws CommonsException {@link ErrorType#SYS0111} resource already existing, name taken.
     */
    @Transactional
    public StyleVO create(StyleParam param, User currentUser) throws Exception {
        styleExists(param.getSpeed(),param.getMode(),param.getWay());
        param.setName(param.getWay()+":"+param.getMode()+":"+param.getSpeed());
        return super.createByPO(StyleVO.class, styleParam2PO(param, new Style(), currentUser), currentUser);
    }


    /**
     * Update {@link User}.
     *
     * @param param {@link UserParam}
     * @return {@link UserVO}
     * @throws CommonsException {@link ErrorType#SYS0122} Cannot find any user by id param.
     */
    @Transactional public StyleVO update(StyleParam param, User currentUser) throws Exception {
        Style style = findByIdAndValidFlag(param.getId());
        if (( StringUtils.isNotBlank(param.getSpeed())||StringUtils.isNotBlank(param.getMode()) )&& (!param.getSpeed().equals(style.getSpeed())||!param.getMode().equals(style.getMode()))) {
            styleExists(param.getSpeed(),param.getMode(),param.getWay());
        }
        param.setName(param.getWay()+":"+param.getMode()+":"+param.getSpeed());
        deviceReposity.updateStyleName(param.getName(), String.valueOf(param.getId()));
        return super.updateByPO(StyleVO.class, styleParam2PO(param, style, currentUser), currentUser);
    }

    public Style findByIdAndValidFlag(Long id) {
        return styleReposity.findById(id).orElse(null);
    }





    // --------------------------
    // PRIVATE FIELDS AND METHODS
    // --------------------------

    @Autowired
    private StyleReposity styleReposity;
    @Autowired
    private DeviceReposity deviceReposity;

    @Autowired
    private Transformer transformer;


    private Style styleParam2PO(StyleParam param, Style style, User currentUser) throws Exception {
        transformer.param2PO(getClassT(), param, style, currentUser);
        return style;
    }

    private void styleExists(String time,String mode,String way) throws Exception {
        if (styleReposity.findBySpeedAndModeAndWayAndValidFlag(time,mode,way,ValidFlag.VALID).isPresent()) {
            // Throw group already existing exception, name taken.
            throw new CommonsException(ErrorType.SYS0111, ErrorMsgHelper.getReturnMsg(ErrorType.SYS0111, getClassT().getSimpleName(),"time or mode"));
        }
    }

}
