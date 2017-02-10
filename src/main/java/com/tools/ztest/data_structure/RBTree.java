package com.tools.ztest.data_structure;

/**
 * Descripe:
 *      http://tech.meituan.com/redblack-tree.html
 *      http://www.cnblogs.com/skywang12345/p/3245399.html
 *
 * @author yingjie.wang
 * @since 17/2/10 上午11:04
 */
public class RBTree<T extends Comparable> {

    public RBNode<T> root;

    public int size = 0;

    public RBTree() {};

    public void add(T value) {
        if (value == null) {
            return;
        }

        /** 定位插入位置 */
        RBNode<T> previous = null;
        RBNode<T> node = root;
        while (node != null) {
            previous = node;
            if (value.compareTo(node.value) == 0) {
                return;
            }
            node = (value.compareTo(node.value) < 0 ? node.left : node.right);
        }

        /** 树为空 */
        if (previous == null) {
            /** 根节点为black */
            root = new RBNode<T>(value, false);
            size++;
            return;
        }

        /** 新节点,默认为red */
        RBNode<T> newNode = new RBNode<T>(value, true, null, null, previous);
        if (newNode.value.compareTo(previous.value) < 0) {
            previous.left = newNode;
        } else {
            previous.right = newNode;
        }

        size++;

        /** 对新插入的节点进行修正 */
        fixAdd(newNode);
    }

    private void fixAdd(RBNode<T> node) {
        RBNode<T> parent = null;
        RBNode<T> grandParent = null;
        RBNode<T> uncle = null;
        while (node != root && node.parent.isRed) {
            /** 非根节点一定有parent */
            parent = node.parent;
            /** 因parent为red,说明parent肯定不是root,进而说明grandParent肯定不为空 */
            grandParent = parent.parent;
            /** 获取node的叔叔节点, uncle可能为null */
            uncle = getUncle(node);
            /**
             * 第1种情况: 当前节点的父节点是红色，且当前节点的叔叔节点也是红色。
             * 1.1 将“父节点”设为黑色。
             * 1.2 将“祖父节点”设为“红色”。
             * 1.3 将“叔叔节点”设为黑色。
             * 1.4 将“祖父节点”设为“当前节点”(红色节点)；即，之后继续对“当前节点”进行操作。
             */
            if (uncle != null && uncle.isRed) {
                parent.isRed = false;
                grandParent.isRed = true;
                uncle.isRed = false;
                node = grandParent;
            } else {
                /** 当当前节点node的父节点parent是其爷爷节点grandParent的左孩子时 */
                if (parent.value.compareTo(grandParent.value) < 0) {
                    /**
                     * 第2种情况: 当前节点的父节点是红色，叔叔节点是黑色，且当前节点是其父节点的右孩子
                     * 2.1 将“父节点”作为“新的当前节点”。
                     * 2.2 以“新的当前节点”为支点进行左旋。
                     */
                    if (node.value.compareTo(parent.value) > 0) {
                        node = parent;
                        rotateLeft(node);
                    }
                    /**
                     * 第3种情况: 当前节点的父节点是红色，叔叔节点是黑色，且当前节点是其父节点的左孩子
                     * 3.1 将“父节点”设为“黑色”。
                     * 3.2 将“祖父节点”设为“红色”。
                     * 3.3 以“祖父节点”为支点进行右旋。
                     */
                    else {
                        parent.isRed = false;
                        grandParent.isRed = true;
                        rotateRight(grandParent);
                        break;
                    }
                }
                /** 当当前节点node的父节点parent是其爷爷节点grandParent的右孩子时 */
                else {
                    /**
                     * 第4种情况: 当前节点的父节点是红色，叔叔节点是黑色，且当前节点是其父节点的右孩子
                     * 4.1 将“父节点”设为“黑色”。
                     * 4.2 将“祖父节点”设为“红色”。
                     * 4.3 以“祖父节点”为支点进行左旋。
                     */
                    if (node.value.compareTo(parent.value) > 0) {
                        parent.isRed = false;
                        grandParent.isRed = true;
                        rotateLeft(grandParent);
                        break;
                    }
                    /**
                     * 第5种情况: 当前节点的父节点是红色，叔叔节点是黑色，且当前节点是其父节点的左孩子
                     * 5.1 将“父节点”作为“新的当前节点”。
                     * 5.2 以“新的当前节点”为支点进行右旋。
                     */
                    else {
                        node = parent;
                        rotateRight(node);
                    }
                }
            }
        }

        /** 根节点置为black */
        root.isRed = false;
    }

    private RBNode<T> getUncle(RBNode<T> node) {
        RBNode<T> parent = node.parent;
        RBNode<T> grandParent = parent.parent;
        if (grandParent == null) {
            return null;
        }
        return parent.value.compareTo(grandParent.value) < 0 ? grandParent.right : grandParent.left;
    }

    /** 旋转逻辑基本同AVLTree.rotateRight */
    private RBNode<T> rotateRight(RBNode<T> node) {
        RBNode<T> left = node.left;
        node.left = left.right;
        if (left.right != null) {
            left.right.parent = node;
        }
        left.right = node;
        left.parent = node.parent;
        node.parent = left;
        if (left.parent != null) {
            if (left.value.compareTo(left.parent.value) < 0) {
                left.parent.left = left;
            } else {
                left.parent.right = left;
            }
        }
        if (left.parent == null) {
            root = left;
        }
        return left;
    }

    /** 旋转逻辑基本同AVLTree.rotateLeft */
    private RBNode<T> rotateLeft(RBNode<T> node) {
        RBNode<T> right = node.right;
        node.right = right.left;
        if (right.left != null) {
            right.left.parent = node;
        }
        right.left = node;
        right.parent = node.parent;
        node.parent = right;
        if (right.parent != null) {
            if (right.value.compareTo(right.parent.value) < 0) {
                right.parent.left = right;
            } else {
                right.parent.right = right;
            }
        }
        if (right.parent == null) {
            root = right;
        }
        return right;
    }

    @Override
    public String toString() {
        if (root == null) {
            return null;
        }
        StringBuffer buffer = new StringBuffer("size: ").append(size)
                .append("\nroot: ").append(root.toString()).append("\ntree: \n");
        inorderTraverse(root, buffer);
        return buffer.toString();
    }

    private void inorderTraverse(RBNode<T> node, StringBuffer buffer) {
        if (node == null) {
            return;
        }
        inorderTraverse(node.left, buffer);
        buffer.append(node.toString()).append("\n");
        inorderTraverse(node.right, buffer);
    }
}
