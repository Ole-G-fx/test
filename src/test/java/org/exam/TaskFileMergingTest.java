package org.exam;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.exam.excep.WrongException;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

import static org.exam.TaskFileMerging.removeDuplicates;

/**
 * Unit test for simple App.
 */
public class TaskFileMergingTest
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public TaskFileMergingTest(String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( TaskFileMergingTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        assertTrue( true );
    }

    public void testRemoveDuplicates() {
        int nums[] = new int[] {-7,-5,-5,-2,-2,0,0,2,2,2,3,5,5,6,8,9,9,10};
        System.out.print(Arrays.toString(nums));
        int k = removeDuplicates(nums);
        System.out.println("<" + k + "> ");
        System.out.print(Arrays.toString(nums));
        writeFileTest();
    }

    public void writeFileTest() {
        Long capacity = 1024L*1024*128*13L;
        BufferedWriter writerFile = null;
        BufferedWriter writerFile1 = null;
        try {

            writerFile = new BufferedWriter(new FileWriter("fileName.txt"));
            writerFile1= new BufferedWriter(new FileWriter("fileName1.txt"));
        } catch (IOException e) {

            new WrongException("Файл не найден или недоступен '" + "fileName" +
                    "' системное сообщение:\n" + e.getMessage());
        }
        for (Long i = 1L; i < capacity; i++) {
            Long out;
            Long out1;
            try {

                out = (long) (Math.random()*6) + i;
                writerFile.write(String.valueOf(out));
                writerFile.newLine();      //9223372036854775808
                out1 = (long) (Math.random()*6) + i;
                writerFile1.write(String.valueOf(out1));
                writerFile1.newLine();
            } catch (IOException e) {

                new WrongException("Файл не найден или недоступен '" +
                        "' системное сообщение:\n" + e.getMessage());
            }
        }
        try {
            writerFile.close();
            writerFile1.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        TaskFileMerging.main(new String[]{"-i", "out1.txt", "in1.txt", "in2.txt", "in3.txt", "in4.txt", "ins4.txt", "1.txt", "2.txt", "fileName.txt", "fileName1.txt"});
    }
}
