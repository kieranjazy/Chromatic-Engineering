package com.grumpybear.chromeng.handler;

import java.util.HashSet;
import java.util.Set;

import org.lwjgl.opengl.GL11;

import com.grumpybear.chromeng.init.ModItems;
import com.grumpybear.chromeng.item.workonlater.ItemBlockDesignator;
import com.grumpybear.chromeng.render.RenderDesignated;
import com.grumpybear.chromeng.util.BlockPosUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderWorldLastEventHandler {

	public static void tick(RenderWorldLastEvent event) {
		renderDesignatedBlocks(event);
	}
	
	private static void renderDesignatedBlocks(RenderWorldLastEvent event) {
		Minecraft mc = Minecraft.getMinecraft();
		EntityPlayerSP p = mc.player;
		ItemStack held = p.getHeldItem(EnumHand.MAIN_HAND);
		if (held.isEmpty()) {
			return;
		}
		
		if (held.getItem() == ModItems.blockDesignator) {
			Set<BlockPos> blocks = new HashSet<BlockPos>();
			for (String key : ItemBlockDesignator.getNBT(held).getKeySet()) {
				blocks.add(BlockPosUtil.longToPos(ItemBlockDesignator.getNBT(held).getLong(key)));
			}
			if (!blocks.isEmpty()) {
				renderHighlightedBlocks(event, p, blocks);
			}
		}
	}
	
	private static final ResourceLocation glow = new ResourceLocation("chromaticengineering:textures/blocks/designated.png");
	
	private static void renderHighlightedBlocks(RenderWorldLastEvent event, EntityPlayerSP p, Set<BlockPos> blocks) {
		double doubleX = p.lastTickPosX + (p.posX - p.lastTickPosX) * event.getPartialTicks();
        double doubleY = p.lastTickPosY + (p.posY - p.lastTickPosY) * event.getPartialTicks();
        double doubleZ = p.lastTickPosZ + (p.posZ - p.lastTickPosZ) * event.getPartialTicks();

        GlStateManager.pushMatrix();
        GlStateManager.translate(-doubleX, -doubleY, -doubleZ);

        GlStateManager.disableDepth();
        GlStateManager.enableTexture2D();

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();

        Minecraft.getMinecraft().getTextureManager().bindTexture(glow);

        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_LMAP_COLOR);
//        tessellator.setColorRGBA(255, 255, 255, 64);
//        tessellator.setBrightness(240);

        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        for (BlockPos coordinate : blocks) {
            float x = coordinate.getX();
            float y = coordinate.getY();
            float z = coordinate.getZ();
            buffer.setTranslation(x, y - 1, z);

            RenderDesignated.addSideFullTexture(buffer, EnumFacing.UP.ordinal(), 1.1f, -0.05f);
            RenderDesignated.addSideFullTexture(buffer, EnumFacing.DOWN.ordinal(), 1.1f, -0.05f);
            RenderDesignated.addSideFullTexture(buffer, EnumFacing.NORTH.ordinal(), 1.1f, -0.05f);
            RenderDesignated.addSideFullTexture(buffer, EnumFacing.SOUTH.ordinal(), 1.1f, -0.05f);
            RenderDesignated.addSideFullTexture(buffer, EnumFacing.WEST.ordinal(), 1.1f, -0.05f);
            RenderDesignated.addSideFullTexture(buffer, EnumFacing.EAST.ordinal(), 1.1f, -0.05f);
            buffer.setTranslation(x, y - 1, z);
        }
        tessellator.draw();

        GlStateManager.disableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.color(.5f, .3f, 0);
        GlStateManager.glLineWidth(2);

        buffer.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION_COLOR);

        for (BlockPos coordinate : blocks) {
            RenderDesignated.renderHighLightedBlocksOutline(buffer,
                    coordinate.getX(), coordinate.getY(), coordinate.getZ(),
                    .5f, .3f, 0f, 1.0f);
        }
        tessellator.draw();

        GlStateManager.enableTexture2D();
        GlStateManager.popMatrix();
	}
	
	
}
