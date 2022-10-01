package thecsdev.itemdespawnflicker.client.gui.widget;

import net.minecraft.client.util.math.MatrixStack;

public class FillWidget extends PassiveWidget
{
	// ==================================================
	public final int color;
	private final int x2, y2;
	// ==================================================
	public FillWidget(int x, int y, int x2, int y2, int color)
	{
		super(x, y, x2 - x, y2 - y);
		this.color = color;
		this.x2 = x2;
		this.y2 = y2;
	}
	// ==================================================
	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta)
	{
		fill(matrices, x, y, x2, y2, color);
	}
	// ==================================================
}