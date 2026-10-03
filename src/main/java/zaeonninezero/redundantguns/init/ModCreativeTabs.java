package zaeonninezero.redundantguns.init;

import com.mrcrayfish.guns.item.GunItem;
import com.mrcrayfish.guns.util.ItemStackUtil;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import zaeonninezero.redundantguns.common.CreativeGunVariantManager;
import zaeonninezero.redundantguns.RedundantGuns;

public final class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, RedundantGuns.MOD_ID);
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MAIN = TABS.register("main",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.redundantguns"))
                    .icon(() -> loadedStack(initItems.SIDEARM_PISTOL.get()))
                    .displayItems((parameters, output) -> {
                        initItems.ITEMS.getEntries().forEach(item -> output.accept(loadedStack(item.get())));
                        NonNullList<ItemStack> variants = NonNullList.create();
                        CreativeGunVariantManager.addItemVariants(variants);
                        variants.forEach(output::accept);
                    }).build());

    public static ItemStack loadedStack(Item item) {
        ItemStack stack = new ItemStack(item);
        if (item instanceof GunItem gun) {
            var tag = ItemStackUtil.getOrCreateTag(stack);
            tag.putInt("AmmoCount", gun.getGun().getGeneral().getMaxAmmo());
            ItemStackUtil.setTag(stack, tag);
        }
        return stack;
    }

    private ModCreativeTabs() {}
}
