package Music;

import java.util.ArrayList;

public class Album implements Comparable<Album>
{

	String title;
	String artist;
	String year;
	int sales;
	ArrayList<String> tracks = new ArrayList<>();

	public String getTitle()
	{
		return title;
	}

	public String getArtist()
	{
		return artist;
	}

	public String getYear()
	{
		return year;
	}

	public Integer getSales()
	{
		return sales;
	}

	public ArrayList<String> getTracks()
	{
		return tracks;
	}

	//for sorting
	@Override
	public int compareTo(Album albumToCompareWith)
	{
		return this.getSales().compareTo(albumToCompareWith.getSales());
	}

	@Override
	public String toString()
	{
		StringBuilder outputAlbumDetails = new StringBuilder();

		outputAlbumDetails.append("Album title:			" + this.getTitle() + "\n");

		outputAlbumDetails.append("Artist:				" + this.getArtist() + "\n");
		outputAlbumDetails.append("Year of release:		" + this.getYear() + "\n");
		String tmpSales = "" + getSales();
		try
		{
			if (tmpSales.length() >= 7)
			{

				double sales = (double) getSales() / 1000000;
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

				outputAlbumDetails.append("Sales to date:			" + salesS + "M\n");
				System.out.print(" |\n");

			} else
			{
				tmpSales = tmpSales.substring(0, 3);
				outputAlbumDetails.append("Sales to date:			" + tmpSales + "K\n");
			}
		} catch (StringIndexOutOfBoundsException oobe)
		{
			System.out.print("\nSomething went wrong with parsing the sales.");
		}

		return outputAlbumDetails.toString();
	}
}
