# Setting Up the Project

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
