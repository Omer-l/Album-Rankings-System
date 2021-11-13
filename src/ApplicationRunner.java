
import Music.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.lang.StringIndexOutOfBoundsException;

public class ApplicationRunner
{

	private static Scanner input = new Scanner(System.in);

	private static boolean exit = false;

	private static AlbumFileReader albumReader = new AlbumFileReader();

	public static void main(String[] args)
	{
		//load the data, get all the albums, fill there attributes etc...
		albumReader.loadData();

		//while the user has not exitted...
		while (exit == false)
		{
			prompt();
			//Take it as a string, parse later.
			String promptInput = input.next();
			//display based on input.
			outputAfterPrompt(promptInput);
		}
	}
//		albumReader.getAlbum().get(0).tracks.get(0);

	private static void prompt()
	{
		System.out.print("\n\n"
			+ "List albums............1\n"
			+ "Select album...........2\n"
			+ "Search titles..........3\n"
			+ "Exit...................0\n\n"
			+ "Enter choice:> ");
	}

	private static void outputAfterPrompt(String userInput)
	{
		try
		{
			int setIn = Integer.parseInt(userInput);

			if (setIn == 1)
			{
				listAlbums();
			} else if (setIn == 2)
			{
				System.out.print("\nEnter album rank from list [1 - " + albumReader.getAlbum().size() + " :> ");
				String albumPick = input.next();

				try
				{
					int albumPickToInt = Integer.parseInt(albumPick);

					if (albumPickToInt > 0 && albumPickToInt <=  albumReader.getAlbum().size())
					{
						selectAlbum(albumPickToInt);
					} else
					{
						System.out.println("\nInput is not valid");
						pause1second();
						outputAfterPrompt(userInput);
					}
				} catch (NumberFormatException nfe)
				{
					System.out.println("\nENTER A DIGIT!");
					pause1second();
					outputAfterPrompt(userInput);
				}
			} else if (setIn == 3)
			{
				Scanner searchInput = new Scanner(System.in);

				System.out.print("Enter search word or phrase > ");
				String userIn = searchInput.nextLine();
				System.out.print("\n");

				//if string is not sensible...
				try
				{
					searchSongTitles(userIn);
				} catch (StringIndexOutOfBoundsException stobe)
				{
					System.out.print("\nPlease enter a sensible search!");
					pause1second();
				}
			} else if (setIn == 0)
			{
				exit = true;
			} //else inputted number is beyond limit
			else
			{
				System.out.println("\nInput is not valid");
				pause1second();
			}

		} //if it input was not a number (after parsing)
		catch (NumberFormatException nfe)
		{
			System.out.println("\nENTER ONE OF THE OPTIONS.");
			pause1second();
		}
	}
	//pause time for 1 second

	private static void pause1second()
	{
		try
		{
			Thread.sleep(1000);
		} catch (InterruptedException ie)
		{
			System.out.println("INTERRUPTED EXCEPTION");
		}
	}

