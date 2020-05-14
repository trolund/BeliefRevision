package parsing.types;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Node<T> implements Cloneable {

    private T data = null;
    private List<Node<T>> children = new ArrayList<>();
    private Node<T> parent = null;

    public Node() {
    }

    public Node(T data, List<Node<T>> children, Node<T> parent) {
        this.data = data;
        this.children = children;
        this.parent = parent;
    }



    @Override
    public String toString() {
        return "Node{" +
                "data=" + data +
                ", children=" + children +
                '}';
    }




    public Node(T data) {
        this.data = data;
    }

    public Node<T> addChild(Node<T> child) {
        child.setParent(this);
        this.children.add(child);
        return child;
    }

    public void setChildren(List<Node<T>> children) {
        this.children = children;
    }

    public void addChildren(List<Node<T>> children) {
        children.forEach(each -> each.setParent(this));
        this.children.addAll(children);
    }

    public List<Node<T>> getChildren() {
        return children;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    private void setParent(Node<T> parent) {
        this.parent = parent;
    }

    public Node<T> getParent() {
        return parent;
    }

    @Override
    public Node clone() {
        try {
            if(this.data instanceof Literal){
                Literal lit = ((Literal) this.getData()).clone();
                return new Node(lit, this.getChildren(), this.parent);
            }
        } catch (Exception e) {
            return new Node(this.getData(), this.getChildren(), this.parent);
        }
        return new Node(this.getData(), this.getChildren(), this.parent);
    }

}