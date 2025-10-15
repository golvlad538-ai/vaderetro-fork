package net.minecraft.src;

import java.util.Random;

public abstract class BlockFluid extends Block {

	private static final Random random = new Random();

	protected BlockFluid(int var1, Material var2) {
		super(
				var1,
				(var2 == Material.lava ? 14 : (var2 == Material.water ? 12 : 10)) * 16 + 13,
				var2
		);
		float var3 = 0.0F;
		float var4 = 0.0F;
		this.setBlockBounds(0.0F + var4, 0.0F + var3, 0.0F + var4, 1.0F + var4, 1.0F + var3, 1.0F + var4);
		this.setTickOnLoad(true);
	}

	public int colorMultiplier(IBlockAccess var1, int var2, int var3, int var4) {
		return 16777215;
	}

	public static float getPercentAir(int var0) {
		if(var0 >= 8) {
			var0 = 0;
		}

		float var1 = (float)(var0 + 1) / 9.0F;
		return var1;
	}

	public int getBlockTextureFromSide(int var1) {
		return var1 != 0 && var1 != 1 ? this.blockIndexInTexture + 1 : this.blockIndexInTexture;
	}

	protected int getFlowDecay(World var1, int var2, int var3, int var4) {
		return var1.getBlockMaterial(var2, var3, var4) != this.blockMaterial ? -1 : var1.getBlockMetadata(var2, var3, var4);
	}

	protected int getEffectiveFlowDecay(IBlockAccess var1, int var2, int var3, int var4) {
		if(var1.getBlockMaterial(var2, var3, var4) != this.blockMaterial) {
			return -1;
		} else {
			int var5 = var1.getBlockMetadata(var2, var3, var4);
			if(var5 >= 8) {
				var5 = 0;
			}

			return var5;
		}
	}

	public boolean renderAsNormalBlock() {
		return false;
	}

	public boolean isOpaqueCube() {
		return false;
	}

	public boolean canCollideCheck(int var1, boolean var2) {
		return var2 && var1 == 0;
	}

	public boolean getIsBlockSolid(IBlockAccess var1, int var2, int var3, int var4, int var5) {
		Material var6 = var1.getBlockMaterial(var2, var3, var4);
		return var6 == this.blockMaterial ? false : (var6 == Material.ice ? false : (var5 == 1 ? true : super.getIsBlockSolid(var1, var2, var3, var4, var5)));
	}

	public boolean shouldSideBeRendered(IBlockAccess var1, int var2, int var3, int var4, int var5) {
		Material var6 = var1.getBlockMaterial(var2, var3, var4);
		return var6 == this.blockMaterial ? false : (var6 == Material.ice ? false : (var5 == 1 ? true : super.shouldSideBeRendered(var1, var2, var3, var4, var5)));
	}

	public AxisAlignedBB getCollisionBoundingBoxFromPool(World var1, int var2, int var3, int var4) {
		return null;
	}

	public int getRenderType() {
		return 4;
	}

	public int idDropped(int var1, Random var2) {
		return 0;
	}

	public int quantityDropped(Random var1) {
		return 0;
	}

