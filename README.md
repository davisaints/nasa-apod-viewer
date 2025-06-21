# APOD CLI Viewer

A simple Java CLI tool to **download** and **view** NASA's Astronomy Picture of the Day (APOD).

---

## Requirements

- Java 17 or higher
- `wget` and `xdg-open` (for Linux users)
- A NASA API key (details below)

---

## Obtaining a NASA API Key

1. Visit:  
   [https://api.nasa.gov/](https://api.nasa.gov/)

2. Generate your free API key.

3. Insert your API key into the `config.properties` file located at:
   ```
   project_dir/src/main/resources/config.properties
   ```

4. The file should include the following line:
   ```
   api.key=YOUR_API_KEY_HERE
   ```

---

## Building the Project

1. Clone the repository.

2. Open a terminal in the project root directory and execute:
   ```
   mvn clean package
   ```

3. Navigate to the `target` folder and run:
   ```
   java -jar apod-cli.jar [OPTION]...
   ```

---

## Available Options

- `-d`, `--download`  
  Download the image of the day.

- `-v`, `--view`  
  View the image of the day.

---