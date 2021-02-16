package rogue.controller;

import rogue.model.Entity;
import rogue.view.View;

/**
 * An abstract implementation of {@link EntityController}.
 */
public abstract class AbstractEntityController implements EntityController {

    private final View view;
    private final Entity entity;

    /**
     * Creates a new AbstractEntityController.
     * @param view
     *          the entity view which this controller refers to
     * @param entity
     *          the entity which this controller refers to
     */
    protected AbstractEntityController(final View view, final Entity entity) {
        this.view = view;
        this.entity = entity;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public View getView() {
        return this.view;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Entity getEntity() {
        return this.entity;
    }

}
