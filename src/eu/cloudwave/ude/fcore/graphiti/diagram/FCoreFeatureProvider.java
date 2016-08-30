package eu.cloudwave.ude.fcore.graphiti.diagram;

import org.eclipse.graphiti.dt.IDiagramTypeProvider;
import org.eclipse.graphiti.features.IAddFeature;
import org.eclipse.graphiti.features.ICreateConnectionFeature;
import org.eclipse.graphiti.features.ICreateFeature;
import org.eclipse.graphiti.features.IDirectEditingFeature;
import org.eclipse.graphiti.features.ILayoutFeature;
import org.eclipse.graphiti.features.IMoveShapeFeature;
import org.eclipse.graphiti.features.IReconnectionFeature;
import org.eclipse.graphiti.features.IRemoveFeature;
import org.eclipse.graphiti.features.IResizeShapeFeature;
import org.eclipse.graphiti.features.IUpdateFeature;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.context.IDirectEditingContext;
import org.eclipse.graphiti.features.context.ILayoutContext;
import org.eclipse.graphiti.features.context.IMoveShapeContext;
import org.eclipse.graphiti.features.context.IReconnectionContext;
import org.eclipse.graphiti.features.context.IRemoveContext;
import org.eclipse.graphiti.features.context.IResizeShapeContext;
import org.eclipse.graphiti.features.context.IUpdateContext;
import org.eclipse.graphiti.features.impl.DefaultRemoveFeature;
import org.eclipse.graphiti.mm.pictograms.Connection;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.FreeFormConnection;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.ui.features.DefaultFeatureProvider;

