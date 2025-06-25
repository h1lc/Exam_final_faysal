# API de Gestion des Employés et Candidats

## 📋 Description

Cette API Spring Boot permet de gérer les employés et les candidats d'une entreprise. Elle fournit des endpoints REST pour effectuer toutes les opérations CRUD (Create, Read, Update, Delete) sur ces entités.

## 🚀 Technologies utilisées

- **Spring Boot 3.5.3**
- **Spring Data JPA**
- **Spring Web**
- **Spring Validation**
- **H2 Database** (pour le développement)
- **MySQL Connector** (pour la production)
- **Lombok**
- **Java 17**

## 📁 Structure du projet

```
src/main/java/com/example/back_server/
├── BackServerApplication.java
├── controller/
│   ├── MainController.java
│   ├── EmployeeController.java
│   └── CandidateController.java
├── service/
│   ├── EmployeeService.java
│   └── CandidateService.java
├── repository/
│   ├── EmployeeRepository.java
│   └── CandidateRepository.java
└── entity/
    ├── Employee.java
    └── Candidate.java
```

## 🏗️ Entités

### Employee
- **name** (String) - Nom de l'employé
- **poste** (String) - Poste occupé
- **debutContrat** (LocalDate) - Date de début de contrat
- **finContrat** (LocalDate) - Date de fin de contrat (optionnel)
- **numeroIdentification** (Long) - Numéro d'identification unique
- **dateNaissance** (LocalDate) - Date de naissance
- **address** (String) - Adresse
- **email** (String) - Email unique
- **telephone** (BigDecimal) - Numéro de téléphone
- **salaire** (BigDecimal) - Salaire
- **observation** (String) - Observations (optionnel)

### Candidate
- **name** (String) - Nom du candidat
- **numeroIdentification** (Long) - Numéro d'identification unique
- **dateNaissance** (LocalDate) - Date de naissance
- **address** (String) - Adresse
- **email** (String) - Email unique
- **telephone** (Long) - Numéro de téléphone
- **note** (Integer) - Note entre 1 et 10
- **specialite** (String) - Spécialité
- **dateEntretien** (LocalDate) - Date de l'entretien
- **observation** (String) - Observations (optionnel)

## 🛠️ Installation et lancement

### Prérequis
- Java 17 ou supérieur
- Maven 3.6 ou supérieur

### Étapes d'installation

1. **Cloner le projet**
   ```bash
   git clone <repository-url>
   cd back_server
   ```

2. **Compiler le projet**
   ```bash
   ./mvnw clean compile
   ```

3. **Lancer l'application**
   ```bash
   ./mvnw spring-boot:run
   ```

4. **Accéder à l'application**
   - API principale : http://localhost:8080
   - Console H2 : http://localhost:8080/h2-console
   - JDBC URL : `jdbc:h2:mem:testdb`
   - Username : `sa`
   - Password : `password`

## 📚 Endpoints API

### Endpoints principaux

#### GET /
- **Description** : Informations sur l'API
- **Réponse** : Informations générales et documentation des endpoints

#### GET /health
- **Description** : Vérification de l'état de l'application
- **Réponse** : Status de l'application

### Gestion des Employés

#### GET /api/employees
- **Description** : Récupérer tous les employés
- **Réponse** : Liste des employés

#### GET /api/employees/{id}
- **Description** : Récupérer un employé par son ID
- **Paramètres** : `id` (Long)
- **Réponse** : Employé ou 404 si non trouvé

#### POST /api/employees
- **Description** : Créer un nouvel employé
- **Body** : Objet Employee (JSON)
- **Réponse** : Employé créé (201) ou erreur de validation (400)

#### PUT /api/employees/{id}
- **Description** : Mettre à jour un employé
- **Paramètres** : `id` (Long)
- **Body** : Objet Employee (JSON)
- **Réponse** : Employé mis à jour ou erreur (400)

#### DELETE /api/employees/{id}
- **Description** : Supprimer un employé
- **Paramètres** : `id` (Long)
- **Réponse** : Message de confirmation ou erreur (400)

#### GET /api/employees/search/name?name={name}
- **Description** : Rechercher des employés par nom
- **Paramètres** : `name` (String)
- **Réponse** : Liste des employés correspondants

#### GET /api/employees/search/poste?poste={poste}
- **Description** : Rechercher des employés par poste
- **Paramètres** : `poste` (String)
- **Réponse** : Liste des employés correspondants

#### GET /api/employees/active
- **Description** : Récupérer les employés actifs
- **Réponse** : Liste des employés avec contrat actif

#### GET /api/employees/search/salary?minSalary={minSalary}
- **Description** : Rechercher des employés par salaire minimum
- **Paramètres** : `minSalary` (BigDecimal)
- **Réponse** : Liste des employés correspondants

### Gestion des Candidats

#### GET /api/candidates
- **Description** : Récupérer tous les candidats
- **Réponse** : Liste des candidats

