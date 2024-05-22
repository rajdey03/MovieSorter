// -------------------------------------------------------
	// Assignment 2
	// Question: Main class
	// Written by: Huu Khoa Kevin Tran 40283037 && Pritthiraj Dey 40273416
	// For COMP 249 Section WW && QQ – Winter 2024
	// --------------------------------------------------------

	// Date of submission: Wednesday, March 27th 2024	
// -------------------------------------------------------
package assignment2;

import java.io.*;
import java.util.Scanner;

/**
The main class serves as the core component of a movie management system. 
Its primary tasks are reading movie data from CSV files, 
serializing and deserializing movie objects, handling exceptions related to invalid movie data, 
providing a user interface for navigating through movie records, and displaying movie details. 
Overall, the main class acts as the central controller, 
coordinating the flow of operations and ensuring the smooth functioning of the movie management system.
*/
public class Main {
	
	static private String[] validGenres = {"musical", "comedy", "animation", "adventure", "drama",
			"crime", "biography", "horror", "action", "documentary",
			"fantasy", "mystery", "sci-fi", "family", "romance",
			"thriller", "western"};
	
	public static void main(String[] args) {
		
		
		// part 1's manifest file
		String part1_manifest = "part1_manifest.txt";
		
		// part 2's manifest file
		String part2_manifest = do_part1(part1_manifest);
		
		// part 3's manifest file
		String part3_manifest = do_part2(part2_manifest);// serialize
		
		Movie[][] all_movies = do_part3(part3_manifest);// deserialize
		
		navigateMovies(all_movies);												  // and navigate
	}

