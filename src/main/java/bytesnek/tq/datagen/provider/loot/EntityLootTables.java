package bytesnek.tq.datagen.provider.loot;

import java.util.function.Predicate;
import java.util.stream.Stream;

import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.data.loot.EntityLootSubProvider;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootTableReference;
import net.minecraft.world.level.storage.loot.entries.TagEntry;
import net.minecraft.world.level.storage.loot.functions.LootingEnchantFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemKilledByPlayerCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.RegistryObject;

import org.jetbrains.annotations.NotNull;

import bytesnek.hiss.sneaky.Sneaky;
import bytesnek.tq.rego.Entities;
import bytesnek.tq.rego.Items;
import bytesnek.tq.rego.LootTables;

import static net.minecraft.world.item.Items.*;

/**
 * Created by SnakerBone on 16/10/2023
 **/
public class EntityLootTables extends EntityLootSubProvider
{
    public EntityLootTables()
    {
        super(FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    public void generate()
    {
        add(Entities.COSMIC_CREEPERITE.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(GUNPOWDER)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0, 2)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0, 1)))
                        )
                        .add(LootItem.lootTableItem(ENDER_PEARL)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0, 2)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0, 1)))
                        )
                )
                .withPool(LootPool.lootPool().add(TagEntry.expandTag(ItemTags.CREEPER_DROP_MUSIC_DISCS))
                        .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.KILLER,
                                EntityPredicate.Builder.entity().of(EntityTypeTags.SKELETONS))
                        )
                )
        );

        add(Entities.FLARE.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(BLAZE_ROD)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0, 1)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0, 1)))
                        )
                        .add(LootItem.lootTableItem(BLAZE_POWDER)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0, 1)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0, 1)))
                        )
                        .when(LootItemKilledByPlayerCondition.killedByPlayer())
                )
        );

        add(Entities.FLUTTERFLY.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(Items.FLUTTERFLY_KERATIN.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0, 1)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0, 1)))
                        )
                        .when(LootItemKilledByPlayerCondition.killedByPlayer())
                )
        );

        add(Entities.COSMO.get(), EntityLootTables.empty());
        add(Entities.COSMO.get(), LootTables.COSMO_RED, createCosmoTable(Items.RED_COSMO_SPINE.get()));
        add(Entities.COSMO.get(), LootTables.COSMO_GREEN, createCosmoTable(Items.GREEN_COSMO_SPINE.get()));
        add(Entities.COSMO.get(), LootTables.COSMO_BLUE, createCosmoTable(Items.BLUE_COSMO_SPINE.get()));
        add(Entities.COSMO.get(), LootTables.COSMO_YELLOW, createCosmoTable(Items.YELLOW_COSMO_SPINE.get()));
        add(Entities.COSMO.get(), LootTables.COSMO_PINK, createCosmoTable(Items.PINK_COSMO_SPINE.get()));
        add(Entities.COSMO.get(), LootTables.COSMO_PURPLE, createCosmoTable(Items.PURPLE_COSMO_SPINE.get()));
        add(Entities.COSMO.get(), LootTables.COSMO_ALPHA, createCosmoTable(Items.ALPHA_COSMO_SPINE.get()));
    }

    @Override
    protected @NotNull Stream<EntityType<?>> getKnownEntityTypes()
    {
        return Sneaky.cast(Entities.REGISTER.getEntries().stream()
                .map(RegistryObject::get)
                .filter(EntityLootTables.getEntitiesWithLootTables()));
    }

    private LootTable.Builder createCosmoTable(ItemLike item)
    {
        return LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(item)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0, 1)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0, 1)))
                        )
                )
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootTableReference.lootTableReference(Entities.COSMO.get().getDefaultLootTable()))
                );
    }

    static Predicate<EntityType<?>> getEntitiesWithLootTables()
    {
        return entity -> entity == Entities.COSMIC_CREEPERITE.get() || entity == Entities.COSMO.get() || entity == Entities.FLARE.get() || entity == Entities.FLUTTERFLY.get();
    }

    static LootTable.Builder empty()
    {
        return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1)));
    }
}
