package eu.cloudwave.ude.fcore.graphiti.validation;

import org.eclipse.jface.dialogs.IInputValidator;

public class NameInputValidator implements IInputValidator {

	@Override
	public String isValid(String name) {
		if (name.equals("")) {
			return "Name has to be entered.";
		}
//		if (!(name.matches("[a-zA-Z]\\w*"))) {
//			return "Name has to start with a letter.";
//		}
		return null;
	}

}
