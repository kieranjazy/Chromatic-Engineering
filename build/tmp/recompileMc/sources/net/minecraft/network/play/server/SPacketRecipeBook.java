package net.minecraft.network.play.server;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.List;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class SPacketRecipeBook implements Packet<INetHandlerPlayClient>
{
    private SPacketRecipeBook.State field_193646_a;
    private List<IRecipe> field_192596_a;
    private List<IRecipe> field_193647_c;
    private boolean field_192598_c;
    private boolean field_192599_d;

    public SPacketRecipeBook()
    {
    }

    public SPacketRecipeBook(SPacketRecipeBook.State p_i47597_1_, List<IRecipe> p_i47597_2_, List<IRecipe> p_i47597_3_, boolean p_i47597_4_, boolean p_i47597_5_)
    {
        this.field_193646_a = p_i47597_1_;
        this.field_192596_a = p_i47597_2_;
        this.field_193647_c = p_i47597_3_;
        this.field_192598_c = p_i47597_4_;
        this.field_192599_d = p_i47597_5_;
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(INetHandlerPlayClient handler)
    {
        handler.func_191980_a(this);
    }

    /**
     * Reads the raw packet data from the data stream.
     */
    public void readPacketData(PacketBuffer buf) throws IOException
    {
        this.field_193646_a = (SPacketRecipeBook.State)buf.readEnumValue(SPacketRecipeBook.State.class);
        this.field_192598_c = buf.readBoolean();
        this.field_192599_d = buf.readBoolean();
        int i = buf.readVarInt();
        this.field_192596_a = Lists.<IRecipe>newArrayList();

        for (int j = 0; j < i; ++j)
        {
            this.field_192596_a.add(CraftingManager.func_193374_a(buf.readVarInt()));
        }

        if (this.field_193646_a == SPacketRecipeBook.State.INIT)
        {
            i = buf.readVarInt();
            this.field_193647_c = Lists.<IRecipe>newArrayList();

            for (int k = 0; k < i; ++k)
            {
                this.field_193647_c.add(CraftingManager.func_193374_a(buf.readVarInt()));
            }
        }
    }

    /**
     * Writes the raw packet data to the data stream.
     */
    public void writePacketData(PacketBuffer buf) throws IOException
    {
        buf.writeEnumValue(this.field_193646_a);
        buf.writeBoolean(this.field_192598_c);
        buf.writeBoolean(this.field_192599_d);
        buf.writeVarInt(this.field_192596_a.size());

        for (IRecipe irecipe : this.field_192596_a)
        {
            buf.writeVarInt(CraftingManager.func_193375_a(irecipe));
        }

        if (this.field_193646_a == SPacketRecipeBook.State.INIT)
        {
            buf.writeVarInt(this.field_193647_c.size());

            for (IRecipe irecipe1 : this.field_193647_c)
            {
                buf.writeVarInt(CraftingManager.func_193375_a(irecipe1));
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public List<IRecipe> func_192595_a()
    {
        return this.field_192596_a;
    }

    @SideOnly(Side.CLIENT)
    public List<IRecipe> func_193644_b()
    {
        return this.field_193647_c;
    }

    @SideOnly(Side.CLIENT)
    public boolean func_192593_c()
    {
        return this.field_192598_c;
    }

    @SideOnly(Side.CLIENT)
    public boolean func_192594_d()
    {
        return this.field_192599_d;
    }

    @SideOnly(Side.CLIENT)
    public SPacketRecipeBook.State func_194151_e()
    {
        return this.field_193646_a;
    }

    public static enum State
    {
        INIT,
        ADD,
        REMOVE;
    }
}