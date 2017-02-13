package com.tools.ztest.data_structure;

/**
 * Descripe: https://github.com/wxyyxc1992/Coder-Essentials/blob/master/Algorithm/java/src/main/java/wx/algorithm/search/avl/AVLTree.java
 *
 * @author yingjie.wang
 * @since 17/2/4 下午6:10
 */
public class AVLTree<K extends Comparable, V> {

    public AVLNode<K,V> root = null;

    public AVLTree() {}

    private int height(AVLNode<K,V> node) {
        return node == null ? 0 : node.height;
    }

    public V get(K key) {
        if (root == null || key == null) {
            return null;
        }
        AVLNode<K,V> node = root;
        while (node != null) {
            if (key.compareTo(node.key) == 0) {
                return node.value;
            }
            node = key.compareTo(node.key) > 0 ? node.right : node.left;
        }
        return null;
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

    public boolean remove(K key) {
        if (root == null || key == null) {
            return false;
        }

        /** 查询需删除的节点 */
        AVLNode<K,V> node = root;
        while (node != null) {
            if (key.compareTo(node.key) == 0) {
                break;
            }
            node = key.compareTo(node.key) > 0 ? node.right : node.left;
        }

        /** 若不存在,则返回false */
        if (node == null) {
            return false;
        }

        /**
         * 下面remove的实现代码不是最佳方式, 最近在网上看到了一种更好的处理方式, 大致思路如下:
         * ① 被删除节点没有儿子，即为叶节点。那么，直接将该节点删除就OK了, 然后平衡其父节点。
         * ② 被删除节点只有一个儿子。那么，直接删除该节点，并用该节点的唯一子节点顶替它的位置, 然后平衡其父节点。
         * ③ 被删除节点有两个儿子。那么，先找出它的后继节点；然后把“它的后继节点的内容”复制给“该节点的内容”；
         *    之后，删除“它的后继节点”。在这里，后继节点相当于替身，在将后继节点的内容复制给"被删除节点"之后，再将后继节点删除。
         *    这样就巧妙的将问题转换为"删除后继节点"的情况了，下面就考虑后继节点。在"被删除节点"有两个非空子节点的情况下，
         *    它的后继节点不可能是双子非空。既然"的后继节点"不可能双子都非空，就意味着"该节点的后继节点"要么没有儿子，
         *    要么只有一个儿子。若没有儿子，则按"情况① "进行处理；若只有一个儿子，则按"情况② "进行处理。
         */

        /** 需要重平衡节点(起始位置) */
        AVLNode<K,V> needRebalanceNode = null;
        /** 获取待删除节点右子树的最小节点 */
        AVLNode<K,V> rightMinNode = getRightMinNode(node.right);

        /** 待删除节点无右子树 */
        if (rightMinNode == null) {
            /** 将其左孩子及其父节点连接 */
            linkToParentOfNode(node.left, node);
            /** node节点的删除,会可能导致其父节点的失衡 */
            needRebalanceNode = node.parent;
            /** 若被删除节点为root,则更新root */
            if (node == root) {
                root = node.left;
            }
        }
        /** 待删除节点有右子树 */
        else {
            AVLNode<K,V> rightOfRightMinNode = rightMinNode.right;
            AVLNode<K,V> parentOfRightMinNode = rightMinNode.parent;
            /** 因rightMinNode需要替代node的位置, 故将其有孩子及其父节点连接 */
            linkToParentOfNode(rightOfRightMinNode, rightMinNode);
            /** 因rightMinNode需要替代node的位置, 故其parent可能会失衡, 在此先标记一下 */
            needRebalanceNode = parentOfRightMinNode;

            /** rightMinNode取代node */
            linkToNode(node.left, rightMinNode, true);
            linkToNode(node.right, rightMinNode, false);
            linkToParentOfNode(rightMinNode, node);

            /** 当rightMinNode恰好为node的右孩子时, 重平衡点需要改为rightMinNode*/
            if (needRebalanceNode == node) {
                needRebalanceNode = rightMinNode;
            }

            /** 当node为root时, 更新root */
            if (node == root) {
                root = rightMinNode;
            }
        }

        /** 重平衡树 */
        rebalance(needRebalanceNode);
        return true;
    }

    /** 获取node右子树中的最小节点 */
    private AVLNode<K,V> getRightMinNode(AVLNode<K,V> node) {
        if (node == null) {
            return null;
        }
        AVLNode<K,V> parent = null;
        AVLNode<K,V> tmpNode = node;
        while (tmpNode != null) {
            parent = tmpNode;
            tmpNode = tmpNode.left;
        }
        return parent;
    }

    /** 将target挂载至node.parent下 */
    private void linkToParentOfNode(AVLNode<K,V> target, AVLNode<K,V> node) {
        if (node != null) {
            if (target != null) {
                target.parent = node.parent;
            }
            if (node.parent != null) {
                linkToNode(target, node.parent, (node.key.compareTo(node.parent.key) < 0));
            }
        }
    }

    /** 将target挂载至node下, toLeft用来表示挂载至node的左孩子还是右孩子 */
    private void linkToNode(AVLNode<K,V> target, AVLNode<K,V> node, boolean toLeft) {
        if (target != null) {
            target.parent = node;
        }
        if (node != null) {
            if (toLeft) {
                node.left = target;
            } else {
                node.right = target;
            }
        }
    }

    private void rebalance(AVLNode<K,V> node) {
        if (node == null) {
            return;
        }

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
