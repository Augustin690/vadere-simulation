import argparse
import json

# example to use this file:
# $python add_pedestrian.py
# --scenario "/path/to/the/file/RiMEA scenario 6.scenario"
# --x 11.5 --y 1.5 --targetID 3 --speed


# Define the pedestrian to add
pedestrian = '''  {
  "attributes" : {
    "id" : 4,
    "shape" : {
      "x" : 0.0,
      "y" : 0.0,
      "width" : 1.0,
      "height" : 1.0,
      "type" : "RECTANGLE"
    },
    "visible" : true,
    "radius" : 0.2,
    "densityDependentSpeed" : false,
    "speedDistributionMean" : 1.34,
    "speedDistributionStandardDeviation" : 0.26,
    "minimumSpeed" : 0.5,
    "maximumSpeed" : 2.2,
    "acceleration" : 2.0,
    "footstepHistorySize" : 4,
    "searchRadius" : 1.0,
    "walkingDirectionSameIfAngleLessOrEqual" : 45.0,
    "walkingDirectionCalculation" : "BY_TARGET_CENTER"
  },
  "source" : null,
  "targetIds" : [ ],
  "nextTargetListIndex" : 0,
  "isCurrentTargetAnAgent" : false,
  "position" : {
    "x" : 11.5,
    "y" : 11.0
  },
  "velocity" : {
    "x" : 0.0,
    "y" : 0.0
  },
  "freeFlowSpeed" : 1.3431000843372085,
  "followers" : [ ],
  "idAsTarget" : -1,
  "isChild" : false,
  "isLikelyInjured" : false,
  "psychologyStatus" : {
    "mostImportantStimulus" : null,
    "threatMemory" : {
      "allThreats" : [ ],
      "latestThreatUnhandled" : false
    },
    "selfCategory" : "TARGET_ORIENTED",
    "groupMembership" : "OUT_GROUP",
    "knowledgeBase" : {
      "knowledge" : [ ],
      "informationState" : "NO_INFORMATION"
    },
    "perceivedStimuli" : [ ],
    "nextPerceivedStimuli" : [ ]
  },
  "healthStatus" : null,
  "infectionStatus" : null,
  "groupIds" : [ ],
  "groupSizes" : [ ],
  "agentsInGroup" : [ ],
  "trajectory" : {
    "footSteps" : [ ]
  },
  "modelPedestrianMap" : null,
  "type" : "PEDESTRIAN"
} '''

def find_ids(data, ids):
  # Recursive function to find all 'id' values in the JSON structure
  if isinstance(data, dict):
      for key, value in data.items():
          if key == 'id':
              ids.append(value)
          else:
              find_ids(value, ids)
  elif isinstance(data, list):
      for item in data:
          find_ids(item, ids)


def add_pedestrian(args):
    # Parse the predefined pedestrian structure from JSON
    parsed_pedestrian = json.loads(pedestrian)

    # Update pedestrian attributes based on command line arguments
    parsed_pedestrian['attributes']['speedDistributionMean'] = args.speed
    parsed_pedestrian['position']['x'] = args.x
    parsed_pedestrian['position']['y'] = args.y
    parsed_pedestrian['targetIds']= args.targetID
    
    with open(args.scenario, 'r') as file:
        file_content = file.read()
        file_content_json = json.loads(file_content)

        # file_content_json['name'] = file_content_json['name'] + "_modified"
        # Append the new pedestrian to the 'dynamicElements' list
        file_content_json['scenario']['topography']['dynamicElements'].append(parsed_pedestrian)

        # Assign a new id to the pedestrian
        ids = []
        find_ids(file_content_json, ids)
        unique_ids = sorted(set(ids))
        id = unique_ids[-1] + 1
        parsed_pedestrian['attributes']['id'] = id

    # Specify the new file name
    # new_file_name = args.scenario.replace('.scenario', '_modified.scenario')

    with open(args.scenario, 'w') as file:
        # Write the modified JSON back to the scenario file
        json.dump(file_content_json, file, indent=0)
    print("Finished adding pedestrian. ID of the new pedestrian is "+str(id))

if __name__ == "__main__":
    # Create a command line argument parser
    parser = argparse.ArgumentParser(description='Add pedestrians to a scenario')
    
    # Define command line arguments
    parser.add_argument('-s', '--scenario', required=True, help='Scenario file')
    parser.add_argument('--x', type=float, required=True, help='Position of pedestrian x')
    parser.add_argument('--y', type=float, required=True, help='Position of pedestrian y')
    parser.add_argument('--speed', type=float, default=1, help='Mean speed Distribution of the pedestrian')
    parser.add_argument('--targetID', type=list, required=True, help='ID of its target')

    # Parse command line arguments
    args = parser.parse_args()

    # Call the function to add the pedestrian to the scenario
    add_pedestrian(args)
