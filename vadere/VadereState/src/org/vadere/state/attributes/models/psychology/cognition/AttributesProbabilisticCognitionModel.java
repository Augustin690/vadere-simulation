package org.vadere.state.attributes.models.psychology.cognition;

import org.vadere.state.psychology.perception.types.InformationStimulus;

import java.util.LinkedList;
import java.util.List;
/**
 * <p>
 * The ProbabilisticCognitionModel models probabilistic route choice behavior.
 * </p>
 * <p>
 * If an agent has received a route recommendation (you need to specify the route recommendation using a {@link InformationStimulus}),
 * the agent will make a probabilistic route choice.
 * Imagine there are two targets: if target 1 is recommended ("take target 1"),
 * the agent takes target 1 with a probability of 80% and target 2 with a probability of 20%.
 * The probabilities need to be specified in {@link AttributesRouteChoiceDefinition}.
 * </p>
 * <p>
 * Please note:
 *    - the appeal "take target 1" must be similar in the {@link InformationStimulus} and the {@link AttributesRouteChoiceDefinition}.
 *    - the agent responds to the first appeal only
 * </p>
 * <p>
 * Example:
 * <code>
 *     "org.vadere.state.attributes.models.psychology.cognition.AttributesProbabilisticCognitionModel": {
 *              "routeChoices" : [ {
 *                "instruction" : "use target 11",
 *                "targetIds" : [ 11, 21, 31 ],
 *                "targetProbabilities" : [ 1.0, 0.0, 0.0 ]
 *              }, {
 *                "instruction" : "use target 21",
 *                "targetIds" : [ 11, 21, 31 ],
 *                "targetProbabilities" : [ 0.0, 1.0, 0.0 ]
 *              }, {
 *                "instruction" : "use target 31",
 *                "targetIds" : [ 11, 21, 31 ],
 *                "targetProbabilities" : [ 0.0, 0.0, 1.0 ]
 *              } ]
 *            }
 *          }
 *        }
 *      }
 * </code>
 */
public class AttributesProbabilisticCognitionModel extends AttributesCognitionModel {
    List<AttributesRouteChoiceDefinition> routeChoices;

    public AttributesProbabilisticCognitionModel(){
        routeChoices = new LinkedList<>();
    }

    public List<AttributesRouteChoiceDefinition> getRouteChoices() {
        return routeChoices;
    }

    public void setRouteChoices(List<AttributesRouteChoiceDefinition> routeChoices) {
        this.routeChoices = routeChoices;
    }


}
