package com.boxiaoyun.common.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author admin
 */
public class TreeNode implements Serializable {
    private static final long serialVersionUID = 8772115911922451037L;
    protected int menuId;
    protected int parentId;
    protected List<TreeNode> children = new ArrayList<TreeNode>();

    public void add(TreeNode node) {
        children.add(node);
    }

    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public List<TreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<TreeNode> children) {
        this.children = children;
    }
}
