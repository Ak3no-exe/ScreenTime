# ScreenTime Stats

Application Android (Capacitor) qui lit automatiquement le temps d'écran du téléphone et affiche des statistiques jour / semaine / mois / année.

## Ce qui est intégré
- Interface `www/index.html` : Accueil, Statistiques (graphique + périodes 7 j à 1 an), Applications, Historique, Réglages.
- Plugin natif `UsageStats` (UsageStatsManager) : temps par appli et nombre d'ouvertures.
- Historique local (aucun serveur), date de dernière synchro, thème clair/sombre/auto, suppression de toutes les données.

## Limites
- Android ne conserve que ~7-10 jours d'événements : l'historique se construit dès l'installation, en ouvrant l'appli régulièrement.
- Pas de donnée = « indisponible », rien n'est inventé.
- iOS : Apple n'expose pas ces données aux apps tierces, non supporté.
- Pas encore : notifications de résumé, période personnalisée, comparaison de deux périodes, widgets, exports CSV/PDF.

## Compilation sans PC
Le workflow `.github/workflows/build-apk.yml` génère l'APK. Voir `INSTALLATION_SANS_PC.md`.

## Compilation sur PC
1. `npm install` 2. `npx cap add android` 3. copier `android-native/*.java` dans `android/app/src/main/java/com/screentimestats/app/` et ajouter la permission `PACKAGE_USAGE_STATS` 4. `npx cap sync android` 5. `npm run build`
