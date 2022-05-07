package org.example.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SampleTest {

    @Test
    public void mergeTwoSortedArrays() {
        int[] input1 = {11, 12, 13, 14, 15, 16, 17, 18, 19};
        int[] input2 = {1, 2, 3, 4, 5, 6, 20};
        int[] expected = {1, 2, 3, 4, 5, 6, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};

        int[] output = new int[input1.length + input2.length];

        int firsArrayIdx = 0;
        int secondArrayIdx = 0;
        int outputCounter = 0;

        while(firsArrayIdx < input1.length && secondArrayIdx < input2.length) {
            if(input1[firsArrayIdx] <= input2[secondArrayIdx]) {
                output[outputCounter] = input1[firsArrayIdx];
                firsArrayIdx++;
            } else {
                output[outputCounter] = input2[secondArrayIdx];
                secondArrayIdx++;
            }
            outputCounter++;
        }

        //Copy the remaining
        int remaining = input1.length - 1 - firsArrayIdx;
        for(int idx = 0; idx <= remaining; idx++) {
            output[outputCounter++] = input1[firsArrayIdx++];
        }

        remaining = input2.length - 1 - secondArrayIdx;
        for(int idx = 0; idx <= remaining; idx++) {
            output[outputCounter++] = input2[secondArrayIdx++];
        }

        for(int i=0;  i < expected.length; i++) {
            Assertions.assertEquals(expected[i], output[i]);
        }
    }
}
