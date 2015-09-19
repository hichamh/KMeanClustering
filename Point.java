
public class Point 
{
	public float x;
	public float y;
	public float distance;
	public int newGroup;
	public int oldGroup;
	public int indexInGroup;
	public static int pointCount;
	
	 Point(int X,int Y)
	{
		x = X;
		y = Y;
		distance = 99999999.99999f;
		newGroup = -1;
		oldGroup = -1;
		pointCount++;
	}
	float computeDistance(Point centroid)
	{
		float dx = x - centroid.x;
	
		float dy = y - centroid.y;
		
		distance = (float) Math.sqrt(Math.pow(dx, 2)+Math.pow(dy, 2));
		return distance;
	}
	boolean findShortestDistance(Group[] g)
	{
		float min = distance;
		int index = newGroup;
		boolean changes = false;
		for(int i=0;i<g.length;i++)
		{
			float newDistance = computeDistance(g[i].centroid) ;
			
			if(newDistance<min)
			{
				min = newDistance;
				index = i;
				changes = true;
			}
		}
		distance = min;
		oldGroup = newGroup;
		newGroup = index;
		int index2 = g[oldGroup].list.indexOf(this);
		Point p = g[oldGroup].list.remove(index2);
		
		g[newGroup].list.add(p);
		
		return changes;
	}
}