	private static void listAlbums()
	{
		System.out.print("\n");
		for (int i = 0; i < 104; i++)
		{
			System.out.print("-");
		}
		System.out.print("\n");

		System.out.printf("%-1s%-6s%-1s%-52s%-1s%-27s%-1s%-6s%-1s%-7s%-1s\n", "|", " Rank ", "|", " Title", "|", " Artist", "|", " Year", "|", " Sales", "|");

		for (int i = 0; i < 104; i++)
		{
			System.out.print("-");
		}
		System.out.print("\n");

		for (int i = 0; i < albumReader.getAlbum().size(); i++)
		{
			String tmpSales = "" + albumReader.getAlbum().get(i).getSales();
			System.out.printf("%-1s%6s%-1s%-52s%-1s%-27s%-1s%-6s",
				"|", i + 1 + " ",
				"|", " " + albumReader.getAlbum().get(i).getTitle(),
				"|", " " + albumReader.getAlbum().get(i).getArtist(),
				"|", " " + albumReader.getAlbum().get(i).getYear());

			try
			{
				if (tmpSales.length() >= 7)
				{

					double sales = (double) albumReader.getAlbum().get(i).getSales() / 1000000;
					tmpSales = sales + "";
					System.out.printf("%2s", "| ");

					//To even out the border, convert to string and see length of number.
					StringBuilder salesS = new StringBuilder();
					salesS.append(sales + "");
					if (salesS.charAt(salesS.length() - 1) == '0')
					{
						salesS.setLength(0);
						salesS.append(tmpSales.substring(0, tmpSales.length() - 1));
						if (salesS.charAt(salesS.length() - 1) == '.')
						{
							salesS.setLength(0);
							salesS.append(tmpSales.substring(0, tmpSales.length() - 2));
						}
					}
					if (salesS.length() > 3)
					{
						System.out.print(salesS + "M");
					} else
					{
						System.out.print(salesS + "M ");
					}
					System.out.print(" |\n");

				} else
				{
					tmpSales = tmpSales.substring(0, 3);
					System.out.printf("%2s", "| ");
					System.out.print(tmpSales + "K");
					System.out.print("  |\n");
				}
			} catch (StringIndexOutOfBoundsException oobe)
			{
				System.out.print("\nSomething went wrong with parsing the sales.\n");
			}

		}

		for (int i = 0; i < 104; i++)
		{
			System.out.print("-");
		}
		System.out.print("\n");
	}

	private static void selectAlbum(int albumNumber)
	{
		System.out.print("\n");
		int index = albumNumber - 1;
		System.out.print(albumReader.getAlbum().get(index).toString());

		//display tracks
		System.out.print("\nTrack list:\n");
		for (int i = 0; i < 99; i++)
		{
			System.out.print("-");
		}
		System.out.print("\n");

		System.out.printf("%-1s%-4s%-1s%-82s%-1s%-4s%-1s%-4s%-1s", "|", "No.", "|", "Title", "|", "Mins", "|", "Secs", "|");

		System.out.print("\n");
		for (int i = 0; i < 99; i++)
		{
			System.out.print("-");
		}
		System.out.print("\n");

		for (int i = 0; i < albumReader.getAlbum().get(index).getTracks().size(); i++)
		{
			String[] splittedTrack = albumReader.getAlbum().get(index).getTracks().get(i).split("[(]");
			String title = splittedTrack[0];
			String[] trackTime = splittedTrack[1].split("[:]");
			String mins = trackTime[0];
			String secs = "";
			//parse seconds
			for (int j = 0; j < trackTime[1].length(); j++)
			{
				if (Character.isDigit(trackTime[1].charAt(j)))
				{
					secs += trackTime[1].charAt(j);
				}
			}
			System.out.printf("%-1s%-4s%-1s%-82s%-1s%4s%-1s%4s%-1s\n", "|", i + 1, "|", title, "|", mins, "|", secs, "|");
		}

		for (int i = 0; i < 99; i++)
		{
			System.out.print("-");
		}
		System.out.print("\n");
	}

