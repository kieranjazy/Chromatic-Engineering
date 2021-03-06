package net.minecraft.stats;

import java.util.BitSet;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class RecipeBook
{
    protected final BitSet field_194077_a = new BitSet();
    protected final BitSet field_194078_b = new BitSet();
    protected boolean field_192818_b;
    protected boolean field_192819_c;

    public void func_193824_a(RecipeBook p_193824_1_)
    {
        this.field_194077_a.clear();
        this.field_194078_b.clear();
        this.field_194077_a.or(p_193824_1_.field_194077_a);
        this.field_194078_b.or(p_193824_1_.field_194078_b);
    }

    public void func_194073_a(IRecipe p_194073_1_)
    {
        if (!p_194073_1_.func_192399_d())
        {
            this.field_194077_a.set(func_194075_d(p_194073_1_));
        }
    }

    public boolean func_193830_f(IRecipe p_193830_1_)
    {
        return this.field_194077_a.get(func_194075_d(p_193830_1_));
    }

    public void func_193831_b(IRecipe p_193831_1_)
    {
        int i = func_194075_d(p_193831_1_);
        this.field_194077_a.clear(i);
        this.field_194078_b.clear(i);
    }

    @Deprecated //DO NOT USE
    protected static int func_194075_d(IRecipe p_194075_0_)
    {
        int ret = CraftingManager.field_193380_a.getIDForObject(p_194075_0_);
        if (ret == -1)
        {
            ret = ((net.minecraftforge.registries.ForgeRegistry<IRecipe>)net.minecraftforge.fml.common.registry.ForgeRegistries.RECIPES).getID(p_194075_0_.getRegistryName());
            if (ret == -1)
                throw new IllegalArgumentException(String.format("Attempted to get the ID for a unknown recipe: %s Name: %s", p_194075_0_, p_194075_0_.getRegistryName()));
        }
        return ret;
    }

    @SideOnly(Side.CLIENT)
    public boolean func_194076_e(IRecipe p_194076_1_)
    {
        return this.field_194078_b.get(func_194075_d(p_194076_1_));
    }

    public void func_194074_f(IRecipe p_194074_1_)
    {
        this.field_194078_b.clear(func_194075_d(p_194074_1_));
    }

    public void func_193825_e(IRecipe p_193825_1_)
    {
        this.field_194078_b.set(func_194075_d(p_193825_1_));
    }

    @SideOnly(Side.CLIENT)
    public boolean func_192812_b()
    {
        return this.field_192818_b;
    }

    public void func_192813_a(boolean p_192813_1_)
    {
        this.field_192818_b = p_192813_1_;
    }

    @SideOnly(Side.CLIENT)
    public boolean func_192815_c()
    {
        return this.field_192819_c;
    }

    public void func_192810_b(boolean p_192810_1_)
    {
        this.field_192819_c = p_192810_1_;
    }
}