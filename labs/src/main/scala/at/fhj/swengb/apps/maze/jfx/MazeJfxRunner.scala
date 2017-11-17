package at.fhj.swengb.apps.maze.jfx

import javafx.application.Application

/**
  * This is the entry point for the maze runner JavaFX application.
  *
  * A JavaFX application needs a special launcher [[Application.launch]] in order to start.
  */
object MazeJfxRunner {

  def main(args: Array[String]): Unit = {
    Application.launch(classOf[MazeJfxApp], args: _*)
  }

}