	private static void searchSongTitles(String input)
	{
		input = input.toLowerCase().trim();
		String userInputFirstCharToUpperCase = "";

		//split spaces to make each word first letter upper case
		String[] userInputFirstCharToUpperCaseSplit = input.split(" ");
		if (input.length() > 1)
		{
			for (int i = 0; i < userInputFirstCharToUpperCaseSplit.length; i++)
			{
				userInputFirstCharToUpperCaseSplit[i] = userInputFirstCharToUpperCaseSplit[i].trim();
				//words which are always lower case first letter (must be past the first word of the title)
				if ((userInputFirstCharToUpperCaseSplit[i].equals("for") || userInputFirstCharToUpperCaseSplit[i].equals("the") || userInputFirstCharToUpperCaseSplit[i].equals("or") || userInputFirstCharToUpperCaseSplit[i].equals("a") || userInputFirstCharToUpperCaseSplit[i].equals("at") || userInputFirstCharToUpperCaseSplit[i].equals("to") || userInputFirstCharToUpperCaseSplit[i].equals("in")) && i > 0)
				{
					userInputFirstCharToUpperCase += userInputFirstCharToUpperCaseSplit[i] + " ";
					continue;
				}
				userInputFirstCharToUpperCaseSplit[i] = Character.toUpperCase(userInputFirstCharToUpperCaseSplit[i].charAt(0)) + userInputFirstCharToUpperCaseSplit[i].substring(1);
				userInputFirstCharToUpperCase += userInputFirstCharToUpperCaseSplit[i] + " ";
			}
		} else //if it's just one letter, just make a capital letter.
		{
			userInputFirstCharToUpperCaseSplit[0] = Character.toUpperCase(input.charAt(0)) + "";
		}
		//trim unneccessary spaces
		userInputFirstCharToUpperCase = userInputFirstCharToUpperCase.trim();

		//ArrayList for checking whether the next song is from an already found artist's song.
		ArrayList<String> searchMatchedArtists = new ArrayList<>();

		//is the track from the same artist?
		boolean trackFromSameArtist = false;

		//go through each album
		for (int i = 0; i < albumReader.getAlbum().size(); i++)
		{
			//reset boolean
			trackFromSameArtist = false;

			//search each album's track
			for (int j = 0; j < albumReader.getAlbum().get(i).getTracks().size(); j++)
			{
				//split time from track name
				String track = albumReader.getAlbum().get(i).getTracks().get(j).split("[(]")[0];

				//To see if track is from the same artist.
				for (String s : searchMatchedArtists)
				{
					if (s.contains(albumReader.getAlbum().get(i).getArtist()))
					{
						trackFromSameArtist = true;
					}
				}

				//search with all letters lower case.
				if (track.indexOf(input) > -1)
				{
					String tmp = track.substring(0, track.indexOf(input))
						+ input.toUpperCase()
						+ track.substring(track.indexOf(input) + input.length());
					//output search results
					//If it's not from the same artist, then print as the next albums..
					if (trackFromSameArtist == false)
					{
						System.out.print("\n----");
						System.out.print("\n" + "Artist (" + albumReader.getAlbum().get(i).getArtist() + ") Album (" + albumReader.getAlbum().get(i).getTitle() + ")");
						System.out.print("\nMatching song title(s):\n-----");
						System.out.print("\nTrack " + (j + 1) + ": " + tmp.replaceAll(input, input.toUpperCase()));
						searchMatchedArtists.add(albumReader.getAlbum().get(i).getArtist());
					} else
					{
						System.out.print("\nTrack " + (j + 1) + ": " + tmp.replaceAll(input, input.toUpperCase()));
					}

					//Seach with first letter as a capital
				} else if (track.indexOf(userInputFirstCharToUpperCase) > -1)
				{
					String tmp = track.substring(0, track.indexOf(userInputFirstCharToUpperCase))
						+ userInputFirstCharToUpperCase.toUpperCase()
						+ track.substring(track.indexOf(userInputFirstCharToUpperCase) + userInputFirstCharToUpperCase.length());
					//output search results
					//If it's not from the same artist, then print as the next albums..
					if (trackFromSameArtist == false)
					{
						System.out.print("\n-----");
						System.out.print("\n" + "Artist (" + albumReader.getAlbum().get(i).getArtist() + ") Album (" + albumReader.getAlbum().get(i).getTitle() + ")");
						System.out.print("\nMatching song title(s):\n-----");
						System.out.print("\nTrack " + (j + 1) + ". " + tmp.replaceAll(userInputFirstCharToUpperCase, userInputFirstCharToUpperCase.toUpperCase()));

						searchMatchedArtists.add(albumReader.getAlbum().get(i).getArtist());
					} else
					{
						System.out.print("\nTrack " + (j + 1) + ". " + tmp.replaceAll(userInputFirstCharToUpperCase, userInputFirstCharToUpperCase.toUpperCase()));
					}

				}
			}
		}
	}
}
