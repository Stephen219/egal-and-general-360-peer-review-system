# Setting Up the Project


## To download the project

1. Ensure Git Bash is installed on your computer.
2. Open Git Bash and run the following command to clone the repository:
   ```bash
   git clone https://git.cardiff.ac.uk/c22067364/legal-and-general-client-project-team-11-personal.git
    ```
   ## Open the Project in IntelliJ

1. Create a MariaDB database named "legalandgeneral" and establish a connection with the project.
2. Locate SQL Scripts: Navigate to the "src/main/resources" directory in the project to locate the SQL scripts.
3. Once connected to the MariaDB database, the SQL script will automatically be executed as part of the project setup process.
   - If the automatic execution doesn't occur, or if you want to run the script manually:
      - In the Database tool window, right-click on the "legalandgeneral" database connection.
      - Choose "Run SQL Script" and select the SQL files from the "src/main/resources" directory.
      - Execute the script and ensure that it runs successfully.

## Load and Run the Project

1. Gradle will automatically load. After loading, run the application.
2. Ensure the web application is running before proceeding, as Selenium UI tests require it.

## Build and Compile

In the terminal, execute the following commands for a clean build:
```bash
./gradlew clean build
```
Due to the integration of extended features into the project, certain tests may fail as the code they evaluate was affected during the development of the additional end-to-end feature.




Before proceeding with the steps below, ensure that you have **Node** installed, as it is necessary for setting up Tailwind CSS.

## Installing Tailwind CSS

1. Install Tailwind CSS by executing the following command at the root of the project:

    ```bash
    npm install
    ```

## Running Tailwind CSS

2. Start the Tailwind CSS compiler by running the following command at the root of the project:

    ```bash
    npm run tailwindcss
    ```

   If you intend to create custom styles, you can do so by writing them in the `app.css` file located at the root of the project. Ensure that Tailwind CSS is running, as it will compile the CSS and incorporate it into the output.

## Using Tailwind in Templates

To integrate Tailwind CSS into your templates, include the following line in the `<head>` section of your template:

```html
<link rel="stylesheet" th:href="@{/main.css}" />
```



## Running the Application

Before building, ensure that the application is up and running. You can start the application using the following command:

```bash
./gradlew bootRun
```

## Building the Application  (for windows)
```bash
./gradlew clean build   
```
