package at.fhj.swengb.apps.maze

import at.fhj.swengb.apps.maze.jfx.{Door, Wall}

import scala.util.Random

object Cell {

  def apply(pos: Pos, topLeft: Coord, region: Rect): Cell = {
    // set doors / no doors randomly for the moment
    val maybeUp = if (Random.nextBoolean()) Option(pos.up) else None
    val maybeRight = if (Random.nextBoolean()) Option(pos.right) else None
    val maybeDown = if (Random.nextBoolean()) Option(pos.down) else None
    val maybeLeft = if (Random.nextBoolean()) Option(pos.left) else None

    Cell(pos, topLeft, region, up = maybeUp, right = maybeRight, down = maybeDown, left = maybeLeft)
  }

}

case class Cell(pos: Pos
                , topLeft: Coord
                , region: Rect
                , up: Option[Pos]
                , right: Option[Pos]
                , down: Option[Pos]
                , left: Option[Pos]) {

  val directions: Seq[Option[Pos]] = Seq(up, right, down, left)

  val x: Double = topLeft.x
  val y: Double = topLeft.y

  val width: Double = region.width
  val height: Double = region.height

  val topRight: Coord = Coord(topLeft.x + width, topLeft.y)
  val bottomRight: Coord = Coord(topLeft.x + width, topLeft.y + height)
  val bottomLeft: Coord = Coord(topLeft.x, topLeft.y + height)
  val center: Coord = Coord(topLeft.x + width / 2, topLeft.y + height / 2)

  val topCenter: Coord = Coord(topLeft.x + width / 2, topLeft.y)
  val rightCenter: Coord = Coord(topRight.x, topRight.y + height / 2)
  val bottomCenter: Coord = Coord(topLeft.x + width / 2, topLeft.y + height)
  val leftCenter: Coord = Coord(topLeft.x, topLeft.y + height / 2)

  val doorRect: Rect = Rect((topRight.x - topLeft.x) / 4, (bottomLeft.y - topLeft.y) / 4)

  val upDoor = Door(up, topCenter, doorRect)
  val rightDoor = Door(right, rightCenter, doorRect)
  val downDoor = Door(down, bottomCenter, doorRect)
  val leftDoor = Door(left, leftCenter, doorRect)

  val doors: Seq[Door] = Seq(upDoor, rightDoor, downDoor, leftDoor)

  val wallTop = Wall(topLeft, topRight)
  val wallRight = Wall(topRight, bottomRight)
  val wallDown = Wall(bottomRight, bottomLeft)
  val wallLeft = Wall(bottomLeft, topLeft)

  val walls: Seq[Wall] = Seq(wallTop, wallRight, wallDown, wallLeft)
}