import java.util.HashMap;
import java.util.Map;

public class Simulation {
    private double predictedProbability;
    private double actualProbability;
    private int numberOfTrials;
    HashMap<Integer, Boolean> trials = new HashMap<>();
    HashMap<Integer, Double> avgTrials = new HashMap<>();

    public Simulation(int numOfTrials, double prob){
        this.predictedProbability = prob;
        this.numberOfTrials = numOfTrials;
    }

    public double getProbability(){ // should return: "num%"?
        return predictedProbability;
    }

    public double getActualProbability(){
        double probability = (double) numberOfTrues()/numberOfTrials;
        double rounded = Math.round(probability * 100.00) / 100.00;
        return rounded;
    }

    public void setProbability(double prob){ //int as in percentage
        this.predictedProbability = prob;
    }

    public int getTrials(){
        return numberOfTrials;
    }

    public void setTrials(int numOfTrials){
        this.numberOfTrials = numOfTrials;
    }

    //true = covid, false = no covid :)
    public void run(){
        for (int i = 1; i <= numberOfTrials; i++){

            double rand = Math.random();
            // (rand <= predictedProbability) ? trials.put(i, true) : trials.put(i, false);
            if(rand <= predictedProbability){
                trials.put(i, true);
            }
            else{
                trials.put(i, false);
            }
        }
    }

    public void clear(){
        trials.clear();
    }

    public int numberOfTrues(int range){ // used for averageTrial
        int trues = 0;
        for (int i = 1; i <= range; i++) {
            if(trials.get(i) == true){
                trues++;
            }
        }
        return trues;
    }

    public int numberOfTrues(){ // calculates all of it
        int trues = 0;
        for(Map.Entry<Integer, Boolean> e : trials.entrySet())  {
            if(e.getValue() == true){
                trues++;
            }
        }
        return trues;
    }


    public void averageTrial(){
        for (int i = 1; i <= numberOfTrials; i++){
            int trues = numberOfTrues(i);
            double average = (double) trues/i;
            double rounded = Math.round(average * 100.0) / 100.0;
            avgTrials.put(i, rounded);
        }
    }

    public void printHashMaps(){
        System.out.println("Trial Results");
        for (Map.Entry<Integer, Boolean> e : trials.entrySet()){
            System.out.println(e.getKey() + " " + e.getValue());
        }

        System.out.println("Average Results");
        for (Map.Entry<Integer, Double> e : avgTrials.entrySet()){
            System.out.println(e.getKey() + " " + e.getValue());
        }
    }

    public HashMap<Integer, Double> getAverageTrials() {
        return this.avgTrials;
    }

}

