package org.vadere.simulator.projects.migration.jsontranformation.json;

import com.fasterxml.jackson.databind.JsonNode;
import org.vadere.annotation.factories.migrationassistant.MigrationTransformation;
import org.vadere.simulator.projects.migration.MigrationException;
import org.vadere.simulator.projects.migration.jsontranformation.SimpleJsonTransformation;
import org.vadere.util.version.Version;

@MigrationTransformation(targetVersionLabel = "2.6")
public class TargetVersionV2_6 extends SimpleJsonTransformation {
    public TargetVersionV2_6() {
        super(Version.V2_6);
    }

    @Override
    protected void initDefaultHooks() {
        addPostHookLast(this::removePropertyAttributesCGM_LostMembers);
        addPostHookLast(this::removePropertyAttributesCGM_WaitBehaviourRelevantAgentsFactor);
        addPostHookLast(this::sort);
    }

    private JsonNode removePropertyAttributesCGM_LostMembers(JsonNode node) throws MigrationException {
        JsonNode nd = path(node, "scenario/attributesModel/org.vadere.state.attributes.models.AttributesCGM");
        if(!nd.isMissingNode()){
            remove(nd, "lostMembers");
        }
        return node;
    }
    private JsonNode removePropertyAttributesCGM_WaitBehaviourRelevantAgentsFactor(JsonNode node) throws MigrationException {
        JsonNode nd = path(node, "scenario/attributesModel/org.vadere.state.attributes.models.AttributesCGM");
        if(!nd.isMissingNode()){
            remove(nd, "waitBehaviourRelevantAgentsFactor");
        }
        return node;
    }
}
