package org.example;


import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@RestController
public class PreferenceController {

    // Endpoint pour sauvegarder la préférence de couleur dans un cookie
    @PostMapping("/api/set-color")
    public String setColorPreference(@RequestParam String color, HttpServletResponse response) {
        try {
            // Vérifiez que la couleur n'est pas nulle ou vide
            if (color == null || color.trim().isEmpty()) {
                throw new IllegalArgumentException("La couleur ne peut pas être vide");
            }

            // Créez un cookie pour la couleur préférée
            Cookie colorCookie = new Cookie("preferredColor", color);
            colorCookie.setMaxAge(20); // Durée de vie de 20 secondes
            colorCookie.setPath("/");
            colorCookie.setHttpOnly(true); // Sécuriser le cookie
            colorCookie.setSecure(false); // Utilisez false pour le développement local
            response.addCookie(colorCookie);

            // Retournez un message de succès
            return "Couleur préférée sauvegardée: " + color;
        } catch (IllegalArgumentException e) {
            // Log de l'exception pour le débogage
            System.err.println("Erreur: " + e.getMessage());
            return "Erreur: " + e.getMessage();
        } catch (Exception e) {
            // Log de l'exception pour le débogage
            e.printStackTrace(); // Ou utilisez un logger pour enregistrer l'erreur
            return "Erreur lors de la sauvegarde de la couleur: " + e.getMessage();
        }
    }


    // Endpoint pour lire la préférence de couleur à partir du cookie
    @GetMapping("/api/get-color")
    public String getColorPreference(@CookieValue(value = "preferredColor", defaultValue = "white") String preferredColor) {
        return preferredColor;
    }
}
