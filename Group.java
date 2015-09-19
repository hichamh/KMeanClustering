import java.util.ArrayList;


public class Group 
{
	public ArrayList<Point> list;
	public Point centroid;
	Group()
	{
		list = new ArrayList<Point>();
		centroid = new Point(0,0);
	}
	public void computeCentroid()
	{
		float cx = 0;
		float cy = 0;
		for(int i=0;i<list.size();i++)
		{
			
			cx = cx + list.get(i).x;
			cy = cy + list.get(i).y;
		}
		
		cx = cx/list.size();
		
		cy = cy/list.size();
		//cx = (float) (cx - Math.floor(cx)<0.5?Math.floor(cx):Math.floor(cx)+1);
		//cy = (float) (cy - Math.floor(cy)<0.5?Math.floor(cy):Math.floor(cy)+1);
		centroid.x = !Float.isNaN(cx)?cx:centroid.x;
		centroid.y = !Float.isNaN(cy)?cy:centroid.y;
	}
}
