package thecsdev.itemdespawnflicker.client.gui.screen;

import static thecsdev.itemdespawnflicker.IDF.tt;
import static thecsdev.itemdespawnflicker.config.IDFConfig.ENABLED;
import static thecsdev.itemdespawnflicker.config.IDFConfig.saveProperties;

import java.awt.Color;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import thecsdev.itemdespawnflicker.client.gui.widget.FillWidget;
import thecsdev.itemdespawnflicker.client.gui.widget.StringWidget;

public class IDFConfigScreen extends Screen
{
	// ==================================================
	public static final int BG_COLOR = new Color(0, 0, 0, 120).getRGB();
	public static final int OL_COLOR = new Color(255, 255, 255, 50).getRGB();
	// --------------------------------------------------
	public final Screen parent;
	// --------------------------------------------------
	public ButtonWidget btn_enabled;
	public ButtonWidget btn_done;
	// --------------------------------------------------
	private int x1, y1, x2, y2, y3;
	// ==================================================
	public IDFConfigScreen(Screen parent)
	{
		super(tt("itemdespawnflicker"));
		this.parent = parent;
	}
	// ==================================================
	@Override
	public void init()
	{
		//calculate bg coordinates
		this.x1 = 10;
		this.y1 = 10;
		this.x2 = this.width - 10;
		this.y2 = this.height - 45;
		this.y3 = this.height - 40;
		
		//add bg children
		addDrawableChild(new FillWidget(x1 - 1, y1 - 1, x2 + 1, y2 + 1, OL_COLOR));
		addDrawableChild(new FillWidget(x1, y1, x2, y2, BG_COLOR));
		
		//add entries children
		addDrawableChild(new StringWidget(this.width / 2, y1 + 15, tt("itemdespawnflicker.gui.config.general"), textRenderer)
				.setCentered());
		
		addDrawableChild(new StringWidget(x1 + 10, y1 + 40, tt("itemdespawnflicker.config.ENABLED"), textRenderer));
		addDrawableChild(btn_enabled = new ButtonWidget(x2 - 160, y1 + 35, 150, 20,
			ENABLED ? tt("itemdespawnflicker.config.ENABLED.true") : tt("itemdespawnflicker.config.ENABLED.false"),
			arg0 ->
			{
				ENABLED = !ENABLED;
				arg0.setMessage(ENABLED ?
						tt("itemdespawnflicker.config.ENABLED.true") :
						tt("itemdespawnflicker.config.ENABLED.false"));
			}));
		
		addDrawableChild(btn_done = new ButtonWidget((this.width / 2) - 75, y3 + 5, 150, 20, tt("gui.done"), arg0 -> close()));
	}
	// --------------------------------------------------
	@Override
	public void close()
	{
		client.setScreen(this.parent);
		saveProperties();
	}
	// ==================================================
	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta)
	{
		renderBackground(matrices);
		super.render(matrices, mouseX, mouseY, delta);
	}
	// ==================================================
}