	private Vec3D getFlowVector(IBlockAccess var1, int var2, int var3, int var4) {
		Vec3D var5 = Vec3D.createVector(0.0D, 0.0D, 0.0D);
		int var6 = this.getEffectiveFlowDecay(var1, var2, var3, var4);

		for(int var7 = 0; var7 < 4; ++var7) {
			int var8 = var2;
			int var10 = var4;
			if(var7 == 0) {
				var8 = var2 - 1;
			}

			if(var7 == 1) {
				var10 = var4 - 1;
			}

			if(var7 == 2) {
				++var8;
			}

			if(var7 == 3) {
				++var10;
			}

			int var11 = this.getEffectiveFlowDecay(var1, var8, var3, var10);
			int var12;
			if(var11 < 0) {
				if(!var1.getBlockMaterial(var8, var3, var10).getIsSolid()) {
					var11 = this.getEffectiveFlowDecay(var1, var8, var3 - 1, var10);
					if(var11 >= 0) {
						var12 = var11 - (var6 - 8);
						var5 = var5.addVector((double)((var8 - var2) * var12), (double)((var3 - var3) * var12), (double)((var10 - var4) * var12));
					}
				}
			} else if(var11 >= 0) {
				var12 = var11 - var6;
				var5 = var5.addVector((double)((var8 - var2) * var12), (double)((var3 - var3) * var12), (double)((var10 - var4) * var12));
			}
		}

		if(var1.getBlockMetadata(var2, var3, var4) >= 8) {
			boolean var13 = false;
			if(var13 || this.getIsBlockSolid(var1, var2, var3, var4 - 1, 2)) {
				var13 = true;
			}

			if(var13 || this.getIsBlockSolid(var1, var2, var3, var4 + 1, 3)) {
				var13 = true;
			}

			if(var13 || this.getIsBlockSolid(var1, var2 - 1, var3, var4, 4)) {
				var13 = true;
			}

			if(var13 || this.getIsBlockSolid(var1, var2 + 1, var3, var4, 5)) {
				var13 = true;
			}

			if(var13 || this.getIsBlockSolid(var1, var2, var3 + 1, var4 - 1, 2)) {
				var13 = true;
			}

			if(var13 || this.getIsBlockSolid(var1, var2, var3 + 1, var4 + 1, 3)) {
				var13 = true;
			}

			if(var13 || this.getIsBlockSolid(var1, var2 - 1, var3 + 1, var4, 4)) {
				var13 = true;
			}

			if(var13 || this.getIsBlockSolid(var1, var2 + 1, var3 + 1, var4, 5)) {
				var13 = true;
			}

			if(var13) {
				var5 = var5.normalize().addVector(0.0D, -6.0D, 0.0D);
			}
		}

		var5 = var5.normalize();
		return var5;
	}

	public void velocityToAddToEntity(World var1, int var2, int var3, int var4, Entity var5, Vec3D var6) {
		Vec3D var7 = this.getFlowVector(var1, var2, var3, var4);
		var6.xCoord += var7.xCoord;
		var6.yCoord += var7.yCoord;
		var6.zCoord += var7.zCoord;
	}

	public int tickRate() {
		return this.blockMaterial == Material.water ? 5 :
				(this.blockMaterial == Material.lava ? 30 :
						(this.blockMaterial == Material.oil ? 30 : 0));
	}

	public float getBlockBrightness(IBlockAccess var1, int var2, int var3, int var4) {
		float var5 = var1.getLightBrightness(var2, var3, var4);
		float var6 = var1.getLightBrightness(var2, var3 + 1, var4);
		return var5 > var6 ? var5 : var6;
	}

	public void updateTick(World var1, int var2, int var3, int var4, Random var5) {
		super.updateTick(var1, var2, var3, var4, var5);
	}

	public int getRenderBlockPass() {
		return this.blockMaterial == Material.water ? 1 : 0;
	}

	public void randomDisplayTick(World var1, int var2, int var3, int var4, Random var5) {
		if(this.blockMaterial == Material.water && var5.nextInt(64) == 0) {
			int var6 = var1.getBlockMetadata(var2, var3, var4);
			if(var6 > 0 && var6 < 8) {
				var1.playSoundEffect((double)((float)var2 + 0.5F), (double)((float)var3 + 0.5F), (double)((float)var4 + 0.5F), "liquid.water", var5.nextFloat() * 0.25F + 12.0F / 16.0F, var5.nextFloat() * 1.0F + 0.5F);
			}
		}

		if(this.blockMaterial == Material.lava && var1.getBlockMaterial(var2, var3 + 1, var4) == Material.air && !var1.isBlockOpaqueCube(var2, var3 + 1, var4) && var5.nextInt(100) == 0) {
			double var12 = (double)((float)var2 + var5.nextFloat());
			double var8 = (double)var3 + this.maxY;
			double var10 = (double)((float)var4 + var5.nextFloat());
			var1.spawnParticle("lava", var12, var8, var10, 0.0D, 0.0D, 0.0D);
		}

	}

