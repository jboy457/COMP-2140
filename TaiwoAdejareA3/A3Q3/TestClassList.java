/*
* COMP 2140 SECTION A02
* INSTRUCTOR: Dr. Lauren Himbeault
* STUDENT NUMBER: 7980132
* ASSIGNMENT: Assignment 3
* QUESTION: Question 
*
* PURPOSE: HashTables
*/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class TestClassList {
    // COMMANDS LIST
    final static char CREATE = 'C';
    final static char PRINT = 'P';
    final static char DEEP_COPY = 'D';
    final static char ENROLL = 'E';
    final static char UNENROLL = 'U';
    final static char ALSO_ENROLL = 'A';
    final static char NOT_ENROLL = 'N';

    // School that contains the specified array of class
    static ClassList[] school;

    public static void main(String[] args) {
        try {
            // Check if fileName is passed in as arg.
            if (args.length < 1) {
                throw new FileNotFoundException();
            }
            File file = new File(args[0]);
            Scanner scnr = new Scanner(file);

            // Get Number of class for school.
            int numOfClass = Integer.parseInt(scnr.nextLine());
            school = new ClassList[numOfClass];

            // While command exist runCommnad.
            while (scnr.hasNextLine()) {
                String line = scnr.nextLine();
                runCommand(line);
            }

            scnr.close();
        } catch (NumberFormatException err) {
            System.out.println("Invalid length of School (Class list Array)");
        } catch (FileNotFoundException err) {
            System.out.println("File not found !!");
        }

        System.out.println("Program terminated succesfully.");
    }

    /**
     * Run commands in file.
     * 
     * @param command The commnad to run.
     */
    public static Boolean runCommand(String command) {
        Boolean isSuccess = false;
        if (command.length() > 0) {
            switch (command.charAt(0)) {
                case CREATE:
                    System.out.printf("=============== Create command - %s ==================\n", command);
                    runCreate(command);
                    break;
                case PRINT:
                    System.out.printf("=============== Print command - %s =================\n", command);
                    runPrint(command);
                    break;
                case DEEP_COPY:
                    System.out.printf("=============== Deep Copy command - %s =================\n", command);
                    runDeepCopy(command);
                    break;
                case ENROLL:
                    System.out.printf("=============== Enroll command - %s =================\n", command);
                    runErroll(command);
                    break;
                case UNENROLL:
                    System.out.printf("=============== Un enroll command - %s =================\n", command);
                    runUnEnroll(command);
                    break;
                case ALSO_ENROLL:
                    System.out.printf("=============== Also Enroll command - %s =================\n", command);
                    runAlsoEnroll(command);
                    break;
                case NOT_ENROLL:
                    System.out.printf("=============== Not Also Enroll command - %s =================\n", command);
                    runNotEnroll(command);
                    break;
                default:
                    break;
            }
        }
        return isSuccess;
    }

    /**
     * Executes the specified command for each student not currently enrolled in
     * other class.
     *
     * @param command The command to execute on non-enrolled students.
     */
    public static void runNotEnroll(String command) {
        try {
            // Split command into attributes
            String[] cmdAttr = command.split(" ");

            // Parse attribute to index(int)
            int firstClass = Integer.parseInt(cmdAttr[1]);
            int secondClass = Integer.parseInt(cmdAttr[2]);
            int toClass = Integer.parseInt(cmdAttr[3]);

            // Enroll "firstClass" student who are not enrolled in "secondClasss" to
            // "toClass" class
            school[toClass] = school[firstClass].notAlsoEnrolledIn(school[secondClass]);
        } catch (IndexOutOfBoundsException err) {
            System.out.println("Class not found in School.");
        } catch (Exception err) {
            System.out.println("Error trying to not erroll class list.");
        }
    }

    /**
     * Executes the specified command for each student currently enrolled in other
     * class.
     *
     * @param command The command to execute on enrolled students.
     */
    public static void runAlsoEnroll(String command) {
        try {
            // Split command into attributes
            String[] cmdAttr = command.split(" ");

            // Parse attribute to index(int)
            int firstClass = Integer.parseInt(cmdAttr[1]);
            int secondClass = Integer.parseInt(cmdAttr[2]);
            int toClass = Integer.parseInt(cmdAttr[3]);

            school[toClass] = school[firstClass].alsoEnrolledIn(school[secondClass]);
        } catch (IndexOutOfBoundsException err) {
            System.out.println("Class not found in School.");
        } catch (Exception err) {
            System.out.println("Error trying to enroll class list.");
        }
    }

    /**
     * Executes the specified command to unenroll students currently enrolled in
     * another class.
     *
     * @param command The command containing enrollment indexes for operation.
     */

    public static void runUnEnroll(String command) {
        try {
            // Spilt String into array
            String[] cmdAttr = command.split(" ");

            int toErollIndex = Integer.parseInt(cmdAttr[1]);
            int fromEnrollIndex = Integer.parseInt(cmdAttr[2]);

            // Calls the unenrollAll method to unenroll students from specified classes
            school[toErollIndex].unenrollAll(school[fromEnrollIndex]);
        } catch (IndexOutOfBoundsException err) {
            System.out.println("Class not found in School.");
        } catch (Exception err) {
            System.out.println("Error trying to run Un erroll class list.");
        }
    }

    /**
     * Executes the specified command to enroll students from one class into
     * another.
     *
     * @param command The command containing enrollment indexes for operation.
     */
    public static void runErroll(String command) {
        try {
            String[] cmdAttr = command.split(" ");
            int toErollIndex = Integer.parseInt(cmdAttr[1]);
            int fromEnrollIndex = Integer.parseInt(cmdAttr[2]);

            school[toErollIndex].enrollAll(school[fromEnrollIndex]);
        } catch (IndexOutOfBoundsException err) {
            System.out.println("Class not found in School.");
        } catch (Exception err) {
            System.out.println("Error trying to run erroll class list.");
        }
    }

    /**
     * Executes the specified command to create a deep copy of one class and store
     * it in another.
     *
     * @param command The command containing class indexes for copying operation.
     */
    public static void runDeepCopy(String command) {
        try {
            String[] cmdAttr = command.split(" ");
            int fromClassIndex = Integer.parseInt(cmdAttr[1]);
            int toClassIndex = Integer.parseInt(cmdAttr[2]);

            ClassList fromClass = school[fromClassIndex];

            school[toClassIndex] = fromClass.duplicate();
        } catch (IndexOutOfBoundsException err) {
            System.out.println("Class not found in School.");
        } catch (Exception err) {
            System.out.println("Error trying to deep copy class list.");
        }
    }

    /**
     * Executes the specified command to print the details of a class.
     *
     * @param command The command containing the index of the class to print.
     */
    public static void runPrint(String command) {
        try {
            String[] cmdAttr = command.split(" ");
            int classIndex = Integer.parseInt(cmdAttr[1]);
            ClassList classToPrint = school[classIndex];
            classToPrint.print();
        } catch (IndexOutOfBoundsException err) {
            System.out.println("Class not found in School.");
        } catch (Exception err) {
            System.err.println(err.getMessage());
            System.out.println("Error trying to print class list.");
        }
    }

    /**
     * Executes the specified command to create a new class list from a file and
     * store it at a given index.
     *
     * @param command The command containing the file path and class index for the
     *                new class list.
     */
    public static void runCreate(String command) {
        try {
            String classFile;
            int classIndex;
            String[] cmdAttr = command.split(" ");

            if (cmdAttr.length > 2) {
                classFile = cmdAttr[1];
                classIndex = Integer.parseInt(cmdAttr[2]);
                try {
                    ClassList classList = new ClassList();
                    File file = new File(classFile);
                    Scanner scnr = new Scanner(file);

                    while (scnr.hasNextLine()) {
                        try {
                            String line = scnr.nextLine();
                            String[] stdData = line.split(",");
                            int id = Integer.parseInt(stdData[1]);
                            classList.enroll(new Student(stdData[0], id, stdData[2], stdData[3]));
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid Student Id.");
                        } catch (Exception err) {
                            System.err.println(err.getMessage());
                            System.out.println("Invalid Student data.");
                        }
                    }

                    school[classIndex] = classList;

                    scnr.close();
                } catch (FileNotFoundException err) {
                    System.err.println("File not found");
                }
            }
        } catch (Exception e) {
            System.err.println("Error Trying create class list.");
        }
    }
}
