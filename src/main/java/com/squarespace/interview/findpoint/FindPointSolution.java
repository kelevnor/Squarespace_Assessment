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
   */
  @SuppressWarnings("UnusedParameters")
  public static List<String> findPathToNode(Node rootNode, Point toFind) {
    // @ToDo Implement this routine
    return new LinkedList<>();
  }
}
