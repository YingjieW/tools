package com.tools.ztest.data_structure;

/**
 * Descripe:
 *      http://tech.meituan.com/redblack-tree.html
 *      http://www.cnblogs.com/sungoshawk/p/3755807.html
 *      http://www.cnblogs.com/skywang12345/p/3245399.html
 *
 * @author yingjie.wang
 * @since 17/2/10 上午11:04
 */
public class RBTree<T extends Comparable> {

    public RBNode<T> root;

    private final boolean BLACK = false;

    private final boolean RED = true;

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
        fixAfterAdd(newNode);
    }

    private void fixAfterAdd(RBNode<T> node) {
        RBNode<T> parent = null;
        RBNode<T> grandParent = null;
        RBNode<T> uncle = null;
        while (node != root && (node.parent.color == RED)) {
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
            if (uncle != null && (uncle.color == RED)) {
                parent.color = BLACK;
                grandParent.color = RED;
                uncle.color = BLACK;
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
                        parent.color = BLACK;
                        grandParent.color = RED;
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
                        parent.color = BLACK;
                        grandParent.color = RED;
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
        root.color = BLACK;
    }

    public boolean remove(T value) {
        if (value == null || root == null) {
            return false;
        }

        RBNode<T> node = findNodeByValue(value);
        if (node == null) {
            return true;
        }

        boolean colorOfRemovedNode = colorOf(node);
        RBNode<T> needFixNode = null;
        /** 若node.left为null, 删掉node, 用其右孩子顶替node的位置 */
        if (node.left == null) {
            needFixNode = node.right;
            transplant(needFixNode, node);
        }
        /** 若node.right为null, 删掉node, 用其左孩子顶替node的位置 */
        else if (node.right == null) {
            needFixNode = node.left;
            transplant(needFixNode, node);
        }
        /** 若node的左右孩子均不为空, 查询其rightMinNode,
         * 用rightMinNode的值取代node的值, 然后删掉rightMinNode */
        else {
            /** 查抄node右子树最小节点rightMinNode(因其最小,故其一定无左子树) */
            RBNode<T> rightMinNode = getRightMinNode(node);
            /** 用rightMinNode.value替代node.value, node的颜色不变 */
            node.value = rightMinNode.value;
            /** 删掉rightMinNode, 用其右孩子替代rightMinNode的位置 */
            transplant(rightMinNode.right, rightMinNode);
            needFixNode = rightMinNode.right;
            colorOfRemovedNode = rightMinNode.color;
        }

        /** 当被删除点是黑色时才需要修复 */
        if (colorOfRemovedNode == BLACK) {
            fixAfterRemove(needFixNode);
        }
        return true;
    }

    private void fixAfterRemove(RBNode<T> node) {
        while (node != root && colorOf(node) == BLACK) {
            /** 当node为其父节点的左子树时 */
            if (node == leftOf(parentOf(node))) {
            }
            /** symmetric: 当node为其父节点的右子树时*/
            else {
                System.out.println("symmetric operation.");
            }
        }

        /** 根节点置为BLACK */
        root.color = BLACK;
    }

    private boolean colorOf(RBNode<T> node) {
        return (node == null) ? BLACK : node.color;
    }

    private RBNode<T> leftOf(RBNode<T> node) {
        return (node == null) ? null : node.left;
    }

    private RBNode<T> rightOf(RBNode<T> node) {
        return (node == null) ? null : node.right;
    }

    private RBNode<T> parentOf(RBNode<T> node) {
        return (node == null) ? null : node.parent;
    }

    /** 将target移植到node.parent下 */
    private void transplant(RBNode<T> target, RBNode<T> node) {
        if (node == null) {
            throw new RuntimeException("node must not be null.");
        }
        if (node.parent == null) {
            root = target;
        } else if (node.value.compareTo(node.parent.value) < 0) {
            node.parent.left = target;
        } else {
            node.parent.right = target;
        }
        if (target != null) {
            target.parent = node.parent;
        }
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

    private RBNode<T> findNodeByValue(T value) {
        if (value == null || root == null) {
            return null;
        }
        RBNode<T> node = root;
        while (node != null) {
            if (value.compareTo(node.value) == 0) {
                return node;
            } else if (value.compareTo(node.value) < 0) {
                node = node.left;
            } else {
                node = node.right;
            }
        }
        return null;
    }

    private RBNode<T> getRightMinNode(RBNode<T> node) {
        if (node == null) {
            return null;
        }
        /** 搜索的起点: node的右孩子 */
        RBNode<T> right = node.right;
        while (right.left != null) {
            /** 遍历左子树 */
            right = right.left;
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
