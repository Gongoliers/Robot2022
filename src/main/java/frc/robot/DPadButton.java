package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.Button;

/**
 * Adapted from Josh Pordon's gist on GitHub.
 * https://gist.github.com/pordonj/970b2c189cc6ee06388b3e2f12abcb72
 */
public class DPadButton extends Button {
  XboxController contorller;
  Direction direction;

  public DPadButton(XboxController controller, Direction direction) {

    this.contorller = controller;
    this.direction = direction;
  }

  public static enum Direction {
    UP(0),
    RIGHT(90),
    DOWN(180),
    LEFT(270);
    int direction;

    private Direction(int direction) {
      this.direction = direction;
    }
  }

  public boolean get() {

    int dPadValue = contorller.getPOV();
    return (dPadValue == direction.direction)
        || (dPadValue == (direction.direction + 45) % 360)
        || (dPadValue == (direction.direction + 315) % 360);
  }
}
