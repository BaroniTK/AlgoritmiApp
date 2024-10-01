
# RecurrenceSolverApp

RecurrenceSolverApp is an Android application that allows users to solve and visualize common mathematical recurrences step by step. Users can input a recurrence equation, and the app computes and displays a detailed solution, including iterative expansion, pattern identification, summation resolution, and determination of asymptotic complexity.

## Features

- **Input Recurrences**:
  - Support for recurrences in the format `T(n) = aT(n/b) + f(n)`
  - Handles a wide range of functions `f(n)`, including polynomial, logarithmic, and their combinations
- **Solving Methods**:
  - **Iterative Expansion**:
    - Detailed step-by-step expansion of the recurrence
    - Symbolic summation resolution without explicit iteration indices
    - Clear and concise mathematical representations using LaTeX
  - **Master Theorem**:
    - Automatic classification of the recurrence into the appropriate case
    - Direct application of the Master Theorem for quick solutions
- **Detailed Step-by-Step Solutions**:
  - Comprehensive explanations for each solving method
  - Symbolic manipulation and simplification steps for better understanding
- **Mathematical Equations**:
  - Rendered using LaTeX with MathJax for high-quality display
- **User Interface**:
  - Modern and intuitive design based on Material Design principles
  - Easy input fields and selection menus for a seamless user experience
- **Additional Features**:
  - Examples of common recurrence equations for quick reference
  - Responsive layout optimized for various screen sizes and devices

## Project Structure

- `MainActivity.java`: Manages the user interface, provides a menu for selecting the solving method (Iterative Expansion or Master Theorem), and handles the main operations
- `IterativeRecurrenceSolver.java`: Contains the enhanced logic for solving recurrences iteratively, including improved summation resolution and symbolic manipulation
- `RecurrenceParser.java`: Parses the recurrence equation input by the user, extracting coefficients and function components
- `activity_main.xml`: User interface layout with input fields, method selection menu (Spinner), and result WebView
- `styles.xml`, `colors.xml`, `themes.xml`: Resource files for styles and colors
- Icons and resources used in the application

## Requirements

- **Development**:
  - Android Studio (latest version recommended)
  - Android SDK (minimum SDK version 21 or higher)
- **Runtime**:
  - Android device or emulator running SDK version 21 or higher
  - Internet connection to load MathJax in the WebView for rendering LaTeX equations

## Installation

1. **Clone the repository**:
   ```bash
   git clone https://github.com/your-username/RecurrenceSolverApp.git
   ```

2. **Open the project with Android Studio**:
   - Launch Android Studio.
   - Select "Open an existing Android Studio project".
   - Navigate to the cloned project folder and select it.

3. **Build and run the application**:
   - Ensure all dependencies are downloaded correctly.
   - Connect an Android device or use an emulator.
   - Click "Run" to compile and run the application.

## Usage

1. **Enter the Recurrence Equation**:
   - In the input field, enter the equation in the format `T(n) = aT(n/b) + f(n)`.
   - **Examples**:
     - `T(n) = 2T(n/2) + n`
     - `T(n) = 3T(n/2) + n^2`
     - `T(n) = 4T(n/4) + n^3`
     - `T(n) = 2T(n/4) + n log n`

2. **Select the Solving Method**:
   - Use the drop-down menu to select between:
     - **Iterative Expansion**: Provides a detailed, step-by-step expansion of the recurrence, including symbolic summation resolution.
     - **Master Theorem**: Quickly applies the Master Theorem to determine the asymptotic complexity.

3. **Calculate the Solution**:
   - Press the "Calculate" button.
   - The app will parse the equation and compute the step-by-step solution using the selected method.

4. **View the Solution**:
   - The detailed solution will be displayed below.
   - Equations are formatted in LaTeX for better readability.

5. **Example Equations**:
   - `T(n) = 3T(n/2) + n^2`
   - `T(n) = 4T(n/2) + n^3`
   - `T(n) = 2T(n/4) + n log n`

## Detailed Solving Methods

### Iterative Expansion

The Iterative Expansion method provides a comprehensive, step-by-step breakdown of solving the recurrence. It includes:

- **Expansion Steps**: Iteratively expands the recurrence until the base case is reached.
- **Pattern Identification**: Identifies the general pattern after `k` iterations.
- **Summation Resolution**: Resolves the resulting summation using symbolic methods, avoiding explicit iteration indices for clarity.
- **Final Simplification**: Simplifies the expressions to determine the asymptotic complexity.

### Master Theorem

The Master Theorem offers a quick and efficient way to determine the asymptotic complexity of recurrences of the form `T(n) = aT(n/b) + f(n)`. It automatically:

- **Classifies** the recurrence into one of the three cases of the Master Theorem.
- **Applies** the appropriate case to determine the solution.
- **Provides** a concise conclusion of the asymptotic behavior.

## Contributions

Contributions, bug reports, and feature requests are welcome! Feel free to open an issue or a pull request.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE.md) file for details.

## Contact

For any questions or information, please contact [baroni.buildati@gmail.com](mailto:baroni.buildati@gmail.com).
