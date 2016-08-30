package eu.cloudwave.ude.fcore.graphiti;

import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

public class IDUtil {

	public static final String SHAPE_KEY_FEATURE = "shape-id-feature";
	public static final String SHAPE_KEY_FEATURE_GROUP = "shape-id-feature-group";
	public static final String SHAPE_KEY_ATTRIBUTE = "shape-id-attribute";
	public static final String SHAPE_KEY_ATTRIBUTE_CONSTRAINT = "shape-id-attribute-constraint";
	public static final String SHAPE_KEY_SOFTGOAL = "shape-id-softgoal";
	
	public static final String SHAPE_VALUE_FEATURE = "feature";
	public static final String SHAPE_VALUE_FEATURE_GROUP = "feature-group";
	public static final String SHAPE_VALUE_ATTRIBUTE = "attribute";
	public static final String SHAPE_VALUE_ATTRIBUTE_CONSTRAINT = "attribute-constraint";
	public static final String SHAPE_VALUE_SOFTGOAL = "softgoal";
 
    public static final void setFeatureShape(PictogramElement pe) {
        Graphiti.getPeService().setPropertyValue(pe, SHAPE_KEY_FEATURE,
            SHAPE_VALUE_FEATURE);
    }
 
    public static boolean isFeatureShape(PictogramElement pe) {
        return SHAPE_VALUE_FEATURE.equals(Graphiti.getPeService()
           .getPropertyValue(pe, SHAPE_KEY_FEATURE));
    }
    
    public static final void setFeatureGroupShape(PictogramElement pe) {
        Graphiti.getPeService().setPropertyValue(pe, SHAPE_KEY_FEATURE_GROUP,
            SHAPE_VALUE_FEATURE_GROUP);
    }
 
    public static boolean isFeatureGroupShape(PictogramElement pe) {
        return SHAPE_VALUE_FEATURE_GROUP.equals(Graphiti.getPeService()
           .getPropertyValue(pe, SHAPE_KEY_FEATURE_GROUP));
    }
    
    public static final void setAttributeShape(PictogramElement pe) {
        Graphiti.getPeService().setPropertyValue(pe, SHAPE_KEY_ATTRIBUTE,
            SHAPE_VALUE_ATTRIBUTE);
    }
 
    public static boolean isAttributeShape(PictogramElement pe) {
        return SHAPE_VALUE_ATTRIBUTE.equals(Graphiti.getPeService()
           .getPropertyValue(pe, SHAPE_KEY_ATTRIBUTE));
    }
    
    public static final void setAttributeConstraintShape(PictogramElement pe) {
        Graphiti.getPeService().setPropertyValue(pe, SHAPE_KEY_ATTRIBUTE_CONSTRAINT,
            SHAPE_VALUE_ATTRIBUTE_CONSTRAINT);
    }
 
    public static boolean isAttributeConstraintShape(PictogramElement pe) {
        return SHAPE_VALUE_ATTRIBUTE_CONSTRAINT.equals(Graphiti.getPeService()
           .getPropertyValue(pe, SHAPE_KEY_ATTRIBUTE_CONSTRAINT));
    }
    
    public static final void setSoftgoalShape(PictogramElement pe) {
        Graphiti.getPeService().setPropertyValue(pe, SHAPE_KEY_SOFTGOAL,
            SHAPE_VALUE_SOFTGOAL);
    }
 
    public static boolean isSoftgoalShape(PictogramElement pe) {
        return SHAPE_VALUE_SOFTGOAL.equals(Graphiti.getPeService()
           .getPropertyValue(pe, SHAPE_KEY_SOFTGOAL));
    }
    
	/**
	 * Opens an simple input dialog with OK and Cancel buttons.
	 * <p>
	 * 
	 * @param dialogTitle
	 *            the dialog title, or <code>null</code> if none
	 * @param dialogMessage
	 *            the dialog message, or <code>null</code> if none
	 * @param initialValue
	 *            the initial input value, or <code>null</code> if none
	 *            (equivalent to the empty string)
	 * @return the string
	 */
	public static String askString(String dialogTitle, String dialogMessage, String initialValue, IInputValidator inputValidator) {
		String ret = null;
		Shell shell = getShell();
		InputDialog inputDialog = new InputDialog(shell, dialogTitle, dialogMessage, initialValue, inputValidator);
		int retDialog = inputDialog.open();
		if (retDialog == Window.OK) {
			ret = inputDialog.getValue();
		}
		return ret;
	}

	/**
	 * Opens a dialog to change the color.
	 * 
	 * @param color
	 *            the color to change
	 * @return the changed color
	 */
//	public static Color editColor(Color color) {
//		if (color != null && color.eContainer() instanceof Diagram) {
//			Shell shell = getShell();
//			ColorDialog colorDialog = new ColorDialog(shell);
//			colorDialog.setText("Choose Color");
//			colorDialog.setRGB(new RGB(color.getRed(), color.getGreen(), color.getBlue()));
//
//			RGB retRgb = colorDialog.open();
//			if (retRgb == null) {
//				return null;
//			}
//
//			Diagram diagram = (Diagram) color.eContainer();
//			Color newColor = Graphiti.getGaService().manageColor(diagram, retRgb.red, retRgb.green, retRgb.blue);
//			return newColor;
//
//		}
//
//		return null;
//	}

	/**
	 * Returns the currently active Shell.
	 * 
	 * @return The currently active Shell.
	 */
	private static Shell getShell() {
		return PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
	}
}
