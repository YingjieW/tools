package com.tools.ztest.data_structure;

/**
 * Descripe: https://github.com/wxyyxc1992/Coder-Essentials/blob/master/Algorithm/java/src/main/java/wx/algorithm/search/avl/AVLTree.java
 *
 * @author yingjie.wang
 * @since 17/2/4 下午6:10
 */
public class AVLTree<K extends Comparable, V> {

    private AVLNode<K,V> root = null;

    public AVLTree() {}

    private int height(AVLNode<K,V> node) {
        return node == null ? 0 : node.height;
    }

    public void add(K key, V value) {

        /** 参数校验 */
        if (key == null || value == null) {
            throw new IllegalArgumentException("key and value must not be null.");
        }

        /** 首次添加 */
        if (root == null) {
            root = new AVLNode<K, V>(key, value, null, null, null);
            return;
        }

        /** 查找应该挂载哪个节点下 */
        AVLNode<K,V> parent = root;
        for (AVLNode<K,V> tmp = root; tmp != null;) {
            parent = tmp;
            if (key.compareTo(tmp.key) == 0) {
                /** 直接覆盖原值 */
                tmp.value = value;
                return;
            }
            if (key.compareTo(tmp.key) < 0) {
                tmp = tmp.left;
            } else {
                tmp = tmp.right;
            }
        }

        /** 创建新节点并挂载 */
        if (key.compareTo(parent.key) < 0) {
            parent.left = new AVLNode<K, V>(key, value, null, null, parent);
        } else {
            parent.right = new AVLNode<K, V>(key, value, null, null, parent);
        }

        /** 重新平衡树 */
        rebalance(parent);
    }

    private void rebalance(AVLNode<K,V> node) {

        /** 重新计算node的balance、height值 */
        updateNode(node);

        /** 左子树高于右子树 */
        if (node.balance == 2) {
            /** 左左模式: 右旋转 */
            if (height(node.left.left) >= height(node.left.right)) {
                node = rotateRight(node);
            }
            /** 左右模式: 先左旋再右旋 */
            else {
                node = rotateLeftThenRight(node);
            }
        }

        /** 右子树高于左子树 */
        if (node.balance == -2) {
            /** 右右模式: 左旋转 */
            if (height(node.right.right) >= height(node.right.left)) {
                node = rotateLeft(node);
            }
            /** 右左模式: 先右旋再左旋 */
            else {
                node = rotateRightThenLeft(node);
            }
        }

        if (node.parent != null) {
            /** 修改父节点, 因为rebalance时, 无法修改node.parent的信息 */
            if (node.key.compareTo(node.parent.key) > 0) {
                node.parent.right = node;
            } else {
                node.parent.left = node;
            }
            /** 平衡父节点 */
            rebalance(node.parent);
        } else {
            root = node;
        }
    }

    private AVLNode<K,V> rotateRight(AVLNode<K,V> node) {
        /** 1.定位左子树, 并修改left的parent */
        AVLNode<K,V> left = node.left;
        left.parent = node.parent;
        /** 2.将左子树的右孩子挂载到node.left下 */
        node.left = left.right;
        /** 3.修改该右孩子的parent */
        if (left.right != null) {
            left.right.parent = node;
        }
        /** 4.将node挂载到left的右孩子下 */
        left.right = node;
        /** 5.更新node的parent、height、balance */
        node.parent = left;
        updateNode(node);
        /** 6.更新left的height、balance */
        updateNode(left);
        return left;
    }

    private AVLNode<K,V> rotateLeft(AVLNode<K,V> node) {
        /** 1.定位右子树, 并修改right的parent */
        AVLNode<K,V> right = node.right;
        right.parent = node.parent;
        /** 2.将右子树的左孩子挂载到node.right下*/
        node.right = right.left;
        /** 3.修改该左孩子的parent */
        if (right.left != null) {
            right.left.parent = node;
        }
        /** 4.将node挂载到right的左孩子下 */
        right.left = node;
        /** 5.更新node的parent、height、balance */
        node.parent = right;
        updateNode(node);
        /** 6.更新right的height、balance */
        updateNode(right);
        return right;
    }

    private AVLNode<K,V> rotateLeftThenRight(AVLNode<K,V> node) {
        /** 1.左转node.left */
        node.left = rotateLeft(node.left);
        /** 2.右转node */
        return rotateRight(node);
    }

    private AVLNode<K,V> rotateRightThenLeft(AVLNode<K,V> node) {
        /** 1.右转node.right */
        node.right = rotateRight(node.right);
        /** 2.左转node */
        return rotateLeft(node);
    }

    private void updateNode(AVLNode<K,V> node) {
        if (node == null) {
            return;
        }
        node.height = Math.max(height(node.left), height(node.right)) + 1;
        node.balance = height(node.left) - height(node.right);
    }

    @Override
    public String toString() {
        /** 返回root及中序遍历的数据 */
        if (root == null) {
            return null;
        }
        StringBuffer buffer = new StringBuffer("root: ").append(root.toString()).append("\ntree: \n");
        inorderTraverse(root, buffer);
        return buffer.toString();
    }

    private void inorderTraverse(AVLNode<K,V> node, StringBuffer buffer) {
        if (node == null) {
            return;
        }
        inorderTraverse(node.left, buffer);
        buffer.append(node.toString()).append("\n");
        inorderTraverse(node.right, buffer);
    }
}
