package utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

public class NumberUtils {

    public String generarNumeroRandon(int longitud){
        if (longitud < 0) {
            throw new IllegalArgumentException("La longitud no puede ser negativa.");
        }
        String caracteres = "0123456789";
        StringBuilder resultado = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < longitud; i++) {
            int randomIndex = random.nextInt(caracteres.length());
            resultado.append(caracteres.charAt(randomIndex));
        }
        return resultado.toString();
    }

    /**
     * Convierte un String de moneda (ej: "$ 250.000,50") a BigDecimal.
     * Maneja el formato latinoamericano donde el punto es separador de miles.
     */
    public static BigDecimal parseMoneda(String texto) {
        if (texto == null || texto.isEmpty()) return BigDecimal.ZERO;

        String limpio = texto.replace("$", "")
                .replace(".", "") // Quita separador de miles
                .replace(",", ".") // Cambia coma decimal por punto para Java
                .trim();
        return new BigDecimal(limpio).setScale(2, RoundingMode.HALF_UP);
    }
}
