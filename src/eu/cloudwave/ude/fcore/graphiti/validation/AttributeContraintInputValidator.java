package eu.cloudwave.ude.fcore.graphiti.validation;

import org.eclipse.jface.dialogs.IInputValidator;

public class AttributeContraintInputValidator implements IInputValidator {

	@Override
	public String isValid(String name) {
		if (name.equals("")) {
			return "Attribute constraint has to be entered.";
		}
		if (name.contains("{") || name.contains("}")) {
			return "Attribute constraint need not contain \"{\" or \"}\"";
		}
		return null;
	}

}
