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