	/**
     * This method performs the first part of the movie data processing.
     * It reads data from the input file, validates each record, handles exceptions, and writes valid records to genre-specific CSV files.
     *
     * @param part1 The file path of the input data.
     * @return The file path of the generated manifest file.
     */
	private static String do_part1(String part1) {
		
			//Deletion of previous files for update
			File badMovies = new File("bad_movie_records.txt");
			badMovies.delete();
			
			for (String element : validGenres) {
				File genreCSV = new File(element+".csv");
				genreCSV.delete();
			}

		// Generation of part2_manifest.txt
		try {
			
			PrintWriter output = new PrintWriter("part2_manifest.txt");
			// Writes each genre, which will be followed by a .csv extenion, into the part2_manifest text file
			for (String element : validGenres) {
				output.println(element+".csv");
			}
			output.close();
			
		} catch (IOException e) {
			e.getMessage();
			e.printStackTrace();
		}
		
		try {
			// Created CSV files for each genre based on the part2_manifest text file
			Scanner inputPart2 = new Scanner(new FileInputStream("part2_manifest.txt"));
			while (inputPart2.hasNextLine()) {
				(new File(inputPart2.nextLine())).createNewFile();	
			}
		} catch (IOException e) {
			e.getMessage();
			e.printStackTrace();
		}
		
		try {
			// Process part1_manifest text file which contains movie records
			Scanner inputPart1 = new Scanner(new FileInputStream(part1));
			while (inputPart1.hasNextLine()) {
				String record = null,fileName = null;
				int pos = 0;
				boolean valid = true;
				
				// Reads each line which contains the file name for movie records
				Scanner inputMovies = new Scanner(new FileInputStream(fileName = inputPart1.nextLine()));
				// Loops through every movie of each input file
				while (inputMovies.hasNextLine()) {
					valid = true;
					int count = 0;
					record = inputMovies.nextLine();
					++pos;
					
					// Checks for missing quotes in the record
					if (record.contains("\"")) {
						
						boolean insideQuotes = false;
						// Iterate through each character in the record
						for (int i = 0; i < record.length(); i++) {
							if (record.charAt(i) == '"') {
								// Toggle insideQuotes variable when encountering quotation marks
								insideQuotes = !insideQuotes;			
							}
							if (insideQuotes && record.charAt(i) == ',') {
								// Counts the number of commas inside quotation marks
								++count;
							}
							
						}
						
						// If insideQuotes is still true, it means there are missing closing quotation marks
						if (insideQuotes == true) {
							try {
								// Throws exception into the bad records file if missing quotes are found
								throw new MissingQuotesException("Missing Quotes - Syntax Error",record,fileName,pos);
							} catch (MissingQuotesException e) {
								e.printStackTrace();
								valid = false;
							}
						}
					}
					
					// Splits the records by comma and check the number of fields
					String[] records = record.split(",");
					// Check if the number of fields in the record is not equal to 10
					if (records.length-1-count != 9) {
						// If the number of fields is greater than 10, throw an exception for excess fields
						if (records.length-1-count > 9) {
							try {
								throw new ExcessFieldsException("Excess Fields - Syntax Error",record,fileName,pos);
							} catch (ExcessFieldsException e) {
								e.printStackTrace();
								valid = false;
							}
						} 
						// If the number of fields is less than 10, throw an exception for missing fields
						else {
							try {
								throw new MissingFieldsException("Missing Fields - Syntax Error",record,fileName,pos);
							} catch (MissingFieldsException e) {
								e.printStackTrace();
								valid = false;
							}
						}
					}
					
					
					// If the record passes the basic syntax checks, program proceeds with additional validations
					if (valid) {
						
						// Temporary adjustment to handle quotes within fields
						boolean insideQuotes = false;
						
						for (int i = 0; i < record.length(); i++) {
							String tempRecord;
							if (record.charAt(i) == '"') {
								// Toggle insideQuotes variable when encountering quotation marks
								insideQuotes = !insideQuotes;			
							}
							if (insideQuotes && record.charAt(i) == ',') {
								// Replace commas inside quotes with a '#'
								tempRecord = new String(record.substring(0,i)+"#"+record.substring(1+i));
								record=tempRecord;
							}
						}
						// Split the record again after adjusting
						records = record.split(",");
						// Restore the commas that were replaced with '#'
						for (String element : records) {
							element = element.replaceAll("#", ",");
						}
						record = record.replaceAll("\\#", ",");

						// Check for missing fields after the temporary adjustment
						try {
							for (String element : records) {
								if (element.isEmpty()) {
									throw new MissingFieldsException("Missing Fields - Syntax Error",record,fileName,pos);
								}
							}
						} catch (MissingFieldsException e) {
							e.printStackTrace();
							valid = false;
						}
						
						if (valid) {
							// Remove quotes from year and check its validity
							records[0] = records[0].replaceAll("\"","");
							for (int i = 0; i < records[0].length(); i++) {
								if (!Character.isDigit(records[0].charAt(i))) {
									records[0] = records[0].substring(0,i)+records[0].substring(1+i);
								}
							}
							// Check if the year is between 1990 and 1999, otherwise throw an exception
							if (Integer.parseInt(records[0]) > 1999 || Integer.parseInt(records[0]) < 1990) {
								try {
									throw new BadYearException("Year is not between 1990 and 1999 - Semantic Error",record,fileName,pos);
								} catch (BadYearException e) {
									valid = false;
									e.printStackTrace();
								}
							}
							
							try {
								// Check if the duration is between 30 and 300 minutes, otherwise throw an exception
								if (Integer.parseInt(records[2].replaceAll("\"","")) > 300 || Integer.parseInt(records[2].replaceAll("\"","")) < 30) {
	
										throw new BadDurationException("Duration is not between 30 and 300 - Semantic Error",record,fileName,pos);
								}
							} catch (BadDurationException e) {
								e.printStackTrace();
								valid = false;
							}
							try {
								// Check if the score is between 0 and 10, otherwise throw an exception
								if (Double.parseDouble(records[5].replaceAll("\"","")) > 10 || Double.parseDouble(records[5].replaceAll("\"","")) < 0) {
									
									throw new BadScoreException("Score is negative or is higher than 10 - Semantic Error",record,fileName,pos);
									
								}
							} catch (BadScoreException e) {
								valid = false;
								e.printStackTrace();
							}
							
							// Remove quotes from rating and checks if the rating is valid, otherwise throw an exception
							records[4] = records[4].replaceAll("\"","");
							if (!(records[4].equals("PG") || records[4].equals("Unrated") || records[4].equals("G") || records[4].equals("R") || records[4].equals("PG-13") || records[4].equals("NC-17"))) {
								try {
									// Throw exception for invalid rating
									throw new BadRatingException("Rating is invalid - Semantic Error",record,fileName,pos);
								} catch (BadRatingException e) {
									valid = false;
									e.printStackTrace();
								}
							}
							
							// Check if the genre is valid, otherwise throw an exception
							boolean found = false;
							records[3] = records[3].replaceAll("\"","");
							for (String element : validGenres) {
								if (records[3].equalsIgnoreCase(element)) {
									found = true;
									break;
								}
							}
							if (found == false) {
								try {
									// Throw exception for invalid genre
									throw new BadGenreException("Genre is invalid - Semantic Error",record,fileName,pos);
								} catch (BadGenreException e) {
									valid = false;
									e.printStackTrace();
								}
							}
						}
					}
					// If the record is valid, write it to the corresponding genre CSV file
					if (valid == true) {
						PrintWriter output = new PrintWriter(new FileOutputStream(records[3].toLowerCase()+".csv", true));
						output.println(record);
						output.close();
					}
				}
				inputMovies.close();
			}
			inputPart1.close();
			
		} catch (IOException e) {
			e.getMessage();
			e.printStackTrace();
		}
		
		return "part2_manifest.txt";
	}
	
