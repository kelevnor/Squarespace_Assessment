package com.squarespace.interview.findpoint;

import java.awt.Point;
import java.util.LinkedList;
import java.util.List;

@SuppressWarnings("WeakerAccess")
public class FindPointSolution {

  /**
   * Given a root {@link com.squarespace.interview.findpoint.Node} of a view hierarchy, provide an ordered
   * list of {@link Node#id} representing the path through the hierarchy where the given {@link java.awt.Point}
   * can be located.
   *
   * @param rootNode Top-level node of the view hierarchy.
   * @param toFind An x,y coordinate to be located.
   * @return An ordered rdered list of {@link Node#id}
   * Marios Sifalakis - Modified it to return emtpy LinkedList if point is not contained
   * and kept and returned the LinkedList to show the node path for the point if found.
   */
  @SuppressWarnings("UnusedParameters")
  public static List<String> findPathToNode(Node rootNode, Point toFind) {
    // @ToDo Implement this routine
    LinkedList<Node> nodeList = new LinkedList();
    return getAllNodesForPoint(rootNode, toFind, nodeList);
  }

  static LinkedList getAllNodesForPoint(Node n, Point p, LinkedList<Node> list) {
    list.add(n);
    for (Node temp : n.getChildren()) {
      if (temp.getChildren().size() > 0) {
        if (isContained(temp, p)) {
          return list;
        } else {
          getAllNodesForPoint(temp, p, list);
        }
      } else {
        list = new LinkedList<>();
        return list;
      }
    }
    list = new LinkedList<>();
    return list;
  }

  static boolean isContained(Node e, Point p){
    Point topLeft = new Point(e.getLeft(), e.getTop());
    Point topRight = new Point(e.getLeft()+e.getWidth(), e.getTop());
    Point bottomLeft = new Point(e.getLeft(), e.getTop()+e.getHeight());
    Point bottomRight = new Point(e.getLeft()+e.getWidth(), e.getTop()+e.getHeight());
    if(topLeft.getX()<p.getX()
            &&p.getX()<topRight.getX()
            &&topLeft.getY()<p.getY()
            &&bottomLeft.getY()>p.getY()){
      return true;
    }
    else{
      return false;
    }
  }
}
