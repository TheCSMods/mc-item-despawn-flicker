package thecsdev.itemdespawnflicker.client.gui.widget;

import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.util.math.MatrixStack;
import thecsdev.itemdespawnflicker.IDF;

public abstract class PassiveWidget extends ClickableWidget
{
	public PassiveWidget(int x, int y, int width, int height) { super(x, y, width, height, IDF.lt("")); }
	
	@Override public void appendNarrations(NarrationMessageBuilder builder) {}
	@Override public boolean changeFocus(boolean lookForwards) { return false; }
	@Override protected boolean clicked(double mouseX, double mouseY) { return false; }
	
	@Override
	public abstract void render(MatrixStack matrices, int mouseX, int mouseY, float delta);
}