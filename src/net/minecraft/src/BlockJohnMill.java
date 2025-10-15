package net.minecraft.src;
// NOTE: moved implementation to net.minecraft.src.johnmillmod.BlockJohnMill

public class BlockJohnMill extends BlockContainer {
    public BlockJohnMill(int id, int textureIndex) {
        super(id, textureIndex, Material.wood);
        this.setHardness(2.0F).setResistance(5.0F).setStepSound(soundWoodFootstep).setBlockName("johnMill");
    }

    public boolean isOpaqueCube() {
        return false;
    }

    public boolean renderAsNormalBlock() {
        return false;
    }

    public int getRenderType() {
        return -1;
    }

    protected TileEntity getBlockEntity() {
        return new TileEntityJohnMill();
    }

    public void onBlockPlacedBy(World var1, int var2, int var3, int var4, EntityLiving var5) {
        int var6 = MathHelper.floor_double((double)(var5.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
        if(var6 == 0) {
            var1.setBlockMetadataWithNotify(var2, var3, var4, 2);
        }

        if(var6 == 1) {
            var1.setBlockMetadataWithNotify(var2, var3, var4, 1);
        }

        if(var6 == 2) {
            var1.setBlockMetadataWithNotify(var2, var3, var4, 3);
        }

        if(var6 == 3) {
            var1.setBlockMetadataWithNotify(var2, var3, var4, 0);
        }
    }
}



