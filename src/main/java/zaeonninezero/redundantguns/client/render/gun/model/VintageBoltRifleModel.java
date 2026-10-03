package zaeonninezero.redundantguns.client.render.gun.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.mrcrayfish.guns.common.Gun;
import com.mrcrayfish.guns.client.GunModel;

import zaeonninezero.nzgmaddon.client.SpecialModels;
import zaeonninezero.redundantguns.client.RedundantSpecialModels;
import com.mrcrayfish.guns.client.render.gun.IOverrideModel;
import com.mrcrayfish.guns.client.util.RenderUtil;
import com.mrcrayfish.guns.item.attachment.IAttachment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.nbt.CompoundTag;
import com.mrcrayfish.guns.util.ItemStackUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemCooldowns;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

/**
 * Author: MrCrayfish
 * Modified by zaeonNineZero for Nine Zero's Gun Expansion
 * Attachment detection logic based off of code from Mo' Guns by Bomb787 and AlanorMiga (MigaMi)
 */
public class VintageBoltRifleModel implements IOverrideModel
{

    @Override
    // This class renders a model with support for NBT and attachment based part variations

    // We start by declaring our render function that will handle rendering the core baked model (which is a non-moving part).
    public void render(float partialTicks, ItemDisplayContext transformType, ItemStack stack, ItemStack parent, @Nullable LivingEntity entity, PoseStack poseStack, MultiBufferSource buffer, int light, int overlay)
    {
        // Render the item's BakedModel, which will serve as the core of our custom model.
        BakedModel bakedModel = RedundantSpecialModels.VINTAGE_BOLT_RIFLE_BASE.getModel();
        Minecraft.getInstance().getItemRenderer().render(stack, ItemDisplayContext.NONE, false, poseStack, buffer, light, overlay, GunModel.wrap(bakedModel));

        // Render the iron sights element, which is only present when a scope is not attached.
        // We have to grab the gun's scope attachment slot and check whether it is empty or not.
        // If the isEmpty function returns true, then we render the iron sights.
        ItemStack attachmentStack = Gun.getAttachment(IAttachment.Type.SCOPE, stack);
        if(attachmentStack.isEmpty())
        {
            RenderUtil.renderModel(SpecialModels.BOLT_ACTION_RIFLE_SIGHTS.getModel(), transformType, null, stack, parent, poseStack, buffer, light, overlay);
        }
        else
        // Render the top rail element that appears when a scope is attached.
        {
            RenderUtil.renderModel(SpecialModels.BOLT_ACTION_RIFLE_RAIL.getModel(), transformType, null, stack, parent, poseStack, buffer, light, overlay);
        }

        // Mechanical firing animation for ordinary CGM.
        // First, some variables for animation building
        boolean isPlayer = entity != null && entity.equals(Minecraft.getInstance().player);
        boolean correctContext = (transformType.firstPerson() || transformType == ItemDisplayContext.THIRD_PERSON_RIGHT_HAND || transformType == ItemDisplayContext.THIRD_PERSON_LEFT_HAND);

        Vec3 boltTranslations = Vec3.ZERO;
        Vec3 boltRotations = Vec3.ZERO;
        Vec3 boltRotOffset = new Vec3(0, -4.15, 0);

        // Mechanical firing animation driven by the CGM cooldown.

        {
            if(isPlayer && correctContext)
            {
                float cooldownDivider = 3.0F;
                float cooldownOffset1 = 1.0F;
                float intensity = 1.9F +1;
                float boltLeadTime = 0.4F;

                ItemCooldowns tracker = Minecraft.getInstance().player.getCooldowns();
                float cooldown = tracker.getCooldownPercent(stack.getItem(), partialTicks);
                cooldown *= cooldownDivider;
                float cooldown_a = cooldown-cooldownOffset1;

                float cooldown_b = Math.min(Math.max(cooldown_a*intensity,0),1);
                float cooldown_c = Math.min(Math.max((-cooldown_a*intensity)+intensity,0),1);
                float cooldown_d = Math.min(cooldown_b,cooldown_c);

                float cooldown_e = Math.min(Math.max(cooldown_a*intensity+boltLeadTime,0),1);
                float cooldown_f = Math.min(Math.max((-cooldown_a*intensity+boltLeadTime)+intensity,0),1);
                float cooldown_g = Math.min(cooldown_e,cooldown_f);

                boltTranslations = new Vec3(0, 0, (cooldown_d * 2.5));
                boltRotations = new Vec3(0, 0, -(67.5F * Math.min(cooldown_g*2F,1)));
            }
        }

        // Bolt-Action Rifle bolt and chamber. This animated part cycles backward then forward after firing.
        // This element consists of two parts.

        // Part 1: Rotating bolt handle
        // Push pose so we can make do transformations without affecting the models above.
        poseStack.pushPose();
        // Now we apply our transformations.
        if(isPlayer)
        {
            if(boltTranslations!=Vec3.ZERO)
            poseStack.translate(0, 0, boltTranslations.z*0.0625);

                poseStack.translate(0, boltRotOffset.y*0.0625, 0);
                poseStack.mulPose(Axis.ZN.rotationDegrees((float) boltRotations.z));
                poseStack.translate(0, -boltRotOffset.y*0.0625, 0);

        }
        // Our transformations are done - now we can render the model.
        RenderUtil.renderModel(SpecialModels.BOLT_ACTION_RIFLE_BOLT.getModel(), transformType, null, stack, parent, poseStack, buffer, light, overlay);
        // Pop pose to compile everything in the render matrix.
        poseStack.popPose();

        // Part 2: Non-rotating bolt/chamber
        // Push pose so we can make do transformations without affecting the models above.
        poseStack.pushPose();
        // Now we apply our transformations.
        if(isPlayer)
        poseStack.translate(0, 0, boltTranslations.z*0.0625);
        // Our transformations are done - now we can render the model.
        RenderUtil.renderModel(SpecialModels.BOLT_ACTION_RIFLE_CHAMBER.getModel(), transformType, null, stack, parent, poseStack, buffer, light, overlay);
        // Pop pose to compile everything in the render matrix.
        poseStack.popPose();

        // Rifle Bullets:
        // Rifle bullet 1 -- used during custom reload animations.
        if(Gun.hasAmmo(stack))
        {
            // Push pose.
            poseStack.pushPose();
            // Initial translation to the starting position.
            poseStack.translate(0.0, -4.15*0.0625, 3.1*0.0625);
            // Apply transformations.

            // Render the model.
            RenderUtil.renderModel(SpecialModels.BOLT_ACTION_RIFLE_BULLET.getModel(), transformType, null, stack, parent, poseStack, buffer, light, overlay);
            // Pop pose.
            poseStack.popPose();
        }
    }
}