import eu.cloudwave.ude.fcore.graphiti.FCORE.Attribute;
import eu.cloudwave.ude.fcore.graphiti.FCORE.AttributeConstraint;
import eu.cloudwave.ude.fcore.graphiti.FCORE.AttributeConstraintConnection;
import eu.cloudwave.ude.fcore.graphiti.FCORE.CardinalityConnection;
import eu.cloudwave.ude.fcore.graphiti.FCORE.ExcludesFeatureConstraint;
import eu.cloudwave.ude.fcore.graphiti.FCORE.FeatureGroup;
import eu.cloudwave.ude.fcore.graphiti.FCORE.FeatureToGroupConnection;
import eu.cloudwave.ude.fcore.graphiti.FCORE.GroupFeature;
import eu.cloudwave.ude.fcore.graphiti.FCORE.GroupToFeatureConnection;
import eu.cloudwave.ude.fcore.graphiti.FCORE.InfluenceAttribute;
import eu.cloudwave.ude.fcore.graphiti.FCORE.InfluenceFeature;
import eu.cloudwave.ude.fcore.graphiti.FCORE.MandatoryConnection;
import eu.cloudwave.ude.fcore.graphiti.FCORE.OptionalConnection;
import eu.cloudwave.ude.fcore.graphiti.FCORE.RequiresFeatureConstraint;
import eu.cloudwave.ude.fcore.graphiti.FCORE.RootFeature;
import eu.cloudwave.ude.fcore.graphiti.FCORE.Softgoal;
import eu.cloudwave.ude.fcore.graphiti.FCORE.SolitaryFeature;
import eu.cloudwave.ude.fcore.graphiti.features.connections.attributeconstraint.AddAttributeConstraintConnectionFeature;
import eu.cloudwave.ude.fcore.graphiti.features.connections.attributeconstraint.CreateAttributeConstraintConnectionFeature;
import eu.cloudwave.ude.fcore.graphiti.features.connections.attributeconstraint.ReconnectionAttributeConstraintConnectionFeature;
import eu.cloudwave.ude.fcore.graphiti.features.connections.cardinality.AddCardinalityConnectionFeature;
import eu.cloudwave.ude.fcore.graphiti.features.connections.cardinality.CreateCardinalityConnectionFeature;
import eu.cloudwave.ude.fcore.graphiti.features.connections.cardinality.ReconnectionCardinalityConnectionFeature;
import eu.cloudwave.ude.fcore.graphiti.features.connections.cardinality.UpdateCardinalityConnectionFeature;
import eu.cloudwave.ude.fcore.graphiti.features.connections.excludes.AddExcludesFeatureConstraintFeature;
import eu.cloudwave.ude.fcore.graphiti.features.connections.excludes.CreateExcludesFeatureConstraintFeature;
import eu.cloudwave.ude.fcore.graphiti.features.connections.excludes.ReconnectionExcludesFeatureConstraintFeature;
import eu.cloudwave.ude.fcore.graphiti.features.connections.featuretogroup.AddFeatureToFeatureGroupConnectionFeature;
import eu.cloudwave.ude.fcore.graphiti.features.connections.featuretogroup.CreateFeatureToFeatureGroupConnectionFeature;
import eu.cloudwave.ude.fcore.graphiti.features.connections.featuretogroup.ReconnectionFeatureToGroupConnectionFeature;
import eu.cloudwave.ude.fcore.graphiti.features.connections.grouptofeature.AddFeatureGroupToFeatureConnectionFeature;
import eu.cloudwave.ude.fcore.graphiti.features.connections.grouptofeature.CreateFeatureGroupToFeatureConnectionFeature;
import eu.cloudwave.ude.fcore.graphiti.features.connections.grouptofeature.ReconnectionGroupToFeatureConnectionFeature;
import eu.cloudwave.ude.fcore.graphiti.features.connections.influenceattribute.AddInfluenceAttributeFeature;
import eu.cloudwave.ude.fcore.graphiti.features.connections.influenceattribute.CreateInfluenceAttributeFeature;
import eu.cloudwave.ude.fcore.graphiti.features.connections.influenceattribute.ReconnectionInfluenceAttributeFeature;
import eu.cloudwave.ude.fcore.graphiti.features.connections.influenceattribute.UpdateInfluenceAttributeFeature;
import eu.cloudwave.ude.fcore.graphiti.features.connections.influencefeature.AddInfluenceFeatureFeature;
import eu.cloudwave.ude.fcore.graphiti.features.connections.influencefeature.CreateInfluenceFeatureFeature;
import eu.cloudwave.ude.fcore.graphiti.features.connections.influencefeature.ReconnectionInfluenceFeatureFeature;
import eu.cloudwave.ude.fcore.graphiti.features.connections.influencefeature.UpdateInfluenceFeatureFeature;
import eu.cloudwave.ude.fcore.graphiti.features.connections.mandatory.AddMandatoryConnectionFeature;
import eu.cloudwave.ude.fcore.graphiti.features.connections.mandatory.CreateMandatoryConnectionFeature;
import eu.cloudwave.ude.fcore.graphiti.features.connections.mandatory.ReconnectionMandatoryConnectionFeature;
import eu.cloudwave.ude.fcore.graphiti.features.connections.optional.AddOptionalConnectionFeature;
import eu.cloudwave.ude.fcore.graphiti.features.connections.optional.CreateOptionalConnectionFeature;
import eu.cloudwave.ude.fcore.graphiti.features.connections.optional.ReconnectionOptionalConnectionFeature;
import eu.cloudwave.ude.fcore.graphiti.features.connections.requires.AddRequiresFeatureConstraintFeature;
import eu.cloudwave.ude.fcore.graphiti.features.connections.requires.CreateRequiresFeatureConstraintFeature;
import eu.cloudwave.ude.fcore.graphiti.features.connections.requires.ReconnectionRequiresFeatureConstraintFeature;
import eu.cloudwave.ude.fcore.graphiti.features.rectangles.attribute.AddAttributeFeature;
import eu.cloudwave.ude.fcore.graphiti.features.rectangles.attribute.CreateAttributeFeature;
import eu.cloudwave.ude.fcore.graphiti.features.rectangles.attribute.DirectEditAttributeFeature;
import eu.cloudwave.ude.fcore.graphiti.features.rectangles.attribute.LayoutAttributeFeature;
import eu.cloudwave.ude.fcore.graphiti.features.rectangles.attribute.MoveAttributeFeature;
import eu.cloudwave.ude.fcore.graphiti.features.rectangles.attribute.RemoveAttributeFeature;
import eu.cloudwave.ude.fcore.graphiti.features.rectangles.attribute.ResizeAttributeFeature;
import eu.cloudwave.ude.fcore.graphiti.features.rectangles.attribute.UpdateAttributeFeature;
import eu.cloudwave.ude.fcore.graphiti.features.rectangles.attributeconstraint.AddAttributeConstraintFeature;
import eu.cloudwave.ude.fcore.graphiti.features.rectangles.attributeconstraint.CreateAttributeConstraintFeature;
import eu.cloudwave.ude.fcore.graphiti.features.rectangles.attributeconstraint.DirectEditAttributeConstraintFeature;
import eu.cloudwave.ude.fcore.graphiti.features.rectangles.attributeconstraint.LayoutAttributeConstraintFeature;
import eu.cloudwave.ude.fcore.graphiti.features.rectangles.attributeconstraint.ResizeAttributeConstraintFeature;
import eu.cloudwave.ude.fcore.graphiti.features.rectangles.attributeconstraint.UpdateAttributeConstraintFeature;
import eu.cloudwave.ude.fcore.graphiti.features.rectangles.featuregroup.AddFeatureGroupFeature;
import eu.cloudwave.ude.fcore.graphiti.features.rectangles.featuregroup.CreateFeatureGroupFeature;
import eu.cloudwave.ude.fcore.graphiti.features.rectangles.featuregroup.LayoutFeatureGroupFeature;
import eu.cloudwave.ude.fcore.graphiti.features.rectangles.featuregroup.ResizeFeatureGroupFeature;
import eu.cloudwave.ude.fcore.graphiti.features.rectangles.featuregroup.UpdateFeatureGroupFeature;
import eu.cloudwave.ude.fcore.graphiti.features.rectangles.groupfeature.AddGroupFeatureFeature;
import eu.cloudwave.ude.fcore.graphiti.features.rectangles.groupfeature.CreateGroupFeatureFeature;
import eu.cloudwave.ude.fcore.graphiti.features.rectangles.groupfeature.DirectEditGroupFeatureFeature;
import eu.cloudwave.ude.fcore.graphiti.features.rectangles.groupfeature.LayoutGroupFeatureFeature;
import eu.cloudwave.ude.fcore.graphiti.features.rectangles.groupfeature.UpdateGroupFeatureFeature;
import eu.cloudwave.ude.fcore.graphiti.features.rectangles.rootfeature.AddRootFeatureFeature;
import eu.cloudwave.ude.fcore.graphiti.features.rectangles.rootfeature.CreateRootFeatureFeature;
import eu.cloudwave.ude.fcore.graphiti.features.rectangles.rootfeature.DirectEditRootFeatureFeature;
import eu.cloudwave.ude.fcore.graphiti.features.rectangles.rootfeature.LayoutRootFeatureFeature;
import eu.cloudwave.ude.fcore.graphiti.features.rectangles.rootfeature.UpdateRootFeatureFeature;
import eu.cloudwave.ude.fcore.graphiti.features.rectangles.softgoal.AddSoftgoalFeature;
import eu.cloudwave.ude.fcore.graphiti.features.rectangles.softgoal.CreateSoftgoalFeature;
import eu.cloudwave.ude.fcore.graphiti.features.rectangles.softgoal.DirectEditSoftgoalFeature;
import eu.cloudwave.ude.fcore.graphiti.features.rectangles.softgoal.LayoutSoftgoalFeature;
import eu.cloudwave.ude.fcore.graphiti.features.rectangles.softgoal.UpdateSoftgoalFeature;
import eu.cloudwave.ude.fcore.graphiti.features.rectangles.solitaryfeature.AddSolitaryFeatureFeature;
import eu.cloudwave.ude.fcore.graphiti.features.rectangles.solitaryfeature.CreateSolitaryFeatureFeature;
import eu.cloudwave.ude.fcore.graphiti.features.rectangles.solitaryfeature.DirectEditSolitaryFeatureFeature;
import eu.cloudwave.ude.fcore.graphiti.features.rectangles.solitaryfeature.LayoutSolitaryFeatureFeature;
import eu.cloudwave.ude.fcore.graphiti.features.rectangles.solitaryfeature.UpdateSolitaryFeatureFeature;

