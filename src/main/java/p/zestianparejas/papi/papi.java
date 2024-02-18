package p.zestianparejas.papi;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import p.zestianparejas.ZestianParejas;

public class papi extends PlaceholderExpansion {

    private ZestianParejas plugin; // Referencia a la clase principal de tu plugin

    public papi(ZestianParejas plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getIdentifier() {
        return "parejas"; // Identificador único para tus placeholders
    }

    @Override
    public String getAuthor() {
        return "Spinnin"; // Tu nombre o el nombre de tu plugin
    }

    @Override
    public String getVersion() {
        return "1.0"; // Versión de tu expansión
    }

    // Método para manejar los placeholders
    @Override
    public String onPlaceholderRequest(Player player, String identifier) {
        if (player == null) {
            return ""; // Si el jugador es nulo, devuelve una cadena vacía
        }

        if (identifier.equals("estado")) {
            // Obtiene el nombre de la pareja del jugador
            String parejaNombre = plugin.getPareja(player);

            // Comprueba si el jugador tiene pareja
            if (parejaNombre != null) {
                return "§x§F§B§4§2§4§2❤ " + parejaNombre; // Devuelve el nombre de la pareja si el jugador está casado
            } else {
                return "Soltero"; // Devuelve "Soltero" si el jugador no está casado
            }
        }

        return null; // Devuelve nulo si el identificador no coincide con ninguno de los placeholders definidos
    }
}