/*
 * Copyright 2018 Kurento (https://www.kurento.org)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.openvidu.fiware.integration.daos;

import io.openvidu.fiware.integration.commons.JsonManager;
import io.openvidu.fiware.integration.config.OrionConnectorConfiguration;
import io.openvidu.fiware.integration.models.orion.OrionEntity;

/**
 * Generic Class for Publishing and updating Orion Entities.
 *
 * @param <O extends {@link OrionEntity}>
 */
public abstract class OrionPublisher<T, O extends OrionEntity> {

    protected OrionConnector<O> orionConnector;

    public OrionPublisher(OrionConnectorConfiguration config, Class<O> clazz) {
        super();
        this.orionConnector = new OrionConnector<O>(config, clazz) {
        };
    }

    public OrionPublisher(OrionConnectorConfiguration config, JsonManager<O> manager, Class<O> clazz) {
        super();
        this.orionConnector = new OrionConnector<O>(config, manager, clazz) {
        };
    }

    /**
     * Creates a new entity in FIWARE. The {@link #mapEntityToOrionEntity(T entity)}
     * should be implemented to map the entity to the output desired entity in
     * FIWARE
     *
     * @param entity a T entity
     */
    public O publish(T entity) {
        O orionEntity = mapEntityToOrionEntity(entity);
        orionConnector.createNewEntity(orionEntity, true);
        return orionEntity;
    }

    /**
     * Creates a new entity in FIWARE.
     *
     * @param orionEntity a O orionEntity
     */
    public O publish(O orionEntity) {
        orionConnector.createNewEntity(orionEntity, true);
        return orionEntity;
    }

    /**
     * Updates an existing entity in FIWARE. The
     * {@link #mapEntityToOrionEntity(T entity)} should be implemented to map the
     * entity to the output desired entity in FIWARE
     *
     * @param entity a T entity
     */
    public void update(T entity) {
        O orionEntity = mapEntityToOrionEntity(entity);
        orionConnector.updateEntity(orionEntity, true);
    }

    /**
     * Updates an existing entity in FIWARE.
     *
     * @param orionEntity a O orionEntity
     */
    public void update(O orionEntity) {
        orionConnector.updateEntity(orionEntity, true);
    }

    /**
     * Deletes an existing entity identified by "id"
     *
     * @param id a String identifying the OrionEntity to delete
     */
    public void delete(String id) {
        orionConnector.deleteOneEntity(id);
    }

    /**
     * Deletes an existing entity representing the T object
     *
     * @param entity object object that maps to an Entity
     */
    public void delete(T entity) {
        OrionEntity entity2Delete = mapEntityToOrionEntity(entity);
        orionConnector.deleteOneEntity(entity2Delete.getId());
    }

    /**
     * Deletes an existing entity
     */
    public void delete(O orionEntity) {
        orionConnector.deleteOneEntity(orionEntity.getId());
    }

    /**
     * Given an object, maps to an appropriate output FIWARE object.
     *
     * @param entity a T entity
     */
    abstract public O mapEntityToOrionEntity(final T entity);

}