	public static double func_293_a(IBlockAccess var0, int var1, int var2, int var3, Material var4) {
		Vec3D var5 = null;
		if(var4 == Material.water) {
			var5 = ((BlockFluid)Block.waterMoving).getFlowVector(var0, var1, var2, var3);
		}

		if(var4 == Material.lava) {
			var5 = ((BlockFluid)Block.lavaMoving).getFlowVector(var0, var1, var2, var3);
		}

		if(var4 == Material.oil) {
			var5 = ((BlockFluid)Block.oilMoving).getFlowVector(var0, var1, var2, var3);
		}

		return var5.xCoord == 0.0D && var5.zCoord == 0.0D ? -1000.0D : Math.atan2(var5.zCoord, var5.xCoord) - Math.PI * 0.5D;
	}

	public void onBlockAdded(World var1, int var2, int var3, int var4) {
		this.checkForHarden(var1, var2, var3, var4);
	}

	public void onNeighborBlockChange(World var1, int var2, int var3, int var4, int var5) {
		this.checkForHarden(var1, var2, var3, var4);
	}

	private void checkForHarden(World var1, int var2, int var3, int var4) {
		if(var1.getBlockId(var2, var3, var4) == this.blockID) {

			if(this.blockMaterial == Material.lava) {
				boolean var5 = false;
				if(var5 || var1.getBlockMaterial(var2, var3, var4 - 1) == Material.water) {
					var5 = true;
				}

				if(var5 || var1.getBlockMaterial(var2, var3, var4 + 1) == Material.water) {
					var5 = true;
				}

				if(var5 || var1.getBlockMaterial(var2 - 1, var3, var4) == Material.water) {
					var5 = true;
				}

				if(var5 || var1.getBlockMaterial(var2 + 1, var3, var4) == Material.water) {
					var5 = true;
				}

				if(var5 || var1.getBlockMaterial(var2, var3 + 1, var4) == Material.water) {
					var5 = true;
				}

				if(var5) {
					int var6 = var1.getBlockMetadata(var2, var3, var4);
					if(var6 == 0) {
						var1.setBlockWithNotify(var2, var3, var4, Block.obsidian.blockID);
					} else if(var6 <= 4) {
						var1.setBlockWithNotify(var2, var3, var4, Block.cobblestone.blockID);
					}

					this.triggerLavaWaterMix(var1, var2, var3, var4);
				}
			}

			if(this.blockMaterial == Material.oil) {
				boolean var5 =
						var1.getBlockMaterial(var2, var3, var4 - 1) == Material.lava ||
                        var1.getBlockMaterial(var2, var3, var4 + 1) == Material.lava ||
                        var1.getBlockMaterial(var2, var3 - 1, var4) == Material.lava ||
                        var1.getBlockMaterial(var2, var3 + 1, var4) == Material.lava ||
                        var1.getBlockMaterial(var2 - 1, var3, var4) == Material.lava ||
                        var1.getBlockMaterial(var2 + 1, var3, var4) == Material.lava ||
                        var1.getBlockMaterial(var2, var3, var4 - 1) == Material.fire ||
                        var1.getBlockMaterial(var2, var3, var4 + 1) == Material.fire ||
                        var1.getBlockMaterial(var2, var3 - 1, var4) == Material.fire ||
                        var1.getBlockMaterial(var2, var3 + 1, var4) == Material.fire ||
                        var1.getBlockMaterial(var2 - 1, var3, var4) == Material.fire ||
                        var1.getBlockMaterial(var2 + 1, var3, var4) == Material.fire;

                if(var5) {
					this.triggerLavaOilMix(var1, var2, var3, var4);
				}
			}

			if(this.blockMaterial == Material.water) {
				boolean var5 =
						var1.getBlockMaterial(var2, var3, var4 - 1) == Material.oil ||
						var1.getBlockMaterial(var2, var3, var4 + 1) == Material.oil ||
						var1.getBlockMaterial(var2, var3 - 1, var4) == Material.oil ||
						var1.getBlockMaterial(var2, var3 + 1, var4) == Material.oil ||
						var1.getBlockMaterial(var2 - 1, var3, var4) == Material.oil ||
						var1.getBlockMaterial(var2 + 1, var3, var4) == Material.oil;

				if(var5) {
					this.triggerOilWaterMix(var1, var2, var3, var4);
				}
			}
		}
	}

