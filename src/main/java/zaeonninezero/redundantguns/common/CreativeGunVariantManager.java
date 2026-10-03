package zaeonninezero.redundantguns.common;

import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import com.mrcrayfish.guns.util.ItemStackUtil;
import net.minecraft.world.item.ItemStack;
import zaeonninezero.redundantguns.init.initItems;

public class CreativeGunVariantManager
{
    public static void addItemVariants(NonNullList<ItemStack> items)
    {
        // Combat Pistol, Variant 1
        ItemStack combatpistol_1 = new ItemStack(initItems.COMBAT_PISTOL.get());
        {
            CompoundTag tag = ItemStackUtil.getOrCreateTag(combatpistol_1);
            tag.putInt("AmmoCount", initItems.COMBAT_PISTOL.get().getGun().getGeneral().getMaxAmmo());
            tag.putInt("BaseVariant", 1);
            tag.putInt("HammerVariant", 1);
            ItemStackUtil.setTag(combatpistol_1, tag);
        }
        items.add(combatpistol_1);
        // Combat Pistol, Variant 2
        ItemStack combatpistol_2 = new ItemStack(initItems.COMBAT_PISTOL.get());
        {
            CompoundTag tag = ItemStackUtil.getOrCreateTag(combatpistol_2);
            tag.putInt("AmmoCount", initItems.COMBAT_PISTOL.get().getGun().getGeneral().getMaxAmmo());
            tag.putInt("BaseVariant", 2);
            tag.putInt("SlideVariant", 1);
            ItemStackUtil.setTag(combatpistol_2, tag);
        }
        items.add(combatpistol_2);


        // Ranger Assault Rifle, Variant 1
        ItemStack rangerar_1 = new ItemStack(initItems.RANGER_ASSAULT_RIFLE.get());
        {
            CompoundTag tag = ItemStackUtil.getOrCreateTag(rangerar_1);
            tag.putInt("AmmoCount", initItems.RANGER_ASSAULT_RIFLE.get().getGun().getGeneral().getMaxAmmo());
            tag.putInt("BaseVariant", 1);
            ItemStackUtil.setTag(rangerar_1, tag);
        }
        items.add(rangerar_1);


        // Carbine Rifle, Variant 1
        ItemStack carbinerrifle_1 = new ItemStack(initItems.CARBINE_RIFLE.get());
        {
            CompoundTag tag = ItemStackUtil.getOrCreateTag(carbinerrifle_1);
            tag.putInt("AmmoCount", initItems.CARBINE_RIFLE.get().getGun().getGeneral().getMaxAmmo());
            tag.putInt("HandguardVariant", 1);
            tag.putInt("SightVariant", 1);
            tag.putInt("BaseVariant", 1);
            ItemStackUtil.setTag(carbinerrifle_1, tag);
        }
        items.add(carbinerrifle_1);
        // Carbine Rifle, Variant 2
        ItemStack carbinerrifle_2 = new ItemStack(initItems.CARBINE_RIFLE.get());
        {
            CompoundTag tag = ItemStackUtil.getOrCreateTag(carbinerrifle_2);
            tag.putInt("AmmoCount", initItems.CARBINE_RIFLE.get().getGun().getGeneral().getMaxAmmo());
            tag.putInt("HandguardVariant", 2);
            tag.putInt("BaseVariant", 1);
            ItemStackUtil.setTag(carbinerrifle_2, tag);
        }
        items.add(carbinerrifle_2);


        // Tactical Carbine, Variant 1
        ItemStack tacticalcarbine_1 = new ItemStack(initItems.TACTICAL_CARBINE.get());
        {
            CompoundTag tag = ItemStackUtil.getOrCreateTag(tacticalcarbine_1);
            tag.putInt("AmmoCount", initItems.TACTICAL_CARBINE.get().getGun().getGeneral().getMaxAmmo());
            tag.putInt("HandguardVariant", 1);
            ItemStackUtil.setTag(tacticalcarbine_1, tag);
        }
        items.add(tacticalcarbine_1);
        // Tactical Carbine, Variant 2
        ItemStack tacticalcarbine_2 = new ItemStack(initItems.TACTICAL_CARBINE.get());
        {
            CompoundTag tag = ItemStackUtil.getOrCreateTag(tacticalcarbine_2);
            tag.putInt("AmmoCount", initItems.TACTICAL_CARBINE.get().getGun().getGeneral().getMaxAmmo());
            tag.putInt("BaseVariant", 1);
            ItemStackUtil.setTag(tacticalcarbine_2, tag);
        }
        items.add(tacticalcarbine_2);
        // Tactical Carbine, Variant 3
        ItemStack tacticalcarbine_3 = new ItemStack(initItems.TACTICAL_CARBINE.get());
        {
            CompoundTag tag = ItemStackUtil.getOrCreateTag(tacticalcarbine_3);
            tag.putInt("AmmoCount", initItems.TACTICAL_CARBINE.get().getGun().getGeneral().getMaxAmmo());
            tag.putInt("HandguardVariant", 2);
            tag.putInt("SightVariant", 1);
            ItemStackUtil.setTag(tacticalcarbine_3, tag);
        }
        items.add(tacticalcarbine_3);


        // Combat Assault Rifle, Variant 1
        ItemStack combatrifle_1 = new ItemStack(initItems.COMBAT_ASSAULT_RIFLE.get());
        {
            CompoundTag tag = ItemStackUtil.getOrCreateTag(combatrifle_1);
            tag.putInt("AmmoCount", initItems.COMBAT_ASSAULT_RIFLE.get().getGun().getGeneral().getMaxAmmo());
            tag.putInt("SightVariant", 1);
            ItemStackUtil.setTag(combatrifle_1, tag);
        }
        items.add(combatrifle_1);


        // Tactical Infantry Rifle, Variant 1
        ItemStack tacinfantryrifle_1 = new ItemStack(initItems.TACTICAL_INFANTRY_RIFLE.get());
        {
            CompoundTag tag = ItemStackUtil.getOrCreateTag(tacinfantryrifle_1);
            tag.putInt("AmmoCount", initItems.TACTICAL_INFANTRY_RIFLE.get().getGun().getGeneral().getMaxAmmo());
            tag.putInt("BaseVariant", 1);
            ItemStackUtil.setTag(tacinfantryrifle_1, tag);
        }
        items.add(tacinfantryrifle_1);


        // Precision Battle Rifle, Variant 1
        ItemStack precisionbr_1 = new ItemStack(initItems.PRECISION_BATTLE_RIFLE.get());
        {
            CompoundTag tag = ItemStackUtil.getOrCreateTag(precisionbr_1);
            tag.putInt("AmmoCount", initItems.PRECISION_BATTLE_RIFLE.get().getGun().getGeneral().getMaxAmmo());
            tag.putInt("BaseVariant", 1);
            ItemStackUtil.setTag(precisionbr_1, tag);
        }
        items.add(precisionbr_1);
        // Precision Battle Rifle, Variant 2
        ItemStack precisionbr_2 = new ItemStack(initItems.PRECISION_BATTLE_RIFLE.get());
        {
            CompoundTag tag = ItemStackUtil.getOrCreateTag(precisionbr_2);
            tag.putInt("AmmoCount", initItems.PRECISION_BATTLE_RIFLE.get().getGun().getGeneral().getMaxAmmo());
            tag.putInt("BaseVariant", 1);
            tag.putInt("HandguardVariant", 1);
            ItemStackUtil.setTag(precisionbr_2, tag);
        }
        items.add(precisionbr_2);


        // Mare's Leg Rifle, Variant 1
        ItemStack maresleg_1 = new ItemStack(initItems.MARES_LEG_RIFLE.get());
        {
            CompoundTag tag = ItemStackUtil.getOrCreateTag(maresleg_1);
            tag.putInt("AmmoCount", initItems.MARES_LEG_RIFLE.get().getGun().getGeneral().getMaxAmmo());
            tag.putInt("BaseVariant", 1);
            ItemStackUtil.setTag(maresleg_1, tag);
        }
        items.add(maresleg_1);
    }
}