public class FCoreFeatureProvider extends DefaultFeatureProvider {

	public FCoreFeatureProvider(IDiagramTypeProvider dtp) {
		super(dtp);
	}

	@Override
	public IAddFeature getAddFeature(IAddContext context) {
		if (context.getNewObject() instanceof RootFeature) {
			return new AddRootFeatureFeature(this);
		} else if (context.getNewObject() instanceof SolitaryFeature) {
			return new AddSolitaryFeatureFeature(this);
		} else if (context.getNewObject() instanceof GroupFeature) {
			return new AddGroupFeatureFeature(this);
		} else if (context.getNewObject() instanceof FeatureGroup) {
			return new AddFeatureGroupFeature(this);
		} else if (context.getNewObject() instanceof Softgoal) {
			return new AddSoftgoalFeature(this);
		} else if (context.getNewObject() instanceof AttributeConstraint) {
			return new AddAttributeConstraintFeature(this);
		} else if (context.getNewObject() instanceof Attribute) {
			return new AddAttributeFeature(this);
		} else if (context.getNewObject() instanceof CardinalityConnection) {
			return new AddCardinalityConnectionFeature(this);
		} else if (context.getNewObject() instanceof MandatoryConnection) {
			return new AddMandatoryConnectionFeature(this);
		} else if (context.getNewObject() instanceof OptionalConnection) {
			return new AddOptionalConnectionFeature(this);
		} else if (context.getNewObject() instanceof ExcludesFeatureConstraint) {
			return new AddExcludesFeatureConstraintFeature(this);
		} else if (context.getNewObject() instanceof RequiresFeatureConstraint) {
			return new AddRequiresFeatureConstraintFeature(this);
		} else if (context.getNewObject() instanceof FeatureToGroupConnection) {
			return new AddFeatureToFeatureGroupConnectionFeature(this);
		} else if (context.getNewObject() instanceof GroupToFeatureConnection) {
			return new AddFeatureGroupToFeatureConnectionFeature(this);
		} else if (context.getNewObject() instanceof InfluenceFeature) {
			return new AddInfluenceFeatureFeature(this);
		} else if (context.getNewObject() instanceof InfluenceAttribute) {
			return new AddInfluenceAttributeFeature(this);
		} else if (context.getNewObject() instanceof AttributeConstraintConnection) {
			return new AddAttributeConstraintConnectionFeature(this);
		}
		return super.getAddFeature(context);
	}

