package at.fhj.swengb.apps.maze

import at.fhj.swengb.apps.maze.jfx.{Door, Wall}

import scala.util.Random


/**
  * A Mazegenerator creates a datastructure representing a random maze.
  */
object MazeGenerator {

  /**
    * Given a list, this function picks a random element.
    *
    * @param seq
    * @tparam A
    * @return
    */
  def pickRandom[A](seq: Seq[A]): A = {
    require(seq.nonEmpty)
    if (seq.length == 1) seq.head else {
      seq(Random.nextInt(seq.length))
    }
  }

  /**
    * Generates a random maze with a given size and entry and exit cells.
    *
    * The idea is to start at the entry cell, walk in any direction
    *
    * @param nrX      number of cells in x direction
    * @param nrY      number of cells in y direction
    * @param start    start / entry cell of the maze
    * @param exit     exit cell of the maze
    * @param cellRect size / region of a cell
    * @return a random maze
    */
  def gen(nrX: Int, nrY: Int, start: Pos, exit: Pos, cellRect: Rect): Maze = {
    require(nrX > 0)
    require(nrY > 0)
    require(0 <= start.x && start.x <= nrX)
    require(0 <= start.y && start.y <= nrY)
    require(0 <= exit.x && exit.x < nrX)
    require(0 <= exit.y && exit.y < nrY)
    require(start != exit)

    val m = Maze(nrX, nrY, cellRect)
    val startCell = m.getCell(start)
    val exitCell = m.getCell(exit)

    // we require exit and entry to be part of the outer cells
    require(m.boundaryCells.contains(startCell))
    require(m.boundaryCells.contains(exitCell))

    closeOuterDoors(m)
  }

  private def closeOuterDoors(m: Maze): Maze = m.copy(grid = for (c <- m.grid) yield clip(m, c))

  private def clip(m: Maze, c: Cell): Cell = {
    val up: Option[Pos] = if (m.topBoundary.contains(c)) None else c.up
    val right: Option[Pos] = if (m.rightBoundary.contains(c)) None else c.right
    val down: Option[Pos] = if (m.downBoundary.contains(c)) None else c.down
    val left: Option[Pos] = if (m.leftBoundary.contains(c)) None else c.left
    c.copy(up = up, down = down, right = right, left = left)
  }

  private def addEntryAndExit(m: Maze, start: Cell, exit: Cell) = {
    for (c <- m.grid) yield {
      if (c == start || c == exit) clip(m, c) else c
    }
  }

  def walls(maze: Maze): Set[Wall] = maze.grid.flatMap(_.walls).toSet

  def doors(maze: Maze): Set[Door] = maze.grid.flatMap(_.doors).toSet

}
