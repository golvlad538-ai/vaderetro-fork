package net.minecraft.src;
// NOTE: moved implementation to net.minecraft.src.johnmillmod.TileEntityJohnMillRenderer

import org.lwjgl.opengl.GL11;

public class TileEntityJohnMillRenderer extends TileEntitySpecialRenderer {
    private ModelJohnMill model = new ModelJohnMill();

    public void renderTileEntityJohnMillAt(TileEntityJohnMill te, double x, double y, double z, float partial) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float)x + 0.5F, (float)y + 0.5F, (float)z + 0.5F);
        // Apply facing from block metadata like signs/stairs
        int meta = te.getBlockMetadata() & 3;
        float yaw = 0.0F;
        if(meta == 0) yaw = 90.0F;      // east
        if(meta == 1) yaw = 0.0F;       // south
        if(meta == 2) yaw = 270.0F;     // west
        if(meta == 3) yaw = 180.0F;     // north
        GL11.glRotatef(-yaw, 0.0F, 1.0F, 0.0F);

        // Smooth rotation using partial ticks to avoid rippling/jitter
        float angle = te.prevRotation + (te.rotation - te.prevRotation) * partial;
        GL11.glRotatef(angle, 0.0F, 0.0F, 1.0F);
        GL11.glScalef(3.5F, 3.5F, 3.5F);
        this.bindTextureByName("/mill/fcwindmillent.png");
        this.model.renderAll();
        GL11.glPopMatrix();
    }

    public void renderTileEntityAt(TileEntity var1, double var2, double var4, double var6, float var8) {
        this.renderTileEntityJohnMillAt((TileEntityJohnMill)var1, var2, var4, var6, var8);
    }
}



