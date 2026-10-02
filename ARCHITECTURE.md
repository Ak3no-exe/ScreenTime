# Architecture

```text
Interface (HTML/CSS/JS)  www/index.html
        |
        +-- Calculs (moyennes sur jours réellement disponibles)
        +-- Historique local (localStorage)
        |
Capacitor bridge
        |
        +-- Plugin natif UsageStats (Java)
                |
                +-- UsageStatsManager.queryEvents
```

Extensions prévues : notifications locales, widgets (module natif), export CSV/PDF, sauvegarde cloud facultative, objectifs, comparaison année par année.
iOS : l'API DeviceActivity ne permet pas de sortir les données de l'extension, donc pas de backend équivalent.