	protected void triggerLavaWaterMix(World var1, int var2, int var3, int var4) {
		var1.playSoundEffect((double)((float)var2 + 0.5F), (double)((float)var3 + 0.5F), (double)((float)var4 + 0.5F), "random.fizz", 0.5F, 2.6F + (var1.rand.nextFloat() - var1.rand.nextFloat()) * 0.8F);

		for(int var5 = 0; var5 < 8; ++var5) {
			var1.spawnParticle("largesmoke", (double)var2 + Math.random(), (double)var3 + 1.2D, (double)var4 + Math.random(), 0.0D, 0.0D, 0.0D);
		}
	}

	protected void triggerLavaOilMix(World var1, int var2, int var3, int var4) {
		var1.setBlock(var2, var3, var4, 0);
		for(; var3 < var3 + 5; ++var3) {
			if(
					var1.getBlockMaterial(var2, var3, var4) != Material.lava &&
					var1.getBlockMaterial(var2, var3, var4) != Material.oil
			) {
				break;
			}
			for(; var2 > 0; --var2) {
				if(
						var1.getBlockMaterial(var2, var3, var4) == Material.lava ||
						var1.getBlockMaterial(var2, var3, var4) == Material.oil
				) {
					break;
				}
				for(; var4 < var4 + 5; --var4) {
					if(
							var1.getBlockMaterial(var2, var3, var4) == Material.lava ||
							var1.getBlockMaterial(var2, var3, var4) == Material.oil
					) {
						break;
					}
				}
			}
		}
		var1.playSoundEffect((double)((float)var2 + 0.5F), (double)((float)var3 + 0.5F), (double)((float)var4 + 0.5F), "random.fizz", 0.5F, 2.6F + (var1.rand.nextFloat() - var1.rand.nextFloat()) * 0.8F);
		for(int var5 = 0; var5 < 8; ++var5) {
			var1.spawnParticle("largesmoke", (double)var2 + Math.random(), (double)var3 + 1.2D, (double)var4 + Math.random(), 0.0D, 0.0D, 0.0D);
		}
		var1.createExplosion(null, var2, var3, var4, 6.0F);
	}

	protected void triggerOilWaterMix(World var1, int var2, int var3, int var4) {
		if(random.nextInt(100) <= 5) {
			var1.setBlock(var2, var3, var4, Block.blockClay.blockID);
		} else {
			var1.setBlock(var2, var3, var4, Block.dirt.blockID);
		}
		var1.playSoundEffect((double)((float)var2 + 0.5F), (double)((float)var3 + 0.5F), (double)((float)var4 + 0.5F), "random.fizz", 0.5F, 2.6F + (var1.rand.nextFloat() - var1.rand.nextFloat()) * 0.8F);
		for(int var5 = 0; var5 < 8; ++var5) {
			var1.spawnParticle("largesmoke", (double)var2 + Math.random(), (double)var3 + 1.2D, (double)var4 + Math.random(), 0.0D, 0.0D, 0.0D);
		}
	}
}
