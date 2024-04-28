package org.vadere.state.attributes.scenario;

import com.fasterxml.jackson.annotation.JsonView;
import org.vadere.state.attributes.AttributesAbsorber;
import org.vadere.state.attributes.AttributesWaiter;
import org.vadere.state.scenario.Pedestrian;
import org.vadere.state.scenario.Target;
import org.vadere.state.util.Views;
import org.vadere.util.geometry.shapes.VShape;
import org.vadere.util.reflection.VadereAttribute;
/**
 * A <i>Target</i> in Vadere is a scenario element that serves as a destination for {@link Pedestrian}s.
 * It may absorb {@link Pedestrian}s or it may be used to let them wait for a certain time.
 * The behaviour of a target is controlled by the {@link AttributesAbsorber} and {@link AttributesWaiter} attributes.
 * <br>@author Ludwig Jaeck
 */
public class AttributesTarget extends AttributesVisualElement {
	/**
	 * <i>absorber</i> is the {@link AttributesAbsorber} that controls the absorption behaviour of this target.
	 */
	@VadereAttribute
	@JsonView(Views.CacheViewExclude.class)
	private AttributesAbsorber absorber = new AttributesAbsorber(true, 0.1);
	/**
	 * <i>waiter</i> is the {@link AttributesWaiter} that controls the waiting behaviour of this target.
	 */
	@VadereAttribute
	@JsonView(Views.CacheViewExclude.class)
	private AttributesWaiter waiter = new AttributesWaiter();
	/**
	 * <i>leavingSpeed</i> stores the free flow speed an agent has after leaving this target. If set to -1, the free flow
	 * speed is not altered by the waiter.
	 */
	@VadereAttribute
	@JsonView(Views.CacheViewExclude.class)
	private Double leavingSpeed = -1.0;
	/**
	 * <i>parallelEvents</i> is the number of events that can be processed in parallel. Hereby meaning specifically the waiting agents.
	 * If AttributesWaiter.individualWaiting is true, and parallelEvents is 0 or less, then an infinite number of agents can be processed.
	 */
	@VadereAttribute
	@JsonView(Views.CacheViewExclude.class) // ignore when determining if floor field cache is valid
	private Integer parallelEvents = 0;

	public AttributesTarget() {
		super();
	}

	public AttributesTarget(final int id,final VShape shape) {
		super();
		this.shape = shape;
	}

	public AttributesTarget(final VShape shape, final int id) {
		this.shape = shape;
		this.id = id;
	}
	public AttributesTarget(final VShape shape, final int id,boolean absorbing) {
		this(shape,id);
		setAbsorbing(true);
	}

	public AttributesTarget(Pedestrian pedestrian) {
		this.shape = pedestrian.getShape();
		this.id = pedestrian.getIdAsTarget();
	}

	// Getters...

	public boolean isAbsorbing() {
		return this.absorber.isEnabled();
	}

	public void setAbsorbing(boolean absorbing) {
		checkSealed();
		this.absorber.setEnabled(absorbing);
	}

	public AttributesAbsorber getAbsorberAttributes() {
		return absorber;
	}

	public void setAbsorberAttributes(AttributesAbsorber absorber) {
		this.absorber = absorber;
	}

	public Boolean isWaiting() {
		return this.waiter.isEnabled();
	}

	public void setWaiting(Boolean waiting) {
		checkSealed();
		this.waiter.setEnabled(waiting);
	}

	public AttributesWaiter getWaiterAttributes() {
		return waiter;
	}

	public void setWaiterAttributes(AttributesWaiter waiter) {
		checkSealed();
		this.waiter = waiter;
	}

	public Double getLeavingSpeed() {
		return leavingSpeed;
	}

	public void setLeavingSpeed(Double leavingSpeed) {
		checkSealed();
		this.leavingSpeed = leavingSpeed;
	}

	public Integer getParallelEvents() {
		return parallelEvents;
	}

	public void setParallelEvents(Integer parallelEvents) {
		checkSealed();
		this.parallelEvents = parallelEvents;
	}
}
