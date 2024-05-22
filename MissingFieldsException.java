// -------------------------------------------------------
	// Assignment 2
	// Question: MissingFieldsException class
	// Written by: Huu Khoa Kevin Tran 40283037 && Pritthiraj Dey 40273416
	// For COMP 249 Section WW && QQ – Winter 2024
	// --------------------------------------------------------

	// Date of submission: Wednesday, March 27th 2024
	
// -------------------------------------------------------
package assignment2;

import java.io.*;

/**
The `MissingFieldsException` class handles cases where movie records contain empty fields. 
It extends the standard `Exception` class and provides constructors to set error details like the type of exception, the problematic record, file, and position. 
Upon instantiation, it logs these details to "bad_movie_records.txt". 
This class enhances error handling by aiding in the identification and management of movie records with missing fields.
 */
public class MissingFieldsException extends Exception{
	
	/**
     * Constructs a new MissingFieldsException with a default message.
     */
	public MissingFieldsException() {
		super("Missing Fields");
	}
	
	/**
     * Constructs a new MissingFieldsException with a custom message and logs the exception details to a file.
     *
     * @param type      The type of exception.
     * @param record    The record causing the exception.
     * @param inputFile The input file where the bad record is located.
     * @param pos       The position of the bad record within the file.
     */
	public MissingFieldsException(String type, String record, String inputFile, int pos) {
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
