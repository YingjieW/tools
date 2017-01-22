package com.tools.ztest.data_structure;

import java.util.Stack;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 17/1/20 下午10:25
 */
public class BinarySearchTree<K extends Comparable, V> {

    private Node<K,V> root = null;

    public BinarySearchTree() {}

    public V put(K key, V value) {
        if (root == null) {
            root = new Node<K, V>(key, value);
        } else {
            Node<K,V> newNode = new Node<K, V>(key, value);
            Node<K,V> parent = root;
            Node<K,V> node = root;
            while (node != null) {
                if (key.compareTo(node.getKey()) == 0) {
                    node.setValue(value);
                    break;
                } else if (key.compareTo(node.getKey()) < 0) {
                    if (node.getLeft() == null) {
                        node.setLeft(newNode);
                    } else {
                        node = node.getLeft();
                    }
                } else {
                    if (node.getRight() == null) {
                        node.setRight(newNode);
                    } else {
                        node = node.getRight();
                    }
                }
            }
        }
        return value;
    }

    public V get(K key) {
        if (root == null) {
            return null;
        }
        Node<K,V> node = findNodeByKey(key);
        return node == null ? null : node.getValue();
    }

    private Node<K,V> findNodeByKey(K key) {
        if (root == null) {
            return null;
        }
        Node<K,V> node = root;
        while (node != null) {
            if (key.compareTo(node.getKey()) == 0) {
                return node;
            } else if (key.compareTo(node.getKey()) < 0) {
                node = node.getLeft();
            } else {
                node = node.getRight();
            }
        }
        return null;
    }

    public V remove(K key) {
        if (root == null) {
            return null;
        }
        Node<K,V> previous = null;
        Node<K,V> node = root;
        while (node != null) {
            if (key.compareTo(node.getKey()) == 0) {
                break;
            } else if (key.compareTo(node.getKey()) < 0) {
                previous = node;
                node = node.getLeft();
            } else {
                previous = node;
                node = node.getRight();
            }
        }
        if (node == null) {
            throw new RuntimeException("Node[key=" + key + "] is not found.");
        }
        // 删除root,若左子树不为空,则将左子树变为root;
        Node<K,V> left = node.getLeft();
        Node<K,V> right = node.getRight();
        if (previous == null) {
            if (left == null) {
                root = right;
                return node.getValue();
            } else {
                root = left;
                Node<K,V> leftestChildOfRight = findLeftestChild(right);
                Node<K,V> rightChildOfLeft = left.getRight();
                left.setRight(right);
                leftestChildOfRight.setLeft(rightChildOfLeft);
                return node.getValue();
            }
        }
        // 若左子树不为空,则将左子树变为root;
        else {
            if (left == null) {
                previous.setRight(right);
                return node.getValue();
            } else {
                Node<K,V> leftestChildOfRight = findLeftestChild(right);
                Node<K,V> rightChildOfLeft = left.getRight();
                if (node.getKey().compareTo(previous.getKey()) < 0) {
                    previous.setLeft(left);
                } else {
                    previous.setRight(left);
                }
                left.setRight(right);
                leftestChildOfRight.setLeft(rightChildOfLeft);
                return node.getValue();
            }
        }
    }

    private Node<K,V> findLeftestChild(Node<K,V> node) {
        if (node == null) {
            return null;
        }
        while (node != null && node.getLeft() != null) {
            node = node.getLeft();
        }
        return node;
    }


    public void inorderRecursively() {
        if (root == null) {
            System.out.println("Tree is empty.");
        }
        inorder(root);
        System.out.println();
    }

    private void inorder(Node<K,V> node) {
        if (node == null) {
            return;
        }
        inorder(node.getLeft());
        System.out.print(node.getValue() + " ");
        inorder(node.getRight());
    }

    public void inorderNonrecursively() {
        if (root == null) {
            System.out.println("Tree is empty.");
        }
        Stack<Node<K,V>> stack = new Stack<Node<K, V>>();
        Node<K,V> node = root;
        while (node != null || !stack.isEmpty()) {
            while (node != null) {
                stack.push(node);
                node = node.getLeft();
            }
            if (!stack.isEmpty()) {
                Node<K,V> temp = stack.pop();
                System.out.print(temp.getValue() + " ");
                node = temp.getRight();
            }
        }
        System.out.println();
    }

    public void preorderRecursively() {
        if (root == null) {
            System.out.println("Tree is empty.");
        }
        preorder(root);
        System.out.println();
    }

    private void preorder(Node<K,V> node) {
        if (node == null) {
            return;
        }
        System.out.print(node.getValue() + " ");
        preorder(node.getLeft());
        preorder(node.getRight());
    }

    public void preorderNonrecursively() {
        if (root == null) {
            System.out.println("Tree is empty.");
        }
        Stack<Node<K,V>> stack = new Stack<Node<K, V>>();
        Node<K,V> node = root;
        while (node != null || !stack.isEmpty()) {
            while (node != null) {
                System.out.print(node.getValue() + " ");
                stack.push(node.getRight());
                node = node.getLeft();
            }
            if (!stack.isEmpty()) {
                node = stack.pop();
            }
        }
        System.out.println();
    }

    public void postorderRecursively() {
        if (root == null) {
            System.out.println("Tree is empty.");
        }
        postorder(root);
        System.out.println();
    }

    private void postorder(Node<K,V> node) {
        if (node == null) {
            return;
        }
        postorder(node.getLeft());
        postorder(node.getRight());
        System.out.print(node.getValue() + " ");
    }

    public void postorderNonrecursively() {
        if (root == null) {
            System.out.println("Tree is empty.");
        }
        Stack<Node<K,V>> stack = new Stack<Node<K, V>>();
        Node node = root;
        Node previous = null;
        while (node != null || !stack.isEmpty()) {
            while (node != null) {
                stack.push(node);
                node = node.getLeft();
            }
            if (!stack.isEmpty()) {
                Node right = stack.peek().getRight();
                if (right == null || right == previous) {
                    previous= stack.pop();
                    node = null;
                    System.out.print(previous.getValue() + " ");
                } else {
                    node = right;
                }
            }
        }
        System.out.println();
    }
}
