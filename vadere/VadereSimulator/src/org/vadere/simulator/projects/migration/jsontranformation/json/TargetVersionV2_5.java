package org.vadere.simulator.projects.migration.jsontranformation.json;

import com.fasterxml.jackson.databind.JsonNode;
import org.vadere.annotation.factories.migrationassistant.MigrationTransformation;
import org.vadere.simulator.projects.migration.MigrationException;
import org.vadere.simulator.projects.migration.jsontranformation.SimpleJsonTransformation;
import org.vadere.util.version.Version;

@MigrationTransformation(targetVersionLabel = "2.5")
public class TargetVersionV2_5 extends SimpleJsonTransformation {
    public TargetVersionV2_5() {
        super(Version.V2_5);
    }

    @Override
    protected void initDefaultHooks() {
        addPostHookLast(this::checkForOVM);
        addPostHookLast(this::removeAttributesCar);
        addPostHookLast(this::sort);
    }

    private JsonNode removeAttributesCar(JsonNode jsonNode) throws MigrationException {
        if (!path(jsonNode, "scenario/topography").isMissingNode()) {
            remove(path(jsonNode, "scenario/topography"),"attributesCar");
        }
        return jsonNode;
    }

    private JsonNode checkForOVM(JsonNode jsonNode) {
        if (!path(jsonNode, "scenario/mainModel").isMissingNode()) {
            if (path(jsonNode, "scenario/mainModel").asText().equals("org.vadere.simulator.models.ovm.OptimalVelocityModel")) {
                throw new IllegalArgumentException("OptimalVelocityModel is not supported anymore.");
            }
        }
        return jsonNode;
    }
}