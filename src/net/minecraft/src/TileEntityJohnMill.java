package net.minecraft.src;
// NOTE: moved implementation to net.minecraft.src.johnmillmod.TileEntityJohnMill

public class TileEntityJohnMill extends TileEntity {
    public float rotation;
    public float prevRotation;
    public float rotationSpeed = 1.0F;

    public void updateEntity() {
        this.prevRotation = this.rotation;
        this.rotation += this.rotationSpeed;
        while(this.rotation >= 360.0F) {
            this.rotation -= 360.0F;
        }
    }

    public void readFromNBT(NBTTagCompound var1) {
        super.readFromNBT(var1);
        this.rotation = var1.getFloat("Rot");
        this.prevRotation = var1.getFloat("PrevRot");
    }

    public void writeToNBT(NBTTagCompound var1) {
        super.writeToNBT(var1);
        var1.setFloat("Rot", this.rotation);
        var1.setFloat("PrevRot", this.prevRotation);
    }
}



