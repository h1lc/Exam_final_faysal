-- Données d'exemple pour les employés
INSERT INTO employees (name, poste, debut_contrat, fin_contrat, numero_identification, date_naissance, address, email, telephone, salaire, observation, created_at, updated_at) VALUES
('Jean Dupont', 'Développeur Java', '2023-01-15', NULL, 1001, '1990-05-20', '123 Rue de la Paix, Paris', 'jean.dupont@email.com', 123456789.00, 45000.00, 'Excellent développeur avec 5 ans d''expérience', NOW(), NOW()),
('Marie Martin', 'Chef de Projet', '2022-06-01', '2024-12-31', 1002, '1985-08-12', '456 Avenue des Champs, Lyon', 'marie.martin@email.com', 987654321.00, 55000.00, 'Gestionnaire de projet expérimentée', NOW(), NOW()),
('Pierre Durand', 'Développeur Frontend', '2023-03-10', NULL, 1003, '1992-11-03', '789 Boulevard Central, Marseille', 'pierre.durand@email.com', 555666777.00, 42000.00, 'Spécialiste React et Angular', NOW(), NOW()),
('Sophie Bernard', 'DevOps Engineer', '2022-09-15', NULL, 1004, '1988-04-25', '321 Rue du Commerce, Toulouse', 'sophie.bernard@email.com', 111222333.00, 48000.00, 'Expert en CI/CD et Docker', NOW(), NOW()),
('Lucas Petit', 'Data Analyst', '2023-07-01', '2024-06-30', 1005, '1995-12-08', '654 Place de la République, Nantes', 'lucas.petit@email.com', 444555666.00, 38000.00, 'Analyste junior prometteur', NOW(), NOW());

-- Données d'exemple pour les candidats
INSERT INTO candidates (name, numero_identification, date_naissance, address, email, telephone, note, specialite, date_entretien, observation, created_at, updated_at) VALUES
('Emma Rousseau', 2001, '1993-07-14', '147 Rue Saint-Michel, Paris', 'emma.rousseau@email.com', 123456789, 8, 'Développeur Full Stack', '2024-01-15', 'Candidat très prometteur, bonne culture technique', NOW(), NOW()),
('Thomas Moreau', 2002, '1990-02-28', '258 Avenue Victor Hugo, Lyon', 'thomas.moreau@email.com', 987654321, 6, 'Développeur Backend', '2024-01-20', 'Bon niveau technique, manque d''expérience en équipe', NOW(), NOW()),
('Julie Leroy', 2003, '1987-11-05', '369 Boulevard de la Croix-Rousse, Lyon', 'julie.leroy@email.com', 555666777, 9, 'Architecte Logiciel', '2024-01-25', 'Excellente candidate, expérience solide', NOW(), NOW()),
('Antoine Simon', 2004, '1996-09-18', '741 Rue de la Bourse, Marseille', 'antoine.simon@email.com', 111222333, 7, 'Développeur Mobile', '2024-02-01', 'Bon potentiel, spécialisation iOS/Android', NOW(), NOW()),
('Camille Dubois', 2005, '1991-04-12', '852 Place Bellecour, Lyon', 'camille.dubois@email.com', 444555666, 5, 'Développeur Frontend', '2024-02-05', 'Niveau correct, besoin de formation', NOW(), NOW()),
('Nicolas Roux', 2006, '1989-12-03', '963 Rue de la République, Toulouse', 'nicolas.roux@email.com', 777888999, 8, 'DevOps Engineer', '2024-02-10', 'Très bon profil, expérience cloud', NOW(), NOW()),
('Sarah Mercier', 2007, '1994-06-22', '159 Avenue Jean Jaurès, Nantes', 'sarah.mercier@email.com', 333444555, 7, 'Data Scientist', '2024-02-15', 'Profil intéressant, compétences ML', NOW(), NOW()),
('Alexandre Blanc', 2008, '1992-03-30', '753 Rue du Faubourg, Bordeaux', 'alexandre.blanc@email.com', 666777888, 6, 'Développeur Java', '2024-02-20', 'Niveau moyen, motivation présente', NOW(), NOW()); 