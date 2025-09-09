# MediLabo

## Contexte de l'application
Les patients souffrent de mauvais choix nutritionnels; ils choisissent des produits bon marché : riches en sucre et en calories au lieu de : opter pour un régime alimentaire sain avec des légumes.
À cause de ces choix, ils ont tendance à être plus exposés à certaines maladies, dont le diabète de type 2. Cet application est un outil pour aider les médecins à identifier les patients les plus à risque pour des fins préventatifs.

## Services disponibles
- Gateway Service : http://localhost:8080
- Patient Service : http://localhost:8081/patients
- Notes Service : http://localhost:8082/notes
- Report Service : http://localhost:8083/report
- Frontend : http://localhost:8084

## Authentification

Deux comptes sont simulés via Spring Security (dans le Gateway) :
#### Médecin :
- username : doctor
- mot de passe : doctor123
- accès à toutes les fonctionnalités.
#### Patient :
- login : patient
- mot de passe : patient123
- accès uniquement à son tableau de bord.

<h1 style="color:green;">Green Code</h1>

## Objectif du Green Code
Le **Green code** vise à réduire l'empreinte écologique du numérique en écrivant un code plus éfficace, optimisé en contenu, et consommant moins en ressources.

## Identifier les parties d'un code qui consomment de la mémoire inutilement
#### En analysant le code
- des fonctionnalités inutiles
- des algorithmes coûteux (par exemple : ne pas utiliser un cache quand possible)
- des dépendances lourdes et non nécessaires
- des conteneurs Docker volumineux qui augment les besoins en stockage
- le poids des ressources (par exemple : images) 
#### En temps réel
En utilisant des Java profiler comme celui integré à intellij ou JProfiler. Ils permettent d'identifier :
- Quel partie du code utilise le plus de CPU
- Les fuites mémoire
#### Suivie des ressources
Ajouter des logs pendant les traitements lours afin d'identifier quel appel fait exploser l'utilisation mémoire ou CPU.

## Pistes d'amélioration du projet
- Essayer d'éviter de garder trop de données en mémoire en même temps (des listes et boucles)
- Optimiser les ressources (réduire la taille des images, ne charger que les ressources nécessaires)
- Vérifier qu'il n'y a pas de code inutile
- Voir si un regroupment des données est possible pour éviter les appels fréquents aux APIs.
- Utiliser des images Docker plus léger (jdk-slim) pour réduire l'empreinte lors du déploiement.