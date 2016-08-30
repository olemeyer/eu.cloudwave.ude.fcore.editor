/**
 */
package eu.cloudwave.ude.fcore.graphiti.FCORE;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see eu.cloudwave.ude.fcore.graphiti.FCORE.FCOREPackage
 * @generated
 */
public interface FCOREFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	FCOREFactory eINSTANCE = eu.cloudwave.ude.fcore.graphiti.FCORE.impl.FCOREFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Feature Model</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Feature Model</em>'.
	 * @generated
	 */
	FeatureModel createFeatureModel();

	/**
	 * Returns a new object of class '<em>Root Feature</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Root Feature</em>'.
	 * @generated
	 */
	RootFeature createRootFeature();

	/**
	 * Returns a new object of class '<em>Solitary Feature</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Solitary Feature</em>'.
	 * @generated
	 */
	SolitaryFeature createSolitaryFeature();

	/**
	 * Returns a new object of class '<em>Group Feature</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Group Feature</em>'.
	 * @generated
	 */
	GroupFeature createGroupFeature();

	/**
	 * Returns a new object of class '<em>Feature Group</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Feature Group</em>'.
	 * @generated
	 */
	FeatureGroup createFeatureGroup();

	/**
	 * Returns a new object of class '<em>Attribute</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Attribute</em>'.
	 * @generated
	 */
	Attribute createAttribute();

	/**
	 * Returns a new object of class '<em>Attribute Constraint</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Attribute Constraint</em>'.
	 * @generated
	 */
	AttributeConstraint createAttributeConstraint();

	/**
	 * Returns a new object of class '<em>Softgoal</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Softgoal</em>'.
	 * @generated
	 */
	Softgoal createSoftgoal();

	/**
	 * Returns a new object of class '<em>Requires Feature Constraint</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Requires Feature Constraint</em>'.
	 * @generated
	 */
	RequiresFeatureConstraint createRequiresFeatureConstraint();

	/**
	 * Returns a new object of class '<em>Excludes Feature Constraint</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Excludes Feature Constraint</em>'.
	 * @generated
	 */
	ExcludesFeatureConstraint createExcludesFeatureConstraint();

	/**
	 * Returns a new object of class '<em>Influence Feature</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Influence Feature</em>'.
	 * @generated
	 */
	InfluenceFeature createInfluenceFeature();

	/**
	 * Returns a new object of class '<em>Influence Attribute</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Influence Attribute</em>'.
	 * @generated
	 */
	InfluenceAttribute createInfluenceAttribute();

	/**
	 * Returns a new object of class '<em>Cardinality Connection</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Cardinality Connection</em>'.
	 * @generated
	 */
	CardinalityConnection createCardinalityConnection();

	/**
	 * Returns a new object of class '<em>Mandatory Connection</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Mandatory Connection</em>'.
	 * @generated
	 */
	MandatoryConnection createMandatoryConnection();

	/**
	 * Returns a new object of class '<em>Optional Connection</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Optional Connection</em>'.
	 * @generated
	 */
	OptionalConnection createOptionalConnection();

	/**
	 * Returns a new object of class '<em>Feature To Group Connection</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Feature To Group Connection</em>'.
	 * @generated
	 */
	FeatureToGroupConnection createFeatureToGroupConnection();

	/**
	 * Returns a new object of class '<em>Group To Feature Connection</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Group To Feature Connection</em>'.
	 * @generated
	 */
	GroupToFeatureConnection createGroupToFeatureConnection();

	/**
	 * Returns a new object of class '<em>Attribute Constraint Connection</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Attribute Constraint Connection</em>'.
	 * @generated
	 */
	AttributeConstraintConnection createAttributeConstraintConnection();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	FCOREPackage getFCOREPackage();

} //FCOREFactory
