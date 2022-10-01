package thecsdev.itemdespawnflicker.util;

public class Vector3 extends Vector2
{
	public int z;
	
	public Vector3() { this(0,0,0); }
	public Vector3(int x, int y, int z)
	{
		super(x, y);
		this.z = z;
	}
}