	@Override
	public ICreateFeature[] getCreateFeatures() {
		return new ICreateFeature[] {
				new CreateRootFeatureFeature(this),
				new CreateSolitaryFeatureFeature(this),
				new CreateGroupFeatureFeature(this),
				new CreateFeatureGroupFeature(this),
				new CreateSoftgoalFeature(this),
				new CreateAttributeConstraintFeature(this),
				new CreateAttributeFeature(this)
				};
	}

	@Override
	public ICreateConnectionFeature[] getCreateConnectionFeatures() {
		return new ICreateConnectionFeature[] {
				new CreateCardinalityConnectionFeature(this),
				new CreateMandatoryConnectionFeature(this),
				new CreateOptionalConnectionFeature(this),
				new CreateExcludesFeatureConstraintFeature(this),
				new CreateRequiresFeatureConstraintFeature(this),
				new CreateFeatureToFeatureGroupConnectionFeature(this),
				new CreateFeatureGroupToFeatureConnectionFeature(this),
				new CreateInfluenceFeatureFeature(this),
				new CreateInfluenceAttributeFeature(this),
				new CreateAttributeConstraintConnectionFeature(this)
				};
	}

	@Override
	public IUpdateFeature getUpdateFeature(IUpdateContext context) {
		PictogramElement pictogramElement = context.getPictogramElement();
		if (pictogramElement instanceof FreeFormConnection) {
			Object bo = getBusinessObjectForPictogramElement(pictogramElement);
			if (bo instanceof InfluenceAttribute) {
				return new UpdateInfluenceAttributeFeature(this);
			} else if (bo instanceof InfluenceFeature) {
				return new UpdateInfluenceFeatureFeature(this);
			} else if (bo instanceof CardinalityConnection) {
				return new UpdateCardinalityConnectionFeature(this);
			}
		} else if (pictogramElement instanceof ContainerShape) {
			Object bo = getBusinessObjectForPictogramElement(pictogramElement);
			if (bo instanceof AttributeConstraint) {
				return new UpdateAttributeConstraintFeature(this);
			} else if (bo instanceof Attribute) {
				return new UpdateAttributeFeature(this);
			} else if (bo instanceof GroupFeature) {
				return new UpdateGroupFeatureFeature(this);
			} else if (bo instanceof FeatureGroup) {
				return new UpdateFeatureGroupFeature(this);
			} else if (bo instanceof RootFeature) {
				return new UpdateRootFeatureFeature(this);
			} else if (bo instanceof Softgoal) {
				return new UpdateSoftgoalFeature(this);
			} else if (bo instanceof SolitaryFeature) {
				return new UpdateSolitaryFeatureFeature(this);
			}
		}
		return super.getUpdateFeature(context);
	}

	@Override
	public IMoveShapeFeature getMoveShapeFeature(IMoveShapeContext context) {
		Shape shape = context.getShape();
		Object bo = getBusinessObjectForPictogramElement(shape);
		if (bo instanceof Attribute) {
			return new MoveAttributeFeature(this);
		}
		return super.getMoveShapeFeature(context);
	}

