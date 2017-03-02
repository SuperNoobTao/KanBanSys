package cc.superliar.enums;

/**
 * Created by shentao on 2017/3/1.
 */
public enum StyleType {

    NOACTION(0), //没有动作
    VERTICAL(1), //竖直滚动
    HORIZONTAL(2);//横向滚动

    int code;

    StyleType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
