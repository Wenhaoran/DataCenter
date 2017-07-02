package cn.digitalpublishing.po.base;

import java.util.List;

/**
 * @description：TreeVO
 * @author：shurui.chen
 * @date：2015/10/1 14:51
 */
public class Tree implements java.io.Serializable {

    private static final long serialVersionUID = 980682543891282923L;
    private String id;
    private String text;
    private String state = "open";// open,closed
    private boolean checked = false;
    private Object attributes;
    private List<Tree> children;
    private String iconCls;	//菜单图标，webgate 系统中，暂时没有使用
    private String pid;
    private String type;
    private String code;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public Object getAttributes() {
        return attributes;
    }

    public void setAttributes(Object attributes) {
        this.attributes = attributes;
    }

    public List<Tree> getChildren() {
        return children;
    }

    public void setChildren(List<Tree> children) {
        this.children = children;
    }

    public String getIconCls() {
        return iconCls;
    }

    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