	/**
	 * Processes the second part of movie data processing.
	 * Reads genre CSV files, serializes movie data, and generates a manifest file.
	 *
	 * @param part2 The file path of the manifest file containing genre CSV filenames.
	 * @return The file path of the generated manifest file for part 3.
	 */
	private static String do_part2(String part2) {
        String part3_manifest = "part3_manifest.txt";

        try {
            Scanner info = new Scanner(new File(part2));
            PrintWriter manifest_writer = new PrintWriter(part3_manifest);
            
         // Iterate through each line in the part_2 manifest file
            while (info.hasNextLine()) {
            	// Get the file genre from the current line
                String file_genre = info.nextLine();
                // Extract the CSV file name from the file genre
                String csv_file = file_genre.substring(0, file_genre.lastIndexOf('.'));
                // Define the name for the corresponding binary file
                String binary_file = csv_file + ".ser";
                
                // Read movies from the CSV file and serialize them into the corresponding binary file
                Movie[] movies = readMoviesFromCSV(file_genre);
                serializeMovies(movies, binary_file);
                
                // Write the name of the binary file to the part 3 manifest file
                manifest_writer.println(binary_file);
            }
            
            // Close the writer and scanner
            manifest_writer.close();
            info.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return part3_manifest;
    }
	
	/**
	 * Reads movie data from a CSV file and converts it into an array of Movie objects.
	 *
	 * @param csv_file The file path of the CSV file containing movie data.
	 * @return An array of Movie objects containing the movie data.
	 */
	private static Movie[] readMoviesFromCSV(String csv_file) {
	    Movie[] movies = null;
	    try {
	    	// Open the CSV file for reading
	        Scanner info = new Scanner(new File(csv_file));
	        int count = 0;
	        while (info.hasNextLine()) {
	            info.nextLine();
	            count++;
	        }
	        info.close();
	        
	        // Reopen the CSV file for reading
	        info = new Scanner(new File(csv_file));
	        movies = new Movie[count];
	        String record;
	        
	        // Iterate through each line in the CSV file
	        for (int i = 0; i < count; i++) {
	        	record = info.nextLine();
	        	
	        	// Temporary adjustment to handle commas within double quotes
				boolean insideQuotes = false;
				
				for (int j = 0; j < record.length(); j++) {
					String tempRecord;
					if (record.charAt(j) == '"') {
						insideQuotes = !insideQuotes;			
					}
					if (insideQuotes && record.charAt(j) == ',') {
						tempRecord = new String(record.substring(0,j)+"#"+record.substring(1+j));
						record=tempRecord;
					}
				}
				String[] parts = record.split(",");
				// Replace temporary adjustments
				for (String element : parts) {
					element = element.replaceAll("#", ",");
				}
				record = record.replaceAll("\\#", ",");
				
				// Convert score to double, handling empty strings
	            double score = parts[5].isEmpty() ? 0 : Double.parseDouble(parts[5].replaceAll("\"", ""));
	            
	            // Create a new Movie object and store it in the movies array
	            movies[i] = new Movie(Integer.parseInt(parts[0]), parts[1], Integer.parseInt(parts[2]),
	                    parts[3], parts[4], score, parts[6], parts[7], parts[8], parts[9]);
	        }
	        info.close();
	    } catch (FileNotFoundException e) {
	        e.printStackTrace();
	    } catch (NumberFormatException e) {
	        e.printStackTrace();
	        System.err.println("Error: Invalid double value found in CSV file.");
	    }
	    return movies;
	}

	/**
	 * Serializes an array of Movie objects and writes them to a binary file.
	 *
	 * @param movies         An array of Movie objects to be serialized.
	 * @param binaryFileName The file path of the binary file to which the movies will be written.
	 */
    private static void serializeMovies(Movie[] movies, String binaryFileName) {
        try {
            FileOutputStream text_out = new FileOutputStream(binaryFileName);
            ObjectOutputStream bin_out = new ObjectOutputStream(text_out);
            bin_out.writeObject(movies);
            bin_out.close();
            bin_out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Processes the third part of movie data processing.
     * Deserializes movie data from binary files and organizes them by genre.
     *
     * @param part3 The file path of the manifest file containing serialized movie filenames.
     * @return A 2D array containing deserialized movie data organized by genre.
     */
    private static Movie[][] do_part3(String part3) {
        try {
            // Read part3_manifest.txt
            Scanner info = new Scanner(new File(part3));
            int num_genres = validGenres.length;
            Movie[][] all_movies = new Movie[num_genres][];

            // Iterate over each line in part3_manifest.txt
            for (int i = 0; i < num_genres; i++) {
                String binary_file = info.nextLine();

                // Deserialize the binary file
                FileInputStream fis = new FileInputStream(binary_file);
                ObjectInputStream ois = new ObjectInputStream(fis);
                Movie[] movies = (Movie[]) ois.readObject();

                // Assign the deserialized movies to the corresponding genre index
                all_movies[i] = movies;

                ois.close();
                fis.close();
            }
            info.close();

            // Now all_movies contains deserialized arrays of Movie objects for each genre
            return all_movies;

        } catch (ClassNotFoundException cnfe) {
        	cnfe.printStackTrace();
        }
        
        catch (IOException ioe) {
            ioe.printStackTrace();
        }
        
        return null;
    }
    
    static int pos_index = 0;
    
    /**
     * Navigates through the movie data stored in memory.
     *
     * @param all_movies A 2D array containing deserialized movie data organized by genre.
     */
    private static void navigateMovies(Movie[][] all_movies) {
    	// Initialize the index of the movie array and the position index to navigate
    	Scanner info = new Scanner(System.in);
    	int array_index = 0;
    	pos_index = 0;
    	
    	while(true) {
    		// Display main menu options
    		System.out.println("-------------------------------");
            System.out.println("Main Menu");
            System.out.println("-------------------------------");
            System.out.println("s Select a movie array to navigate");
            System.out.println("n Navigate " + validGenres[array_index] + " movies (" + all_movies[array_index].length + " records)");
            System.out.println("x Exit");
            System.out.println("-------------------------------");
            System.out.print("Enter Your Choice: ");
            
            String choice = info.nextLine().toLowerCase();
            
            switch(choice) {
            	case "s":
            		// Display genre sub-menu to select a movie array
            		System.out.println("-------------------------------");
            		System.out.println("\tGenre Sub-Menu");
            		System.out.println("-------------------------------");
            		
            		// Display available genres and their movie counts
            		for (int i = 0; i<validGenres.length;i++) {
            			System.out.println((i+1)+" "+validGenres[i]+"                  ("+all_movies[i].length+" movies)");
            		}
            		System.out.println(validGenres.length+1+" Exit");
            		System.out.println("-------------------------------");
            		System.out.println("Enter your choice: ");
            		int genre_choice = info.nextInt();
            		info.nextLine();
            		
            		if (genre_choice >= 1 && genre_choice <= validGenres.length) {
            			// Update the array index to the user's desired genre
            			array_index = genre_choice - 1;
            			pos_index = 0;
            			
            		}
            		
            		else if (genre_choice == validGenres.length+1){
            			break;
            		}
            		
            		else {
            			System.out.println("Invalid choice. Try Again.");
            		}
            		
            		break;
            		
            	case "n":
            		System.out.println("Navigating "+validGenres[array_index]+" movies ("+all_movies[array_index].length+")");
            		System.out.println("Enter your choice: ");
            		int nav_choice = info.nextInt();
            		info.nextLine();
            		
            		if (nav_choice == 0) {
            			break;
            		}
            		while(nav_choice != 0) {
            			int printLimit = 0;
            			boolean current = false;
            			
            			// Navigate backward in movie records
            			if (nav_choice<0) {
            				 // Check if reaching the beginning of file
            				if (pos_index-(Math.abs(nav_choice)-1)<0) {
            					printLimit = pos_index;
            					System.out.println("BOF has been reached");
            				} else {
            					printLimit = pos_index-(Math.abs(nav_choice)-1);
            				}
            				
            				// Display movie records from printLimit to pos_index
            				for (int i = printLimit;i<=pos_index;i++) {
            					// Check if the current record is being displayed
            					current = false;
            					if (i == pos_index) {
            						current = true;
            					}
            					displayMovieRecord(all_movies[array_index][i],current);
            				}
            				// Update the position index
            				pos_index = printLimit;
            			}
            			// Navigate forward in movie records
            			if (nav_choice>0) {
            				// Check if reaching the end of file
            				if (pos_index-(Math.abs(nav_choice)-1)>=all_movies[array_index].length) {
            					printLimit = all_movies[array_index].length-1;
            				} 
            				
            				else {
            					printLimit = pos_index + (Math.abs(nav_choice)-1);
            				}
            				
            				// Display movie records from pos_index to printLimit
            				for (int i = pos_index;i<=printLimit;i++) {
            					 // Check if the current record is being displayed
            					current = false;
            					if (i == pos_index) {
            						current = true;
            					}
            					displayMovieRecord(all_movies[array_index][i],current);
            				}
            				// Update the position index
            				pos_index = printLimit;
            			}
            			System.out.println("Enter your choice: ");
                		nav_choice = info.nextInt();
                		info.nextLine();
                    }                	
            		break;
    			  		
            	case "x":
            		System.out.println("Program is shutting down. Thank you for using MoviesManager");
            		return;
            	default:
            		System.out.println("Invalid choice. Please try again.");
            }
    	}
    }
    
    /**
     * Displays the details of a movie record.
     *
     * @param movie   The Movie object containing the movie details.
     * @param current A boolean indicating if the movie is the currently selected one.
     */
    private static void displayMovieRecord(Movie movie, boolean current) {
        // Display movie details, borders the current movie differently to the other movies displayed
    	if (current) {
    		System.out.println ("vvvvvvvvvvvv(CURRENT)vvvvvvvvvvvv");
    	} else {
    		 System.out.println("---------------------------------");
    	}
        System.out.println("Year: " + movie.getYear());
        System.out.println("Title: " + movie.getTitle());
        System.out.println("Duration: " + movie.getDuration());
        System.out.println("Genre: " + movie.getGenre());
        System.out.println("Rating: " + movie.getRating());
        System.out.println("Score: " + movie.getScore());
        System.out.println("Director: " + movie.getDirector());
        System.out.println("Actor 1: " + movie.getActor1());
        System.out.println("Actor 2: " + movie.getActor2());
        System.out.println("Actor 3: " + movie.getActor3());
        if (current) {
    		System.out.println ("^^^^^^^^^^^^(CURRENT)^^^^^^^^^^^^");
    	} else {
    		 System.out.println("---------------------------------");
    	}
    }
    
}


