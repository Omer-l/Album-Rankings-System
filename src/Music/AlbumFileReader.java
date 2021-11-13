package Music;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class AlbumFileReader
{

	//ArrayList of albums, ready for adding albums with their attributes.
	ArrayList<Album> album = new ArrayList<Album>();

	//album file path, can stay private.
	private final String albumFilePath = System.getProperty("user.dir") + File.separator + "albums.txt";

	public void loadData()
	{
		//object of the loaded file
		File dataObject = new File(albumFilePath);

		//Try to access file
		try
		{
			Scanner fileRead = new Scanner(dataObject);
			//booleans for seeing whether it is a new album or new track.
			boolean isNewAlbum = true;
			boolean isTrack = false;

			//albumNumber is -1, ready to start counting index.
			int albumNumber = -1;

			//Loop to read file.
			while (fileRead.hasNextLine())
			{
				try
				{
					//store line into currentLine variable
					String currentLine = fileRead.nextLine();

					if (isNewAlbum == true)
					{

						//album header validation.
						if (currentLine.split("[:]").length == 5)
						{
							albumToAdd(currentLine);
							albumNumber++;
						} else
						{
							System.out.print("\nAlbum header number " + (albumNumber + 2)
								+ " was unable to parse and therefore not added to the list.\n"
								+ "The format should be: <rank/number><title><artist><year><sales>\n");
						}

						//Then set newAlbum to false as there is tracks next
						isNewAlbum = false;
						//The next lines are tracks until it is a dash so set isTracks to true.
						isTrack = true;
					} //If the index is a dash 
					else if (currentLine.charAt(0) == '-')
					{
						isNewAlbum = true;
						isTrack = false;
					} //if the proceeding lines are tracks.
					else if (isTrack == true)
					{
						album.get(albumNumber).tracks.add(currentLine);
					}
				} catch (IndexOutOfBoundsException ioobe)
				{
					System.out.print("\nSomething went wrong with parsing the text file...\n");
				}
			}
		} //If file was not found...
		catch (FileNotFoundException fnfe)
		{
			System.out.println("FILE WAS NOT FOUND!");
		}

		//Sort albums in reverse order.
		Collections.sort(album, Collections.reverseOrder());
	}

	public ArrayList<Album> getAlbum()
	{
		return album;
	}

	private void albumToAdd(String currentLine)
	{
		String[] splittedCurrentLine = currentLine.split("[:]");
		Album albumToAdd = new Album();

		for (int i = 0; i < splittedCurrentLine.length; i++)
		{
			String line = splittedCurrentLine[i];
			switch (i)
			{
				case 1:
					albumToAdd.title = line;
					break;
				case 2:
					albumToAdd.artist = line;
					break;
				case 3:
					albumToAdd.year = line;
					break;
				case 4:
					double sales = Double.parseDouble(line.substring(0, line.length() - 1));
					if (line.charAt(line.length()-1) == 'M')
					{
						sales = Double.parseDouble(line.substring(0, line.length() - 1)) * 1000000;
					} else if(line.charAt(line.length()-1) == 'K')
					{
						sales = sales = Double.parseDouble(line.substring(0, line.length() - 1)) * 1000;
					} else
					{
						System.out.print("Please check the album: " + albumToAdd.getTitle() + ". The total sales should end in 'M' or 'K'.");
					}
					//to take away the decimal places, convert to int
					albumToAdd.sales = (int) sales;
					album.add(albumToAdd);
					break;
			}
		}
	}
}
