package p.zestianparejas;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import p.zestianparejas.command.CasarseCommand;
import p.zestianparejas.command.DivorcioCommand;
import p.zestianparejas.papi.papi;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class ZestianParejas extends JavaPlugin {
    private Map<Player, Player> solicitudesPendientes = new HashMap<>();
    private Map<Player, Player> casados = new HashMap<>();
    private File parejasFile;
    private FileConfiguration parejasConfig;


    public void onEnable() {
        // Cargar archivo de configuración parejas.yml
        parejasFile = new File(getDataFolder(), "parejas.yml");
        if (!parejasFile.exists()) {
            saveResource("parejas.yml", false);
        }
        parejasConfig = YamlConfiguration.loadConfiguration(parejasFile);


        if (getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new papi(this).register();
            getLogger().info("La expansión de PlaceholderAPI para ParejasPlugin ha sido registrada.");
        } else {
            getLogger().warning("PlaceholderAPI no encontrado. La expansión no puede ser registrada.");
        }
        getCommand("casarse").setExecutor(new CasarseCommand(this));
        getCommand("divorcio").setExecutor(new DivorcioCommand(this));

    }


    @Override
    public void onDisable() {
        saveParejasConfig();
        // Plugin shutdown logic
    }
    public void setPareja(Player jugador1, Player jugador2) {
        // Guardar los nombres de usuario de las parejas en el archivo YAML
        parejasConfig.set(jugador1.getName(), jugador2.getName());
        parejasConfig.set(jugador2.getName(), jugador1.getName());
        saveParejasConfig();
    }

    public void removePareja(Player jugador) {
        // Eliminar la pareja del jugador y de su pareja en el archivo YAML
        String pareja = parejasConfig.getString(jugador.getName());
        parejasConfig.set(jugador.getName(), null);
        if (pareja != null) {
            parejasConfig.set(pareja, null);
        }
        saveParejasConfig();
    }

    public String getPareja(Player jugador) {
        // Cargar datos de parejas desde el archivo YAML
        parejasConfig = YamlConfiguration.loadConfiguration(parejasFile);

        String parejaNombre = parejasConfig.getString(jugador.getName());
        return (parejaNombre != null && !parejaNombre.isEmpty()) ? parejaNombre : null;
    }


    private void saveParejasConfig() {
        try {
            parejasConfig.save(parejasFile);
        } catch (IOException e) {
            getLogger().warning("No se pudo guardar el archivo de configuración 'parejas.yml'");
        }
    }
    public Map<Player, Player> getSolicitudesPendientes() {
        return solicitudesPendientes;
    }
    public Map<Player, Player> getCasados() {
        return casados;
    }
}

