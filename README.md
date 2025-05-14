📦 Help Project

Help is an information system designed to support the management of food aid distribution within a network composed of supplier companies, dioceses, and local distribution centers (called “Poli”). The project was developed as part of a university software engineering course at the University of Palermo.
🎯 Objective

The system aims to:

    Manage the acquisition, storage, and distribution of food supplies;

    Register and track families in need;

    Automatically forecast monthly food requirements;

    Generate activity reports and handle error reporting.

🧱 Architecture

The application is based on a Repository Architecture, where independent subsystems (Polo, Diocese, Company, Admin UE) communicate through a centralized database (MariaDB) using a dedicated DBManager module.
🖥️ Technologies

    Language: Java

    GUI: Java Swing with WindowBuilder

    Database: MariaDB (via XAMPP / phpMyAdmin)

    Email Service: Gmail SMTP for password recovery

    IDE: Eclipse

    Reports: Generated as .txt files using BufferedWriter

👥 Key Roles

    Polo Manager: Manages family records and distributes food.

    Diocese Manager: Oversees Polos and forwards shipments.

    UE Admin: Has full control over the entire system.

    Company Manager: Manages product catalogs and sends food batches.

📂 Documentation

This repository includes:

    📄 RAD – Requirements Analysis Document

    📄 SDD – System Design Document

    📄 ODD – Object Design Document
