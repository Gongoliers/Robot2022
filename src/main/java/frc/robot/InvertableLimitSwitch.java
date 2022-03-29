package frc.robot;

import com.thegongoliers.input.switches.Switch;
import edu.wpi.first.wpilibj.DigitalInput;

public class InvertableLimitSwitch implements Switch {

	private final DigitalInput input;
	private boolean isInverted = false;

	public InvertableLimitSwitch(int port) {
		input = new DigitalInput(port);
	}

	public void setInverted(boolean inverted) {
		isInverted = inverted;
	}

	@Override
	public boolean isTriggered() {
		if (isInverted) {
			return !input.get();
		} else {
			return input.get();
		}
	}

}
