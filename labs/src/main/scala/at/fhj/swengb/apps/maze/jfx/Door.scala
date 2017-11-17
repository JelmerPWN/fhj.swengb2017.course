package at.fhj.swengb.apps.maze.jfx

import javafx.scene.paint.{Color, Paint}
import javafx.scene.shape.Rectangle

import at.fhj.swengb.apps.maze.{Coord, Pos, Rect}

object Door {

  def apply(maybeTo: Option[Pos], c: Coord, rect: Rect): Door = {
    if (maybeTo.isDefined) {
      OpenDoor(c, rect)
    } else {
      ClosedDoor(c, rect)
    }
  }

}

sealed trait Door extends Drawable {

  def coord: Coord

  def rect: Rect

  def fill: Paint

  def draw: Rectangle = {
    val leftX = coord.x - rect.width / 2
    val upY = coord.y - rect.height / 2
    val r = new Rectangle(leftX, upY, rect.width, rect.height)
    r.setFill(fill)
    r
  }
}

case class ClosedDoor(coord: Coord, rect: Rect, fill: Paint = Color.BLACK) extends Door

case class OpenDoor(coord: Coord, rect: Rect, fill: Paint = Color.WHITE) extends Door
