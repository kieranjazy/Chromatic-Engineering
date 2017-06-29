/*
 * Minecraft Forge
 * Copyright (c) 2016.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation version 2.1
 * of the License.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */
package net.minecraftforge.oredict;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntComparators;
import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.client.util.RecipeItemHelper;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class OreIngredient extends Ingredient
{
    private NonNullList<ItemStack> ores;
    private IntList itemIds = null;
    private ItemStack[] array = null;

    public OreIngredient(String ore)
    {
        super(0);
        ores = OreDictionary.getOres(ore);
    }

    @Override
    @Nonnull
    public ItemStack[] func_193365_a()
    {
        if (array == null || this.array.length != ores.size())
        {
            NonNullList<ItemStack> lst = NonNullList.create();
            for (ItemStack itemstack : this.ores)
            {
                if (itemstack.getMetadata() == OreDictionary.WILDCARD_VALUE)
                    itemstack.getItem().getSubItems(CreativeTabs.SEARCH, lst);
                else
                    lst.add(itemstack);
            }
            this.array = lst.toArray(new ItemStack[lst.size()]);
        }
        return this.array;
    }


    @Override
    @Nonnull
    @SideOnly(Side.CLIENT)
    public IntList func_194139_b()
    {
        if (this.itemIds == null || this.itemIds.size() != ores.size())
        {
            this.itemIds = new IntArrayList(this.ores.size());

            for (ItemStack itemstack : this.ores)
            {
                if (itemstack.getMetadata() == OreDictionary.WILDCARD_VALUE)
                {
                    NonNullList<ItemStack> lst = NonNullList.create();
                    itemstack.getItem().getSubItems(CreativeTabs.SEARCH, lst);
                    for (ItemStack item : lst)
                        this.itemIds.add(RecipeItemHelper.func_194113_b(item));
                }
                else
                {
                    this.itemIds.add(RecipeItemHelper.func_194113_b(itemstack));
                }
            }

            this.itemIds.sort(IntComparators.NATURAL_COMPARATOR);
        }

        return this.itemIds;
    }


    @Override
    public boolean apply(@Nullable ItemStack input)
    {
        if (input == null)
            return false;

        for (ItemStack target : this.ores)
            if (OreDictionary.itemMatches(target, input, false))
                return true;

        return false;
    }

    @Override
    protected void invalidate()
    {
        this.itemIds = null;
    }
}