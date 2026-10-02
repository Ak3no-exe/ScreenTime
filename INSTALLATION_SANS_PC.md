# INSTALLER SCREENTIME STATS — SANS PC

## 1. Créer le dépôt GitHub
Depuis ton téléphone : crée un nouveau dépôt (nom conseillé : `ScreenTime-Stats`).

## 2. Envoyer les fichiers
Décompresse le ZIP et envoie tous les fichiers dans le dépôt, en gardant les dossiers `www/`, `android-native/` et `.github/workflows/`.
Le fichier `.github/workflows/build-apk.yml` doit absolument être présent (dossier caché : vérifie qu'il apparaît sur GitHub).

## 3. Lancer la compilation
Onglet `Actions` > `Build ScreenTime APK` > `Run workflow`. Attends la fin (quelques minutes).

## 4. Récupérer l'APK
Ouvre l'exécution terminée > `Artifacts` > télécharge `ScreenTimeStats-APK` > récupère `app-debug.apk`.

## 5. Installer
Ouvre `app-debug.apk` (autorise l'installation depuis cette source si Android le demande).

## 6. Premier lancement
Ouvre l'appli > « Ouvrir les réglages » > active l'accès aux données d'utilisation pour ScreenTime Stats > reviens dans l'appli.

## Important
Les données restent sur ton téléphone. L'historique commence à l'installation (Android ne garde que quelques jours).
