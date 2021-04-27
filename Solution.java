package peakHeights;

import java.util.LinkedList;
import java.util.Arrays;

public class Solution {

  // Input data: initially all land area is designated with '1' and all water area with '0'.
  public int[][] matrix;
  boolean[][] visited;
  public int[][] moves = {
    {-1, 0}, // up
    {1, 0}, // down
    {0, -1}, // left
    {0, 1} // right
  };

  /*
  By the problem design on binarysearch.com, we have to work
  around the given method 'public int[][] solve(int[][] matrix)' so that the code
  can be run on the website. Even though the name 'solve' does not make
  a lot of sense, it is left as it is, so that the code can be run directly
  on the website, without any modifications.

  @return The modified matrix, with incrased height of the land points,
          as defined by the problem statement.
  */
  public int[][] solve(int[][] matrix) {
    this.matrix = matrix;
    multiSource_bfs_increaseHeightOfLandPoints();
    return this.matrix;
  }

  /*
  Multi-Source Breadth First Search: by a series of concentric waves,
  starting from the land on coast and moving inland, we increase the height
  of the land points, as per the problem statement, i.e. the absolute
  difference in height between a point and any of its immediate neighbours
  (left, right, up, down) can not be greater than '1'.
  */
  public void multiSource_bfs_increaseHeightOfLandPoints() {

    visited = new boolean[matrix.length][matrix[0].length];
    LinkedList<Point> queue = findlandPointsOnCoast();

    while (!queue.isEmpty()) {

      Point current = queue.removeFirst();
      int row = current.row;
      int column = current.column;

      for (int i = 0; i < moves.length; i++) {
        int new_r = row + moves[i][0];
        int new_c = column + moves[i][1];

        if (isInMatrix(new_r, new_c) && !visited[new_r][new_c] && matrix[new_r][new_c] != 0) {
          queue.add(new Point(new_r, new_c));
          matrix[new_r][new_c] = matrix[row][column] + 1;
          visited[new_r][new_c] = true;
        }
      }
    }
  }

  /*
  The increase in land height has to be gradual, with
  absolute difference in height of a point and any of its neighbours
  (left, right, up, down) no greater than '1'.

  Therefore, the increase has to start from the land points on coast,
  since water height is '0' and the initial land height is '1'.

  @return All coastal land points.
  */
  public LinkedList<Point> findlandPointsOnCoast() {

    LinkedList<Point> landPointsOnCoast = new LinkedList<Point>();

    for (int r = 0; r < matrix.length; r++) {
      for (int c = 0; c < matrix[0].length; c++) {

        if (matrix[r][c] == 1 && isLandPointOnCoast(r, c)) {
          Point point = new Point(r, c);
          landPointsOnCoast.add(point);
          visited[r][c] = true;
        }
      }
    }

    return landPointsOnCoast;
  }

  public boolean isLandPointOnCoast(int row, int column) {

    for (int i = 0; i < moves.length; i++) {
      int new_r = row + moves[i][0];
      int new_c = column + moves[i][1];

      if (isInMatrix(new_r, new_c) && matrix[new_r][new_c] == 0) {
        return true;
      }
    }
    return false;
  }

  public boolean isInMatrix(int row, int column) {
    if (row < 0 || column < 0 || row > matrix.length - 1 || column > matrix[0].length - 1) {
      return false;
    }
    return true;
  }
}

class Point {
  int row;
  int column;

  public Point(int row, int column) {
    this.row = row;
    this.column = column;
  }
}
