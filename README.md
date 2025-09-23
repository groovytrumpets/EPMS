# Employee Profile Management System (EPMS)

## 📌 Introduction
The purpose of this system is to provide an integrated solution that supports the Human Resources and Administrative departments in managing employee profiles and related workflows in an efficient, secure, and transparent manner. 

---

## 🛠️ Technologies
- **Backend**: Java 17, Servlet/JSP, JSTL  
- **Frontend**: HTML5, CSS3, JavaScript (Bootstrap)  
- **Database**: Microsoft SQL Server  
- **Server**: Apache Tomcat  
- **Deployment**: Cloudflare Tunnel  
- **Storage**: Cloudinary 
- **Project Management:** Waterfall Model  

---

## 🚀 Installation & Run

### 1. Requirements
- **JDK**: Java JDK 17  
- **IDE**: Apache NetBeans 17  
- **Server**: Apache Tomcat 10  
- **Database**: Microsoft SQL Server + SSMS  
- **JDBC Driver**: SQL Server JDBC Driver  

---

## 👥 Actors & Roles
- **Admin**: Manage accounts, configure fields, upload forms, view logs, access backups.  
- **HR/User**: Create/approve candidates, edit profiles, view employees, assign tests, manage leave requests.  
- **Employee**: Login, update info, upload documents, view/download forms, take assessments.  
- **Candidate**: Activate account, upload CV, register schedules, submit leave, take tests.  
- **System**: Validate files, log activities, send notifications, maintain backups.  

---
## 📑 Documentation
The following documents are available in the `/docs` folder:

- [RDS Document](./docs/SE1878_JS(IT)_G3_RDS%20Document.docx) or [Google Doc](https://docs.google.com/document/d/1CfNBzL0LphhNT5nsKX6_Rmpe328yQX86eiCQ_GvqkZw/edit?usp=sharing)    
- [Database Schema (SQL)](./docs/DB_Final.sql) or [Google Drive](https://drive.google.com/file/d/1YA2TvHjEratpqyruz5a3Oa_Kfax-nv4n/view?usp=sharing)  
## 🛠️ Setup Guide

#### (A) Install NetBeans 17
1. Go to [Apache NetBeans 17 Download](https://netbeans.apache.org/download/).  
2. Install and configure the IDE.  

#### (B) Install Java JDK 17
1. Download JDK 17 from [Oracle JDK Download](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html).  
2. Run the installer and follow instructions.  
3. Set the `JAVA_HOME` environment variable.  

#### (C) Install & Configure SQL Server + SSMS
1. Download SQL Server Management Studio (SSMS) from [Microsoft SSMS Download](https://aka.ms/ssmsfullsetup).  
2. Install SQL Server from [Microsoft SQL Server Download](https://www.microsoft.com/en-us/sql-server/sql-server-downloads).  
3. Run the setup wizard and configure.  

#### (D) Install Apache Tomcat 10
1. Download from [Tomcat 10 Download](https://tomcat.apache.org/download-10.cgi).  
2. Extract the ZIP file to a directory (e.g., `C:\Tomcat`).  
3. Configure Tomcat in NetBeans:  
   - Open NetBeans → Tools → Servers → Add Server → Apache Tomcat.  
   - Select the extracted Tomcat directory.  

#### (E) Set Up JDBC Driver for SQL Server
1. Download [Microsoft JDBC Driver for SQL Server](https://learn.microsoft.com/en-us/sql/connect/jdbc/download-microsoft-jdbc-driver-for-sql-server).  
2. Add JDBC driver to NetBeans:  
   - Tools → Libraries → New Library.  
   - Add the `.jar` file(s) from the JDBC driver.  

---

### 3. Database Setup
1. Open `docs/DB_Final.sql` in SQL Server Management Studio (SSMS).  
2. Execute the script to create the schema and insert sample data.  
3. Verify the database `EPMS` has been created successfully.  