#### GET /api/candidates/{id}
- **Description** : Récupérer un candidat par son ID
- **Paramètres** : `id` (Long)
- **Réponse** : Candidat ou 404 si non trouvé

#### POST /api/candidates
- **Description** : Créer un nouveau candidat
- **Body** : Objet Candidate (JSON)
- **Réponse** : Candidat créé (201) ou erreur de validation (400)

#### PUT /api/candidates/{id}
- **Description** : Mettre à jour un candidat
- **Paramètres** : `id` (Long)
- **Body** : Objet Candidate (JSON)
- **Réponse** : Candidat mis à jour ou erreur (400)

#### DELETE /api/candidates/{id}
- **Description** : Supprimer un candidat
- **Paramètres** : `id` (Long)
- **Réponse** : Message de confirmation ou erreur (400)

#### GET /api/candidates/search/name?name={name}
- **Description** : Rechercher des candidats par nom
- **Paramètres** : `name` (String)
- **Réponse** : Liste des candidats correspondants

#### GET /api/candidates/search/specialite?specialite={specialite}
- **Description** : Rechercher des candidats par spécialité
- **Paramètres** : `specialite` (String)
- **Réponse** : Liste des candidats correspondants

#### GET /api/candidates/top
- **Description** : Récupérer les candidats avec une note élevée (8+)
- **Réponse** : Liste des candidats triés par note décroissante

#### GET /api/candidates/search/notes?minNote={minNote}&maxNote={maxNote}
- **Description** : Rechercher des candidats par plage de notes
- **Paramètres** : `minNote` (Integer), `maxNote` (Integer)
- **Réponse** : Liste des candidats correspondants

#### GET /api/candidates/recent
- **Description** : Récupérer les candidats récents (entretien dans les 30 derniers jours)
- **Réponse** : Liste des candidats récents

#### GET /api/candidates/stats/count-by-specialite
- **Description** : Statistiques du nombre de candidats par spécialité
- **Réponse** : Tableau avec spécialité et nombre de candidats

#### GET /api/candidates/stats/average-note-by-specialite
- **Description** : Moyenne des notes par spécialité
- **Réponse** : Tableau avec spécialité et moyenne des notes

## 📝 Exemples d'utilisation

### Créer un employé
```bash
curl -X POST http://localhost:8080/api/employees \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Jean Dupont",
    "poste": "Développeur Java",
    "debutContrat": "2023-01-15",
    "numeroIdentification": 1001,
    "dateNaissance": "1990-05-20",
    "address": "123 Rue de la Paix, Paris",
    "email": "jean.dupont@email.com",
    "telephone": 123456789.00,
    "salaire": 45000.00,
    "observation": "Excellent développeur"
  }'
```

### Créer un candidat
```bash
curl -X POST http://localhost:8080/api/candidates \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Emma Rousseau",
    "numeroIdentification": 2001,
    "dateNaissance": "1993-07-14",
    "address": "147 Rue Saint-Michel, Paris",
    "email": "emma.rousseau@email.com",
    "telephone": 123456789,
    "note": 8,
    "specialite": "Développeur Full Stack",
    "dateEntretien": "2024-01-15",
    "observation": "Candidat prometteur"
  }'
```

### Récupérer tous les employés
```bash
curl -X GET http://localhost:8080/api/employees
```

### Mettre à jour un employé
```bash
curl -X PUT http://localhost:8080/api/employees/1 \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Jean Dupont",
    "poste": "Développeur Senior Java",
    "debutContrat": "2023-01-15",
    "numeroIdentification": 1001,
    "dateNaissance": "1990-05-20",
    "address": "123 Rue de la Paix, Paris",
    "email": "jean.dupont@email.com",
    "telephone": 123456789.00,
    "salaire": 50000.00,
    "observation": "Excellent développeur senior"
  }'
```

### Supprimer un candidat
```bash
curl -X DELETE http://localhost:8080/api/candidates/1
```

## 🔧 Configuration

### Base de données
L'application utilise H2 en mémoire pour le développement. Pour utiliser MySQL en production, modifiez `application.properties` :

```properties
# Configuration MySQL
spring.datasource.url=jdbc:mysql://localhost:3306/your_database
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect
```

### Validation
Toutes les entités incluent des validations :
- Champs obligatoires
- Format d'email valide
- Notes entre 1 et 10 pour les candidats
- Dates de naissance dans le passé
- Unicité des emails et numéros d'identification

## 🧪 Tests

Pour lancer les tests :
```bash
./mvnw test
```

## 📊 Données d'exemple

L'application inclut des données d'exemple qui sont automatiquement chargées au démarrage :
- 5 employés avec différents postes
- 8 candidats avec différentes spécialités et notes

## 🔒 Sécurité

Actuellement, l'API n'inclut pas d'authentification. Pour un environnement de production, il est recommandé d'ajouter :
- Spring Security
- JWT Authentication
- CORS configuration appropriée

## 📞 Support

Pour toute question ou problème, n'hésitez pas à ouvrir une issue sur le repository. 