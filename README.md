
# RecurrenceSolverApp

RecurrenceSolverApp is an Android application that allows users to solve and visualize common mathematical recurrences step by step. Users can input a recurrence equation, and the app computes and displays a detailed solution, including iterative expansion, pattern identification, summation expansion, and determination of asymptotic complexity.

## Features

- Input recurrences in the format `T(n) = aT(n/b) + f(n)`
- Detailed step-by-step solution
- Mathematical equations displayed using LaTeX format with MathJax
- Modern and intuitive user interface based on Material Design

## Project Structure

- `MainActivity.java`: Manages the user interface and main operations
- `IterativeRecurrenceSolver.java`: Contains the logic for solving recurrences iteratively
- `RecurrenceParser.java`: Parses the recurrence equation input by the user
- `activity_main.xml`: User interface layout
- `styles.xml`, `colors.xml`, `themes.xml`: Resource files for styles and colors
- Icons and resources used in the application

## Requirements

- Android Studio (latest version recommended)
- Android SDK (minimum SDK version 21 or higher)
- Internet connection to load MathJax in the WebView

## Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/RecurrenceSolverApp.git
   ```

2. Open the project with Android Studio:
   - Launch Android Studio.
   - Select "Open an existing Android Studio project".
   - Navigate to the cloned project folder and select it.

3. Build and run the application:
   - Ensure all dependencies are downloaded correctly.
   - Connect an Android device or use an emulator.
   - Click "Run" to compile and run the application.

## Usage

1. **Enter the Recurrence Equation**:
   - In the input field, enter the equation in the format `T(n) = aT(n/b) + f(n)`.
   - Example: `T(n) = 2T(n/2) + n`.

2. **Calculate the Solution**:
   - Press the "Calculate" button.
   - The app will parse the equation and compute the step-by-step solution.

3. **View the Solution**:
   - The detailed solution will be displayed below.
   - Equations are formatted in LaTeX for better readability.

4. **Example Equations**:
   - `T(n) = 3T(n/2) + n^2`
   - `T(n) = 4T(n/2) + n^3`
   - `T(n) = 2T(n/4) + n log n`

## Contributions

Contributions, bug reports, and feature requests are welcome! Feel free to open an issue or a pull request.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE.md) file for details.

## Contact

For any questions or information, please contact [baroni.buildati@gmail.com](mailto:baroni.buildati@gmail.com).
