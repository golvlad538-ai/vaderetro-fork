package net.minecraft.src;
// NOTE: moved implementation to net.minecraft.src.johnmillmod.ModelJohnMill

public class ModelJohnMill extends ModelBase {
    public ModelRenderer hub;
    public ModelRenderer blade1;
    public ModelRenderer blade2;
    public ModelRenderer blade3;
    public ModelRenderer blade4;

    public ModelJohnMill() {
        this.hub = new ModelRenderer(0, 0);
        this.hub.addBox(-3, -3, -1, 6, 6, 2, 0.0F);

        this.blade1 = new ModelRenderer(0, 8);
        this.blade1.addBox(-2, -24, -0, 4, 48, 1, 0.0F);

        this.blade2 = new ModelRenderer(10, 8);
        this.blade2.addBox(-2, -24, -0, 4, 48, 1, 0.0F);
        this.blade2.rotateAngleZ = (float)Math.toRadians(90);

        this.blade3 = new ModelRenderer(20, 8);
        this.blade3.addBox(-2, -24, -0, 4, 48, 1, 0.0F);
        this.blade3.rotateAngleZ = (float)Math.toRadians(180);

        this.blade4 = new ModelRenderer(30, 8);
        this.blade4.addBox(-2, -24, -0, 4, 48, 1, 0.0F);
        this.blade4.rotateAngleZ = (float)Math.toRadians(270);
    }

    public void renderAll() {
        this.hub.render(0.0625F);
        this.blade1.render(0.0625F);
        this.blade2.render(0.0625F);
        this.blade3.render(0.0625F);
        this.blade4.render(0.0625F);
    }
}



