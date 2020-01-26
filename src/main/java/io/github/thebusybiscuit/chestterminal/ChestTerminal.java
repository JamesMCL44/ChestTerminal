package io.github.thebusybiscuit.chestterminal;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.Item.CustomItem;
import me.mrCookieSlime.Slimefun.GEO.OreGenResource;
import me.mrCookieSlime.Slimefun.GEO.OreGenSystem;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Lists.SlimefunItems;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.api.item_transport.CargoNet;
import me.mrCookieSlime.Slimefun.bstats.bukkit.Metrics;
import me.mrCookieSlime.Slimefun.cscorelib2.config.Config;
import me.mrCookieSlime.Slimefun.cscorelib2.updater.GitHubBuildsUpdater;
import me.mrCookieSlime.Slimefun.cscorelib2.updater.Updater;

public class ChestTerminal extends JavaPlugin implements Listener {
	
	@Override
	public void onEnable() {
		Config cfg = new Config(this);
		
		// Setting up bStats
		new Metrics(this);

		// Setting up the Auto-Updater
		Updater updater = null;

		if (getDescription().getVersion().startsWith("DEV - ")) {
			// If we are using a development build, we want to switch to our custom 
			updater = new GitHubBuildsUpdater(this, getFile(), "TheBusyBiscuit/ChestTerminal/master");
		}

		if (updater != null && cfg.getBoolean("options.auto-update")) updater.start();
		
		final Category category = new Category(new CustomItem(SlimefunItems.CHEST_TERMINAL, "&5\u7bb1\u5b50\u7d42\u7aef\u6a5f", "", "&a> \u9ede\u64ca\u958b\u555f"));
		/* "&5箱子主終端機", "", "&a> 點擊開啟" */
		
		final SlimefunItemStack milkyQuartz = new SlimefunItemStack("MILKY_QUARTZ", Material.QUARTZ, "&r\u4e73\u767d\u8272\u77f3\u82f1");
		/* "&r乳白色石英" */
		
		final SlimefunItemStack wirelessTerminal16 = new SlimefunItemStack("CT_WIRELESS_ACCESS_TERMINAL_16", Material.ITEM_FRAME, "&3CT \u7121\u7dda\u5b58\u53d6\u7d42\u7aef &b(16)", "&8\u21E8 &7\u5df2\u9023\u63a5: &c--", "&8\u21E8 &7\u6700\u5927\u8ddd\u96e2: &e16\u683c\u5167", "&c&o&8\u21E8 &e\u26A1 &70 / 10 J", "", "&7\u5047\u5982\u9023\u63a5\u81f3\u4e3b\u7d42\u7aef\u6a5f","\u53ef\u9060\u7a0b\u5b58\u53d6\u7d42\u7aef", "", "&7&e\u5c0d\u4e3b\u7d42\u7aef\u6a5f\u53f3\u6309&7\u555f\u52d5\u9023\u63a5", "&7&e\u5c0d\u7a7a\u6c23\u53f3\u6309&7\u958b\u555f\u5df2\u9023\u63a5\u4e3b\u7d42\u7aef\u6a5f");
		/* "&3CT 無線存取終端 &b(16)", "&8\u21E8 &7已連接: &c--", "&8\u21E8 &7最大距離: &e16格內", "&c&o&8\u21E8 &e\u26A1 &70 / 10 J", "", "&7假如連接至主終端機","可遠程存取終端", "", "&7&e對主終端機右按&7啟動連接", "&7&e對空氣右按&7開啟已連接主終端機" */
		final SlimefunItemStack wirelessTerminal64 = new SlimefunItemStack("CT_WIRELESS_ACCESS_TERMINAL_64", Material.ITEM_FRAME, "&3CT \u7121\u7dda\u5b58\u53d6\u7d42\u7aef &b(64)", "&8\u21E8 &7\u5df2\u9023\u63a5: &c--", "&8\u21E8 &7\u6700\u5927\u8ddd\u96e2: &e64\u683c\u5167", "&c&o&8\u21E8 &e\u26A1 &70 / 25 J", "", "&7\u5047\u5982\u9023\u63a5\u81f3\u4e3b\u7d42\u7aef\u6a5f","\u53ef\u9060\u7a0b\u5b58\u53d6\u7d42\u7aef", "", "&7&e\u5c0d\u4e3b\u7d42\u7aef\u6a5f\u53f3\u6309&7\u555f\u52d5\u9023\u63a5", "&7&e\u5c0d\u7a7a\u6c23\u53f3\u6309&7\u958b\u555f\u5df2\u9023\u63a5\u4e3b\u7d42\u7aef\u6a5f");
		/* "&3CT 無線存取終端 &b(64)", "&8\u21E8 &7已連接: &c--", "&8\u21E8 &7最大距離: &e64格內", "&c&o&8\u21E8 &e\u26A1 &70 / 25 J", "", "&7假如連接至主終端機","可遠程存取終端", "", "&7&e對主終端機右按&7啟動連接", "&7&e對空氣右按&7開啟已連接主終端機" */
		final SlimefunItemStack wirelessTerminal128 = new SlimefunItemStack("CT_WIRELESS_ACCESS_TERMINAL_128", Material.ITEM_FRAME, "&3CT \u7121\u7dda\u5b58\u53d6\u7d42\u7aef &b(128)", "&8\u21E8 &7\u5df2\u9023\u63a5: &c--", "&8\u21E8 &7\u6700\u5927\u8ddd\u96e2: &e128\u683c\u5167", "&c&o&8\u21E8 &e\u26A1 &70 / 50 J", "", "&7\u5047\u5982\u9023\u63a5\u81f3\u4e3b\u7d42\u7aef\u6a5f","\u53ef\u9060\u7a0b\u5b58\u53d6\u7d42\u7aef", "", "&7&e\u5c0d\u4e3b\u7d42\u7aef\u6a5f\u53f3\u6309&7\u555f\u52d5\u9023\u63a5", "&7&e\u5c0d\u7a7a\u6c23\u53f3\u6309&7\u958b\u555f\u5df2\u9023\u63a5\u4e3b\u7d42\u7aef\u6a5f");
		/* "&3CT 無線存取終端 &b(128)", "&8\u21E8 &7已連接: &c--", "&8\u21E8 &7最大距離: &e128格內", "&c&o&8\u21E8 &e\u26A1 &70 / 50 J", "", "&7假如連接至主終端機","可遠程存取終端", "", "&7&e對主終端機右按&7啟動連接", "&7&e對空氣右按&7開啟已連接主終端機" */
		final SlimefunItemStack wirelessTerminalTransdimensional = new SlimefunItemStack("CT_WIRELESS_ACCESS_TERMINAL_TRANSDIMENSIONAL", Material.ITEM_FRAME, "&3CT \u7121\u7dda\u5b58\u53d6\u7d42\u7aef &b(\u8de8\u4e16\u754c)", "&8\u21E8 &7\u5df2\u9023\u63a5: &c--", "&8\u21E8 &7\u6700\u5927\u8ddd\u96e2: &e\u7121\u9650", "&c&o&8\u21E8 &e\u26A1 &70 / 50 J", "", "&7\u5047\u5982\u9023\u63a5\u81f3\u4e3b\u7d42\u7aef\u6a5f","\u53ef\u9060\u7a0b\u5b58\u53d6\u7d42\u7aef", "", "&7&e\u5c0d\u4e3b\u7d42\u7aef\u6a5f\u53f3\u6309&7\u555f\u52d5\u9023\u63a5", "&7&e\u5c0d\u7a7a\u6c23\u53f3\u6309&7\u958b\u555f\u5df2\u9023\u63a5\u4e3b\u7d42\u7aef\u6a5f");
		/* "&3CT 無線存取終端 &b(跨世界)", "&8\u21E8 &7已連接: &c--", "&8\u21E8 &7最大距離: &e無限", "&c&o&8\u21E8 &e\u26A1 &70 / 50 J", "", "&7假如連接至主終端機","可遠程存取終端", "", "&7&e對主終端機右按&7啟動連接", "&7&e對空氣右按&7開啟已連接主終端機" */
		
		new SlimefunItem(category, milkyQuartz, new RecipeType(SlimefunItems.GEO_MINER), new ItemStack[0]).register();
		
		new SlimefunItem(category, new CustomItem(SlimefunItems.CHEST_TERMINAL, "&3CT \u767c\u5149\u9762\u677f", "&7\u5408\u6210\u7d44\u4ef6"), "CT_PANEL", RecipeType.ENHANCED_CRAFTING_TABLE,
		new ItemStack[] {milkyQuartz, SlimefunItems.BLISTERING_INGOT_3, milkyQuartz, SlimefunItems.REDSTONE_ALLOY, SlimefunItems.POWER_CRYSTAL, SlimefunItems.REDSTONE_ALLOY, milkyQuartz, SlimefunItems.BLISTERING_INGOT_3, milkyQuartz})
		.register(); /* "&3CT 發光面板", "&7合成組件" */
		
		new AccessTerminal(category, SlimefunItems.CHEST_TERMINAL, "CHEST_TERMINAL", RecipeType.ENHANCED_CRAFTING_TABLE,
		new ItemStack[] {milkyQuartz, SlimefunItems.GPS_TRANSMITTER_3, milkyQuartz, SlimefunItems.POWER_CRYSTAL, SlimefunItem.getItem("CT_PANEL"), SlimefunItems.POWER_CRYSTAL, SlimefunItems.PLASTIC_SHEET, SlimefunItems.ENERGY_REGULATOR, SlimefunItems.PLASTIC_SHEET})
		.register();
		
		new ImportBus(category, SlimefunItems.CT_IMPORT_BUS, "CT_IMPORT_BUS", RecipeType.ENHANCED_CRAFTING_TABLE,
		new ItemStack[] {SlimefunItems.REDSTONE_ALLOY, SlimefunItems.POWER_CRYSTAL, SlimefunItems.REDSTONE_ALLOY, SlimefunItems.HARDENED_METAL_INGOT, SlimefunItems.CARGO_INPUT, SlimefunItems.HARDENED_METAL_INGOT, SlimefunItems.PLASTIC_SHEET, SlimefunItems.CARGO_MOTOR, SlimefunItems.PLASTIC_SHEET})
		.register();
		
		new ExportBus(category, SlimefunItems.CT_EXPORT_BUS, "CT_EXPORT_BUS", RecipeType.ENHANCED_CRAFTING_TABLE,
		new ItemStack[] {null, SlimefunItems.DAMASCUS_STEEL_INGOT, null, SlimefunItems.ALUMINUM_BRONZE_INGOT, SlimefunItem.getItem("CT_IMPORT_BUS"), SlimefunItems.ALUMINUM_BRONZE_INGOT, SlimefunItems.PLASTIC_SHEET, SlimefunItems.GOLD_10K, SlimefunItems.PLASTIC_SHEET})
		.register();
		
		new WirelessTerminal(category, wirelessTerminal16, RecipeType.ENHANCED_CRAFTING_TABLE,
		new ItemStack[] {milkyQuartz, SlimefunItems.GPS_TRANSMITTER, milkyQuartz, SlimefunItems.COBALT_INGOT, SlimefunItems.CHEST_TERMINAL, SlimefunItems.COBALT_INGOT, SlimefunItems.BATTERY, SlimefunItems.ELECTRIC_MOTOR, SlimefunItems.BATTERY}) {

			@Override
			public int getRange() {
				return 16;
			}
			
		}.register();
		
		new WirelessTerminal(category, wirelessTerminal64, RecipeType.ENHANCED_CRAFTING_TABLE,
		new ItemStack[] {milkyQuartz, SlimefunItems.GPS_TRANSMITTER, milkyQuartz, SlimefunItems.COBALT_INGOT, wirelessTerminal16, SlimefunItems.COBALT_INGOT, SlimefunItems.BATTERY, SlimefunItems.ELECTRIC_MOTOR, SlimefunItems.BATTERY}) {

			@Override
			public int getRange() {
				return 64;
			}
			
		}.register();
		
		new WirelessTerminal(category, wirelessTerminal128, RecipeType.ENHANCED_CRAFTING_TABLE,
		new ItemStack[] {milkyQuartz, SlimefunItems.GPS_TRANSMITTER_2, milkyQuartz, SlimefunItems.COBALT_INGOT, wirelessTerminal64, SlimefunItems.COBALT_INGOT, SlimefunItems.BATTERY, SlimefunItems.ELECTRIC_MOTOR, SlimefunItems.BATTERY}) {

			@Override
			public int getRange() {
				return 128;
			}
			
		}.register();
		
		new WirelessTerminal(category, wirelessTerminalTransdimensional, RecipeType.ENHANCED_CRAFTING_TABLE,
		new ItemStack[] {milkyQuartz, SlimefunItems.GPS_TRANSMITTER_4, milkyQuartz, SlimefunItems.COBALT_INGOT, wirelessTerminal128, SlimefunItems.COBALT_INGOT, SlimefunItems.BATTERY, SlimefunItems.BLISTERING_INGOT_3, SlimefunItems.BATTERY}) {

			@Override
			public int getRange() {
				return -1;
			}
			
		}.register();
		
		OreGenSystem.registerResource(new OreGenResource() {
			
			@Override
			public String getName() {
				return "Milky Quartz";
			}
			
			@Override
			public String getMeasurementUnit() {
				return "Unit(s)";
			}
			
			@Override
			public ItemStack getIcon() {
				return milkyQuartz;
			}
			
			@Override
			public int getDefaultSupply(Biome biome) {
				return new Random().nextInt(6) + 1;
			}

			@Override
			public boolean isLiquid() {
				return false;
			}
		});
		
		CargoNet.extraChannels = true;
	}
}
