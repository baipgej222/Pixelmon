package net.minecraft.src;

public class EntityAIOcelotSit extends EntityAIBase
{
    private final EntityOcelot field_50019_a;
    private final float field_50017_b;
    private int field_50018_c = 0;
    private int field_52005_h = 0;
    private int field_50015_d = 0;
    private int field_50016_e = 0;
    private int field_50013_f = 0;
    private int field_50014_g = 0;

    public EntityAIOcelotSit(EntityOcelot par1EntityOcelot, float par2)
    {
        this.field_50019_a = par1EntityOcelot;
        this.field_50017_b = par2;
        this.setMutexBits(5);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        return this.field_50019_a.isTamed() && !this.field_50019_a.isSitting() && this.field_50019_a.getRNG().nextDouble() <= 0.006500000134110451D && this.func_50012_f();
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean continueExecuting()
    {
        return this.field_50018_c <= this.field_50015_d && this.field_52005_h <= 60 && this.func_50011_a(this.field_50019_a.worldObj, this.field_50016_e, this.field_50013_f, this.field_50014_g);
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        this.field_50019_a.getNavigator().tryMoveToXYZ((double)((float)this.field_50016_e) + 0.5D, (double)(this.field_50013_f + 1), (double)((float)this.field_50014_g) + 0.5D, this.field_50017_b);
        this.field_50018_c = 0;
        this.field_52005_h = 0;
        this.field_50015_d = this.field_50019_a.getRNG().nextInt(this.field_50019_a.getRNG().nextInt(1200) + 1200) + 1200;
        this.field_50019_a.func_50021_C().setIsSitting(false);
    }

    /**
     * Resets the task
     */
    public void resetTask()
    {
        this.field_50019_a.setSitting(false);
    }

    /**
     * Updates the task
     */
    public void updateTask()
    {
        ++this.field_50018_c;
        this.field_50019_a.func_50021_C().setIsSitting(false);

        if (this.field_50019_a.getDistanceSq((double)this.field_50016_e, (double)(this.field_50013_f + 1), (double)this.field_50014_g) > 1.0D)
        {
            this.field_50019_a.setSitting(false);
            this.field_50019_a.getNavigator().tryMoveToXYZ((double)((float)this.field_50016_e) + 0.5D, (double)(this.field_50013_f + 1), (double)((float)this.field_50014_g) + 0.5D, this.field_50017_b);
            ++this.field_52005_h;
        }
        else if (!this.field_50019_a.isSitting())
        {
            this.field_50019_a.setSitting(true);
        }
        else
        {
            --this.field_52005_h;
        }
    }

    private boolean func_50012_f()
    {
        int var1 = (int)this.field_50019_a.posY;
        double var2 = 2.147483647E9D;

        for (int var4 = (int)this.field_50019_a.posX - 8; (double)var4 < this.field_50019_a.posX + 8.0D; ++var4)
        {
            for (int var5 = (int)this.field_50019_a.posZ - 8; (double)var5 < this.field_50019_a.posZ + 8.0D; ++var5)
            {
                if (this.func_50011_a(this.field_50019_a.worldObj, var4, var1, var5) && this.field_50019_a.worldObj.isAirBlock(var4, var1 + 1, var5))
                {
                    double var6 = this.field_50019_a.getDistanceSq((double)var4, (double)var1, (double)var5);

                    if (var6 < var2)
                    {
                        this.field_50016_e = var4;
                        this.field_50013_f = var1;
                        this.field_50014_g = var5;
                        var2 = var6;
                    }
                }
            }
        }

        return var2 < 2.147483647E9D;
    }

    private boolean func_50011_a(World par1World, int par2, int par3, int par4)
    {
        int var5 = par1World.getBlockId(par2, par3, par4);
        int var6 = par1World.getBlockMetadata(par2, par3, par4);

        if (var5 == Block.chest.blockID)
        {
            TileEntityChest var7 = (TileEntityChest)par1World.getBlockTileEntity(par2, par3, par4);

            if (var7.numUsingPlayers < 1)
            {
                return true;
            }
        }
        else
        {
            if (var5 == Block.stoneOvenActive.blockID)
            {
                return true;
            }

            if (var5 == Block.bed.blockID && !BlockBed.isBlockFootOfBed(var6))
            {
                return true;
            }
        }

        return false;
    }
}
