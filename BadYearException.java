// -------------------------------------------------------
	// Assignment 2
	// Question: BadYearException class
	// Written by: Huu Khoa Kevin Tran 40283037 && Pritthiraj Dey 40273416
	// For COMP 249 Section WW && QQ – Winter 2024
	// --------------------------------------------------------

	// Date of submission: Wednesday, March 27th 2024
	
// -------------------------------------------------------
package assignment2;

import java.io.*;
/**
The `BadNameException` class is a custom exception used to handle cases where a movie's year released is invalid. 
It extends the standard `Exception` class and provides constructors for creating instances of this exception. 
This exception class aids in identifying and handling instances where movie year released does not meet the required criteria, 
enhancing the robustness of the movie management system.
*/
public class BadYearException extends Exception{
	/**
     * Constructs a new BadYearException with a default message.
     */
	public BadYearException() {
		super("Invalid Year");
	}
	
	/**
     * Constructs a new BadYearException with a custom message and logs the exception details to a file.
     *
     * @param type      The type of exception.
     * @param record    The record causing the exception.
     * @param inputFile The input file where the bad record is located.
     * @param pos       The position of the bad record within the file.
     */
	public BadYearException(String type, String record, String inputFile, int pos) {
		try {
			// Create a PrintWriter to write to "bad_movie_records.txt" file, in append mode
			PrintWriter output = new PrintWriter(new FileOutputStream("bad_movie_records.txt", true));
			
			// Write exception details to the file, such as the type of exception
			output.println(type);
			// The record that's causing the exception
			output.println(record);
			// Which input file the bad record is located
			output.println(inputFile);
			// The position of the record within the file
			output.println(pos);
			output.println();
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
