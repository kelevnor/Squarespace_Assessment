package com.squarespace.interview.findpoint;

import static com.squarespace.interview.Common.loadFromClasspath;
import static java.lang.String.join;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.emptyMap;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;

import org.junit.Test;

public class FindPointSolutionTest {

  /**
   * Initializes an instance of Node from the given {@link JsonObject}.  Recurses on children to deeply initialize
   * the object.
   *
   * @param jsonObject A reference to JSON containing node data.
   * @return A deeply initialized Node instance.
   */
  private static Node initializeNode(JsonObject jsonObject) {
    String id = jsonObject.getString("id");
    int left = jsonObject.getInt("left");
    int top = jsonObject.getInt("top");
    int width = jsonObject.getInt("width");
    int height = jsonObject.getInt("height");
    JsonArray children = jsonObject.getJsonArray("children");

    Node node = new Node(id, left, top, width, height);

    for (JsonObject child : children.getValuesAs(JsonObject.class)) {
      node.addChild(initializeNode(child));
    }

    return node;
  }

  /**
   * Given a {@link java.io.InputStream} providing a view hierarchy definition in JSON and a set of points, provide
   * the path through the hierarchy for each given point.
   *
   * @param input JSON file defining a nested view hierarchy.
   * @param pointsToFind A unique collection of points to find within the view hierarchy.
   * @return An ordered list of String node IDs that define the path to the view containing each given point.
   */
  private static Map<Point, List<String>> processInput(InputStream input, Set<Point> pointsToFind) {
    Node rootNode;
    try (JsonReader reader = Json.createReader(input)) {
      JsonObject root = reader.readObject();
      rootNode = initializeNode(root);
    }

    if (rootNode == null) {
      return emptyMap();
    }

    Map<Point, List<String>> results = new HashMap<>();
    for (Point toFind : pointsToFind) {
      List<String> result = FindPointSolution.findPathToNode(rootNode, toFind);
      results.put(toFind, result);
    }

    return results;
  }

  /**
   * Given a file name on the classpath of expected results parse it into a data structure to use for validating
   * results. The file contains a given (x,y) point and the path to that point in a view hierarchy.
   *
   * @param expectedData Tab delimited fields, new line delimited records.
   * @return For every point, an expected path through an hierarchy.
   * @throws IOException If error locating or reading file.
   */
  @SuppressWarnings("JavaDoc")
  private static Map<Point, List<String>> loadExpectedResults(String expectedData) throws IOException {
    InputStream stream = loadFromClasspath(expectedData);
    Map<Point, List<String>> expectedResults = new HashMap<>();
    try (BufferedReader br = new BufferedReader(new InputStreamReader(stream))) {
      // each row:  toFindX\ttoFindY\texpectedPath\n
      String s;
      while ((s = br.readLine()) != null) {
        if (!s.isEmpty()) {
          String[] ss = s.trim().split("\t");
          int x = Integer.parseInt(ss[0]);
          int y = Integer.parseInt(ss[1]);
          Point toFind = new Point(x, y);
          if (ss[2].equals("[]")) {
            // handle empty path case
            expectedResults.put(toFind, emptyList());
          } else {
            String[] path = ss[2].split(",");
            expectedResults.put(toFind, asList(path));
          }
        }
      }
      return expectedResults;
    }
  }

  @Test
  public void testRun000() throws IOException {
    String inputData = "findpoint/input000.txt";
    String expectedData = "findpoint/output000.txt";
    assertTestRun(inputData, expectedData);
  }

  @Test
  public void testRun001() throws IOException {
    String inputData = "findpoint/input001.txt";
    String expectedData = "findpoint/output001.txt";
    assertTestRun(inputData, expectedData);
  }

  @Test
  public void testRun002() throws IOException {
    String inputData = "findpoint/input002.txt";
    String expectedData = "findpoint/output002.txt";
    assertTestRun(inputData, expectedData);
  }

  @Test
  public void testRun003() throws IOException {
    String inputData = "findpoint/input003.txt";
    String expectedData = "findpoint/output003.txt";
    assertTestRun(inputData, expectedData);
  }

  private void assertTestRun(String inputData, String expectedData) throws IOException {
    try (InputStream input = loadFromClasspath(inputData)) {
      Map<Point, List<String>> expectedResults = loadExpectedResults(expectedData);
      Map<Point, List<String>> actualResults = processInput(input, expectedResults.keySet());

      assertNotNull("Error in output: Expected non-null collection of results.", actualResults);
      assertEquals("Error in output: got different number of results than expected.",
          expectedResults.keySet().size(), actualResults.keySet().size());

      for (Map.Entry<Point, List<String>> entry : expectedResults.entrySet()) {
        Point toFind = entry.getKey();
        List<String> expectedResult = entry.getValue();
        List<String> actualResult = actualResults.get(toFind);

        assertTrue("Error in output: key not found in results: " + toFind, actualResults.containsKey(toFind));
        assertEquals("Error in output. Unexpected path for point: " + toFind,
            join("/", expectedResult), join("/", actualResult));

        // Verbose logging:
        //System.out.printf("Results for point [%d,%d]: expected: [%s] actual: [%s]\n",
        //   toFind.x, toFind.y, join("/", expectedResult), join("/", actualResult));

      }
    }
  }
}
