/**
 */
package eu.cloudwave.ude.fcore.graphiti.FCORE.impl;

import eu.cloudwave.ude.fcore.graphiti.FCORE.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class FCOREFactoryImpl extends EFactoryImpl implements FCOREFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static FCOREFactory init() {
		try {
			FCOREFactory theFCOREFactory = (FCOREFactory)EPackage.Registry.INSTANCE.getEFactory(FCOREPackage.eNS_URI);
			if (theFCOREFactory != null) {
				return theFCOREFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new FCOREFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FCOREFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case FCOREPackage.FEATURE_MODEL: return createFeatureModel();
			case FCOREPackage.ROOT_FEATURE: return createRootFeature();
			case FCOREPackage.SOLITARY_FEATURE: return createSolitaryFeature();
			case FCOREPackage.GROUP_FEATURE: return createGroupFeature();
			case FCOREPackage.FEATURE_GROUP: return createFeatureGroup();
			case FCOREPackage.ATTRIBUTE: return createAttribute();
			case FCOREPackage.ATTRIBUTE_CONSTRAINT: return createAttributeConstraint();
			case FCOREPackage.SOFTGOAL: return createSoftgoal();
			case FCOREPackage.REQUIRES_FEATURE_CONSTRAINT: return createRequiresFeatureConstraint();
			case FCOREPackage.EXCLUDES_FEATURE_CONSTRAINT: return createExcludesFeatureConstraint();
			case FCOREPackage.INFLUENCE_FEATURE: return createInfluenceFeature();
			case FCOREPackage.INFLUENCE_ATTRIBUTE: return createInfluenceAttribute();
			case FCOREPackage.CARDINALITY_CONNECTION: return createCardinalityConnection();
			case FCOREPackage.MANDATORY_CONNECTION: return createMandatoryConnection();
			case FCOREPackage.OPTIONAL_CONNECTION: return createOptionalConnection();
			case FCOREPackage.FEATURE_TO_GROUP_CONNECTION: return createFeatureToGroupConnection();
			case FCOREPackage.GROUP_TO_FEATURE_CONNECTION: return createGroupToFeatureConnection();
			case FCOREPackage.ATTRIBUTE_CONSTRAINT_CONNECTION: return createAttributeConstraintConnection();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FeatureModel createFeatureModel() {
		FeatureModelImpl featureModel = new FeatureModelImpl();
		return featureModel;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RootFeature createRootFeature() {
		RootFeatureImpl rootFeature = new RootFeatureImpl();
		return rootFeature;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SolitaryFeature createSolitaryFeature() {
		SolitaryFeatureImpl solitaryFeature = new SolitaryFeatureImpl();
		return solitaryFeature;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GroupFeature createGroupFeature() {
		GroupFeatureImpl groupFeature = new GroupFeatureImpl();
		return groupFeature;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FeatureGroup createFeatureGroup() {
		FeatureGroupImpl featureGroup = new FeatureGroupImpl();
		return featureGroup;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Attribute createAttribute() {
		AttributeImpl attribute = new AttributeImpl();
		return attribute;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AttributeConstraint createAttributeConstraint() {
		AttributeConstraintImpl attributeConstraint = new AttributeConstraintImpl();
		return attributeConstraint;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Softgoal createSoftgoal() {
		SoftgoalImpl softgoal = new SoftgoalImpl();
		return softgoal;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RequiresFeatureConstraint createRequiresFeatureConstraint() {
		RequiresFeatureConstraintImpl requiresFeatureConstraint = new RequiresFeatureConstraintImpl();
		return requiresFeatureConstraint;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ExcludesFeatureConstraint createExcludesFeatureConstraint() {
		ExcludesFeatureConstraintImpl excludesFeatureConstraint = new ExcludesFeatureConstraintImpl();
		return excludesFeatureConstraint;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InfluenceFeature createInfluenceFeature() {
		InfluenceFeatureImpl influenceFeature = new InfluenceFeatureImpl();
		return influenceFeature;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InfluenceAttribute createInfluenceAttribute() {
		InfluenceAttributeImpl influenceAttribute = new InfluenceAttributeImpl();
		return influenceAttribute;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CardinalityConnection createCardinalityConnection() {
		CardinalityConnectionImpl cardinalityConnection = new CardinalityConnectionImpl();
		return cardinalityConnection;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MandatoryConnection createMandatoryConnection() {
		MandatoryConnectionImpl mandatoryConnection = new MandatoryConnectionImpl();
		return mandatoryConnection;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OptionalConnection createOptionalConnection() {
		OptionalConnectionImpl optionalConnection = new OptionalConnectionImpl();
		return optionalConnection;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FeatureToGroupConnection createFeatureToGroupConnection() {
		FeatureToGroupConnectionImpl featureToGroupConnection = new FeatureToGroupConnectionImpl();
		return featureToGroupConnection;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GroupToFeatureConnection createGroupToFeatureConnection() {
		GroupToFeatureConnectionImpl groupToFeatureConnection = new GroupToFeatureConnectionImpl();
		return groupToFeatureConnection;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AttributeConstraintConnection createAttributeConstraintConnection() {
		AttributeConstraintConnectionImpl attributeConstraintConnection = new AttributeConstraintConnectionImpl();
		return attributeConstraintConnection;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FCOREPackage getFCOREPackage() {
		return (FCOREPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static FCOREPackage getPackage() {
		return FCOREPackage.eINSTANCE;
	}

} //FCOREFactoryImpl
