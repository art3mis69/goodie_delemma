package com.company;

import java.io.*;
import java.util.*;

public class Main {

    private static List<int[]> arrayListCombo = new ArrayList<>();
    public static void main(String[] args) {
        File file = new File("C:\\goodie-dilemma\\sample_input.txt");
        List<Integer> goodieValueList = new  ArrayList<Integer>();
        BufferedReader br = null;
        StringBuilder outputBuilder = new StringBuilder();
        outputBuilder.append("The goodies selected for distribution are:");
        outputBuilder.append("\t\n");
        int employeeCount = 0;
        HashMap goodieMap = new HashMap();
        try {
            br = new BufferedReader(new FileReader(file));
            int lineNum =0;

            String goodie;
            while ((goodie = br.readLine()) != null){
                if (lineNum == 0){
                    String employeeCountString = goodie.substring(goodie.indexOf(':')+1).trim();
                    employeeCount = Integer.parseInt(employeeCountString);
                }
                if (lineNum > 3) {
                    goodieMap = processGoodies(goodie, goodieMap);
                    String goodieValue = goodie.substring(goodie.indexOf(':')+1).trim();
                    goodieValueList.add(Integer.parseInt(goodieValue));
                }
                lineNum++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        int[] data = new int[goodieMap.size()];
        for (int i=0; i < goodieMap.size(); i++){
            data[i] =goodieValueList.get(i);
        }
        printCombination(data, goodieMap.size(), employeeCount );
        HashMap sortedGoodieMap = new HashMap();
        List<String> combinationsList = new ArrayList<String>();
        int leastDifference =0;
        for (int[] combination : arrayListCombo) {
            //System.out.println(Arrays.toString(combination));
            int[] sortedArray = sortGoodieArray(combination, employeeCount);
            int difference = sortedArray[3] - sortedArray[0];
            sortedGoodieMap.put(difference,combination);
            if (leastDifference == 0) leastDifference =difference;

            if (difference < leastDifference) leastDifference = difference;
        }

        int[] selectedGoodieSet = (int[]) sortedGoodieMap.get(leastDifference);
        for (int goodieValue: selectedGoodieSet){
            String value  = (String) goodieMap.get(String.valueOf(goodieValue));
            System.out.println(value);
            System.out.println(goodieValue);
            outputBuilder.append(value +": "+goodieValue+"\n");
        }
        outputBuilder.append("And the difference between the chosen goodie with highest price and the lowest price is "+leastDifference);
    writeOutputToFile(outputBuilder);
    }

    private static void writeOutputToFile(StringBuilder outputBuilder) {
        //File myObj = new File("C:\\goodie-dilemma\\sample_output.txt");
        FileWriter myWriter = null;
        try {
            myWriter = new FileWriter("C:\\goodie-dilemma\\sample_output.txt");
            myWriter.write(outputBuilder.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(myWriter != null){
                try {
                    myWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }

    private static int[] sortGoodieArray(int[] combination, int n) {
        for(int i = 0 ; i < combination.length;i++)
        {
            for(int j = i+1 ; j< combination.length;j++)
            {
                if(combination[i] > combination[j])
                {
                    int temp = combination[i];
                    combination[i] = combination[j];
                    combination[j] = temp;
                }
            }
        }
        return combination;
    }

    private static HashMap processGoodies(String goodie, HashMap goodieMap){
        String goodieName = goodie.substring(0,goodie.indexOf(':')).trim();
        String goodieValue = goodie.substring(goodie.indexOf(':')+1).trim();

        goodieMap.put(goodieValue, goodieName);
        return goodieMap;
    }

    static void combinationUtil(int[] arr, int[] data, int start,
                                int end, int index, int r )
    {

        // Current combination is ready to be printed, print it
        if (index == r)
        {
            int dataArray[]= new int[r];
            for (int j=0; j<r; j++) {
                System.out.print(data[j] + " ");
                dataArray[j] = data[j];
            }
            System.out.println("");
            arrayListCombo.add(dataArray);
            return;
        }

        // replace index with all possible elements. The condition
        // "end-i+1 >= r-index" makes sure that including one element
        // at index will make a combination with remaining elements
        // at remaining positions
        for (int i=start; i<=end && end-i+1 >= r-index; i++)
        {
            data[index] = arr[i];
            combinationUtil(arr, data, i+1, end, index+1, r);
        }
    }

    // The main function that prints all combinations of size r
    // in arr[] of size n. This function mainly uses combinationUtil()
    static void printCombination(int[] arr, int n, int r)
    {
        // A temporary array to store all combination one by one
        int data[]=new int[r];
        List<int[]> arrayListCombo = new ArrayList<>();
        // Print all combination using temprary array 'data[]'
        combinationUtil(arr, data, 0, n-1, 0, r);
       // return null;
    }
}
