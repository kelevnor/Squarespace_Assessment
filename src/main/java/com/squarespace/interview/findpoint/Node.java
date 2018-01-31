package com.squarespace.interview.findpoint;

import static java.util.Collections.unmodifiableList;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
class Node {

  private final String id;
  private final Integer left;
  private final Integer top;
  private final Integer width;
  private final Integer height;
  private final List<Node> children;

  Node(String id, int x, int y, int width, int height) {
    this.id = id;
    this.left = x;
    this.top = y;
    this.width = width;
    this.height = height;
    this.children = new ArrayList<>();
  }

  void addChild(Node child) {
    if (child != null) {
      children.add(child);
    }
  }

  public String getId() {
    return id;
  }

  public Integer getLeft() {
    return left;
  }

  public Integer getTop() {
    return top;
  }

  public Integer getWidth() {
    return width;
  }

  public Integer getHeight() {
    return height;
  }

  public List<Node> getChildren() {
    return unmodifiableList(children);
  }
}
