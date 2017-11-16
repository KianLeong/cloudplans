package com.cloudplan.cloudplan;

import java.util.*;

public class CloudPlanApp {

    private static List<Feature> selectedfeatures=new ArrayList<Feature>();
    private static List<Plan> availableplan=new ArrayList<Plan>();

    private static double lowestcost=Double.POSITIVE_INFINITY;
    private static List<Plan> bestsolution=new ArrayList<Plan>();

    private static final int notCloserToSolution=0;
    private static final int closerToSolution=1;
    private static final int isSolution=2;

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();

        //Features users selected. You may set the features you want here
        selectedFeatures();
        //Populate list of plans available
        preparePlans();

        //Main logic is done here
        computeCheapestPlan();

        long endTime   = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println(totalTime);

    }

    private static void computeCheapestPlan() {
        //Looks like a backtracking problem
        //Constraint 1: there are at most N plans in a solution
        //Constraint 2: In a solution, it fulfils the features selected
        Stack<Plan> solution=new Stack<>();
        Map<String, Feature> featuremap = new HashMap<String, Feature>();
        for (Feature f : selectedfeatures) {
            featuremap.put(f.getName(), f);
        }

        //The recursive method that traverse through all the plans and backtracks accordingly
        buildCombi(solution, featuremap, 0);

        System.out.print("Best Plan:");

        StringBuilder sb = new StringBuilder();
        for (Plan plan:bestsolution){
            sb.append(plan.getName()).append(", ");
        }
        String result = sb.deleteCharAt(sb.length() - 2).toString();
        System.out.println(result);
        System.out.println("Cost:"+ lowestcost);



    }

    private static boolean buildCombi(Stack<Plan> solution, Map<String,Feature> constraints,int start){
        //Copy the constraints which is the current list of selected features that have been fulfilled into another temporary list
        Map<String, Feature> tempconstraints = new HashMap<String, Feature>();
        tempconstraints.putAll(constraints);

        //If solution is not empty, do a constraint check to see if solution is fulfilled or no improvement with the latest plan added
        if (!solution.isEmpty()) {
            //[Start] Update the checklist of feature selected that has been fulfilled
            int initialconstraintsize = tempconstraints.size();
            updateConstraint(solution.peek(), tempconstraints);
            int updatedconstraintsize = tempconstraints.size();
            //[End] Update the checklist of feature selected that has been fulfilled
            if  (updatedconstraintsize==0){
                double currcost=calculateTotalCost(solution);
                //If cost of solution is lower than cost of best solution so far, update solution and cost
                if (currcost<lowestcost){
                    lowestcost=currcost;
                    bestsolution.clear();
                    bestsolution.addAll(solution);
                }
                //return true to stop exploring other branches in the last item of current solution list since other branches are more expensive
                return true;
            }
            else if (initialconstraintsize==updatedconstraintsize){
                //return false to continue exploring other branches since this plan does not aid in enhancing solution
                return false;
            }
        }

        //This part utilizes the concept of backtracking to find different permutations for the available plans
        for (int i=start;i<availableplan.size();i++){
            //Add plan into the last item of the current solution list
            solution.push(availableplan.get(i));
            boolean solutionfound=buildCombi(solution, tempconstraints,i + 1);
            //Remove the current branch in the last item of the current solution list to traverse other branches
            solution.pop();

            //If solution is found no need other permutations down the list as they have higher cost
            if (solutionfound)
                break;

        }
        return false;


    }

    private static double calculateTotalCost(List<Plan> solution) {
        double total=0;
        for(Plan plan:solution){
            total+=plan.getCost();
        }
        return total;

    }

    private static void updateConstraint(Plan latestplan, Map<String, Feature> constraints) {
        for(Feature feature:latestplan.getFeatures()){
            if (constraints.containsKey(feature.getName())){
                constraints.remove(feature.getName());
            }
        }
    }

    private static void selectedFeatures(){
        Feature feature1=new Feature();
        feature1.setName("voice");

        Feature feature2=new Feature();
        feature2.setName("email");

        Feature feature3=new Feature();
        feature3.setName("archiving");

        Feature feature4=new Feature();
        feature4.setName("analytics");

        selectedfeatures.add(feature1);
        selectedfeatures.add(feature2);
        //selectedfeatures.add(feature3);
        //selectedfeatures.add(feature4);
    }

    private static void preparePlans(){
        Feature feature1=new Feature();
        feature1.setName("voice");

        Feature feature2=new Feature();
        feature2.setName("email");

        Feature feature3=new Feature();
        feature3.setName("archiving");

        Feature feature4=new Feature();
        feature4.setName("analytics");

        Feature feature5=new Feature();
        feature5.setName("sharing");

        /*
        Plan e1=new Plan();
        e1.setName("E1");
        e1.setCost(30);
        List<Feature> e1features=new ArrayList<>();
        e1features.add(feature1);
        e1features.add(feature2);
        e1.setFeatures(e1features);
        */

        Plan f1=new Plan();
        f1.setName("F1");
        f1.setCost(5);
        List<Feature> f1features=new ArrayList<>();
        f1features.add(feature1);
        //f1features.add(feature2);
        //f1features.add(feature4);
        f1.setFeatures(f1features);

        Plan g1=new Plan();
        g1.setName("G1");
        g1.setCost(10);
        List<Feature> g1features=new ArrayList<>();
       // g1features.add(feature1);
        g1features.add(feature2);
        //g1features.add(feature3);
        g1.setFeatures(g1features);

        Plan h1=new Plan();
        h1.setName("H1");
        h1.setCost(12);
        List<Feature> h1features=new ArrayList<>();
        h1features.add(feature1);
        h1features.add(feature2);
        h1.setFeatures(h1features);

       // availableplan.add(e1);
        availableplan.add(f1);
        availableplan.add(g1);
        availableplan.add(h1);

        //List is sorted according to price to optimize on the cheapest plans computation since only the cheapest plan combination is required
        Collections.sort(availableplan);

    }
}
