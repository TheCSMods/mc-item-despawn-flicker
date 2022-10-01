package thecsdev.itemdespawnflicker.client.gui.widget;

import java.awt.Color;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

public class StringWidget extends PassiveWidget
{
	// ==================================================
	public final Text text;
	public final TextRenderer textRenderer;
	// --------------------------------------------------
	public int color;
	public boolean isCentered;
	// ==================================================
	public StringWidget(int x, int y, Text text, TextRenderer tr)
	{
		super(x, y, tr.getWidth(text.getString()), tr.fontHeight);
		this.text = text;
		this.textRenderer = tr;
		
		this.color = Color.white.getRGB();
		this.isCentered = false;
	}
	public StringWidget setCentered() { this.isCentered = true; return this; }
	public StringWidget setColored(int color) { this.color = color; return this; }
	// ==================================================
	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta)
	{
		if(isCentered) drawCenteredText(matrices, textRenderer, text, x, y, color);
		else drawTextWithShadow(matrices, textRenderer, text, x, y, color);
	}
	// ==================================================
}