	@Override
	public IResizeShapeFeature getResizeShapeFeature(IResizeShapeContext context) {
		Shape shape = context.getShape();
		Object bo = getBusinessObjectForPictogramElement(shape);
		if (bo instanceof Attribute) {
			return new ResizeAttributeFeature(this);
		} else if (bo instanceof AttributeConstraint) {
			return new ResizeAttributeConstraintFeature(this);
		} else if (bo instanceof FeatureGroup) {
			return new ResizeFeatureGroupFeature(this);
		}
		return super.getResizeShapeFeature(context);
	}

	@Override
	public ILayoutFeature getLayoutFeature(ILayoutContext context) {
		PictogramElement pictogramElement = context.getPictogramElement();
		Object bo = getBusinessObjectForPictogramElement(pictogramElement);
		if (bo instanceof AttributeConstraint) {
			return new LayoutAttributeConstraintFeature(this);
		} else if (bo instanceof Attribute) {
			return new LayoutAttributeFeature(this);
		} else if (bo instanceof FeatureGroup) {
			return new LayoutFeatureGroupFeature(this);
		} else if (bo instanceof GroupFeature) {
			return new LayoutGroupFeatureFeature(this);
		} else if (bo instanceof RootFeature) {
			return new LayoutRootFeatureFeature(this);
		} else if (bo instanceof Softgoal) {
			return new LayoutSoftgoalFeature(this);
		} else if (bo instanceof SolitaryFeature) {
			return new LayoutSolitaryFeatureFeature(this);
		}
		return super.getLayoutFeature(context);
	}

	@Override
	public IDirectEditingFeature getDirectEditingFeature(
			IDirectEditingContext context) {
		PictogramElement pe = context.getPictogramElement();
		Object bo = getBusinessObjectForPictogramElement(pe);
		if (bo instanceof AttributeConstraint) {
			return new DirectEditAttributeConstraintFeature(this);
		} else if (bo instanceof Attribute) {
			return new DirectEditAttributeFeature(this);
		} else if (bo instanceof GroupFeature) {
			return new DirectEditGroupFeatureFeature(this);
		} else if (bo instanceof RootFeature) {
			return new DirectEditRootFeatureFeature(this);
		} else if (bo instanceof Softgoal) {
			return new DirectEditSoftgoalFeature(this);
		} else if (bo instanceof SolitaryFeature) {
			return new DirectEditSolitaryFeatureFeature(this);
		}
		return super.getDirectEditingFeature(context);
	}
	
	@Override
	public IRemoveFeature getRemoveFeature(IRemoveContext context) {
		PictogramElement pe = context.getPictogramElement();
		Object bo = getBusinessObjectForPictogramElement(pe);
		if (bo instanceof Attribute) {
			return new RemoveAttributeFeature(this);
		}
		return new DefaultRemoveFeature(this);
	}
	
	@Override
	public IReconnectionFeature getReconnectionFeature(IReconnectionContext context) {
		Connection connection = context.getConnection();
		Object bo = getBusinessObjectForPictogramElement(connection);
		if (bo instanceof AttributeConstraintConnection) {
			return new ReconnectionAttributeConstraintConnectionFeature(this);
		} else if (bo instanceof CardinalityConnection) {
			return new ReconnectionCardinalityConnectionFeature(this);
		} else if (bo instanceof ExcludesFeatureConstraint) {
			return new ReconnectionExcludesFeatureConstraintFeature(this);
		} else if (bo instanceof FeatureToGroupConnection) {
			return new ReconnectionFeatureToGroupConnectionFeature(this);
		} else if (bo instanceof GroupToFeatureConnection) {
			return new ReconnectionGroupToFeatureConnectionFeature(this);
		} else if (bo instanceof InfluenceAttribute) {
			return new ReconnectionInfluenceAttributeFeature(this);
		} else if (bo instanceof InfluenceFeature) {
			return new ReconnectionInfluenceFeatureFeature(this);
		} else if (bo instanceof MandatoryConnection) {
			return new ReconnectionMandatoryConnectionFeature(this);
		} else if (bo instanceof OptionalConnection) {
			return new ReconnectionOptionalConnectionFeature(this);
		} else if (bo instanceof RequiresFeatureConstraint) {
			return new ReconnectionRequiresFeatureConstraintFeature(this);
		}
		return super.getReconnectionFeature(context);
	}
}
