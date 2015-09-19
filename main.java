/*
Name: Hicham Hammouche
Due Date: 10/14/2014
Submitted Date:
Professor: Dr. T. Phillips
Problem: Kmean clustering.
Steps:
step0:prepare everything
step1:randomly partition the poinset into k-sets
step2:for every group we compute the centroid
step3:for every point we compute distancs from point coords to centroids
step4: we relabel each point to the group that has the shortest centroid distance
step5: repeat 2 to 4 until no point changes label
Input: args[1] : KmeanData.txt args[2] : output.txt args[3] : k
output : output.txt
*/
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Scanner;


public class main {

	public static void actualizeMap(String[][] map,String[][] ogmap,Group groups[])
	{
		for(int i=0;i<ogmap.length;i++)
		{
			for(int j=0;j<ogmap[i].length;j++)
			{
				map[i][j]= ogmap[i][j];
			}
		}
		for(int i=0;i<groups.length;i++)
		{
			if(map[(int)Math.rint(groups[i].centroid.x)][(int)Math.rint(groups[i].centroid.y)] == "X")
			{
				
				map[(int)Math.rint(groups[i].centroid.x)][(int)Math.rint(groups[i].centroid.y)] =String.valueOf(i);
			}
			else
			{
				map[(int)Math.rint(groups[i].centroid.x)][(int)Math.rint(groups[i].centroid.y)] = String.valueOf(i);
			}
		}
	}
	public static void printMap(String[][] map,PrintWriter writer)
	{
		System.out.println("X for a regular point. Centroids are represented by the group number");
		writer.println("X for a regular point. C for a centroide and O for both.");
		for(int i=0;i<map.length;i++)
		{
			for(int j=0;j<map[i].length;j++)	
			{
				if(i == 0 && j == 0)
				{
					System.out.print("   ");
					writer.print("   ");
				}
				else if(i==0 || j==0)	
				{	
					if(i==0)
					{
						System.out.print(j<10?j-1 + "  ":j-1 + " ");
						writer.print(j<10?j-1 + "  ":j-1 + " ");
					}
					else
					{
						System.out.print(i<10?i-1 + "  ":i-1 + " ");
						writer.print(i<10?i-1 + "  ":i-1 + " ");
					}
				}
				else
				{
					System.out.print(map[i-1][j-1] + "  ");
					writer.print(map[i-1][j-1] + "  ");
				}
			}
			System.out.println(" ");
			writer.println(" ");
		}
	}
	public static void printGroups(Group groups[],PrintWriter writer)
	{
		for(int i=0;i<groups.length;i++)
		{
			System.out.println("Group : "+i);
			writer.println("Group : "+i);
			for(int j=0;j<groups[i].list.size();j++)
			{
				System.out.print("(" + groups[i].list.get(j).x + ", " + groups[i].list.get(j).y + ")");
				writer.print("(" + groups[i].list.get(j).x + ", " + groups[i].list.get(j).y + ")");
			}
			System.out.println(" ");
			writer.println(" ");
		}
	}
	public static void main(String[] args) 
	{
		//prep everything
		int k = Integer.parseInt(args[1]);
		Scanner inputFile = null;
		PrintWriter writer = null;
		int sizeX;
		int sizeY;
		String[][] map;
		String[][] ogMap;
		Group groups[];
		ArrayList<Point> points = new ArrayList<Point>();
		try 
		{
			writer = new PrintWriter("output.txt", "UTF-8");
		} 
		catch (FileNotFoundException | UnsupportedEncodingException error) 
		{
			
			error.printStackTrace();
		}
		try 
		{
			inputFile = new Scanner(new FileReader(args[0]));
		} 
		catch (FileNotFoundException error) 
		{
			error.printStackTrace();
		}
		sizeX = inputFile.nextInt();
		sizeY = inputFile.nextInt();
		System.out.println("map size : " + sizeX+ "," + sizeY);
		System.out.println("Clustering points into " + k + " Groups");
		writer.println("map size : " + sizeX+ "," + sizeY);
		writer.println("Clustering points into " + k + " Groups");
		//init map for printing
		map = new String[sizeX][sizeY];
		ogMap = new String[sizeX][sizeY];
		for(int i=0;i<sizeX;i++)
			for(int j=0;j<sizeY;j++)
			{
				map[i][j]= " ";
				ogMap[i][j]= " ";
			}
		//putting the Xs on the empty board
		while(inputFile.hasNextInt())
		{
			int x = inputFile.nextInt();
			int y = inputFile.nextInt();
			map[x][y] = "X";
			ogMap[x][y] = "X";
			points.add(new Point(x,y));
		}
		
		//print map
		printMap(map,writer);
		
		groups = new Group[k];
		
		//init groups
		for(int i=0;i<k;i++)
			groups[i] = new Group();
		
		//assign points to each group
		for(int i=0;i<points.size();i++)
		{
			groups[i%k].list.add(points.get(i)); 
			points.get(i).newGroup = i%k;
		}
		
		printGroups(groups,writer);
		
		boolean changes = false;
		
		do
		{
			System.out.println(" ");
			System.out.println(" ");
			writer.println(" ");
			writer.println(" ");
			changes = false;
			
			for(int i=0;i<k;i++)
			{
				groups[i].computeCentroid();
				System.out.print(" Group " + i + " centroid is : ("+ groups[i].centroid.x + " , " + groups[i].centroid.y + ") ." );
				writer.print(" Group " + i + " centroid is : ("+ groups[i].centroid.x + " , " + groups[i].centroid.y + ") ." );

			}
			
			System.out.println(" ");
			writer.println(" ");
			for(int i=0;i<points.size();i++)
			{
			 
				Point p = points.get(i);
			 
			 boolean temp = p.findShortestDistance(groups);
			 
			 if(temp)
				 changes = true;
			 
			}
			
			printGroups(groups,writer);
			
			actualizeMap(map, ogMap,groups);
			
			printMap(map,writer);
			
			for(int i=0;i<sizeX*2;i++)
			{
				System.out.print("-");
				writer.print("-");
			}
			
		}
		while(changes);
		
		inputFile.close();
		writer.close();
	}
}
