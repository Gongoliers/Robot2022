package frc.robot.subsystems;

import com.thegongoliers.input.vision.LimelightCamera;
import com.thegongoliers.input.vision.LimelightCamera.LEDMode;
import com.thegongoliers.input.vision.TargetingCamera;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class VisionSubsystem extends SubsystemBase {
  private LimelightCamera targetingCamera;

  public VisionSubsystem() {
    targetingCamera = new LimelightCamera();
    setDriverMode(true);
  }

  public TargetingCamera getTargetingCamera() {
    return targetingCamera;
  }

  @Override
  public void periodic() {
    // Put code here to be run every loop
    var angle = getAngleToTarget();
    var targetFound = isTargetFound();
    SmartDashboard.putNumber("Angle to Target", angle);
    SmartDashboard.putBoolean("Aligned", targetFound && Math.abs(angle) < 1.5);
    SmartDashboard.putBoolean("Target Found?", targetFound);
  }

  public double getAngleToTarget() {
    return targetingCamera.getHorizontalOffset();
  }

  public double getTargetArea() {
    return targetingCamera.getTargetArea();
  }

  public boolean isTargetFound() {
    return targetingCamera.hasTarget();
  }

  public void setDriverMode(boolean enabled) {
    if (enabled) {
      targetingCamera.setLEDMode(LEDMode.Off);
    } else {
      targetingCamera.setLEDMode(LEDMode.On);
    }
  }
}
